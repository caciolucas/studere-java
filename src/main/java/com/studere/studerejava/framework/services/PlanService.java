package com.studere.studerejava.framework.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.framework.core.exceptions.OpenAIAPIError;
import com.studere.studerejava.framework.models.Module;
import com.studere.studerejava.framework.models.Plan;
import com.studere.studerejava.framework.models.PlanItem;
import com.studere.studerejava.framework.models.dto.request.PlanAiGenerateDTO;
import com.studere.studerejava.framework.models.dto.request.PlanCreateOrUpdateDTO;
import com.studere.studerejava.framework.models.dto.request.PlanItemDTO;
import com.studere.studerejava.framework.repositories.PlanRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PlanService<T extends Plan, U extends PlanItem> {
    protected final PlanRepository<T> planRepository;
    protected final ModuleService moduleService;
    protected final OpenAiService openAiService;
    private final ObjectMapper objectMapper;

    public PlanService(PlanRepository<T> planRepository, ModuleService moduleService, OpenAiService openAiService) {
        this.planRepository = planRepository;
        this.moduleService = moduleService;
        this.openAiService = openAiService;
        this.objectMapper = new ObjectMapper();
    }

    protected abstract T createNewPlan();

    protected abstract U createNewPlanItem();


    protected T setPlanFieldsFromDTO(PlanCreateOrUpdateDTO<? extends PlanItemDTO> planCreateOrUpdateDTO, T plan, UUID userId) {
        Module module = moduleService.findModuleById(planCreateOrUpdateDTO.getModuleId(), userId);
        plan.setTitle(planCreateOrUpdateDTO.getTitle());
        plan.setModule(module);
        return plan;
    }

    protected void addPlanItemsFromDTO(PlanCreateOrUpdateDTO<? extends PlanItemDTO> planCreateOrUpdateDTO, T plan) {
        plan.getPlanItems().clear();
        plan.getPlanItems().addAll(planCreateOrUpdateDTO.getItems().stream().map(item -> {
            U planItem = createNewPlanItem();
            planItem.setTitle(item.getTitle());
            planItem.setDescription(item.getDescription());
            planItem.setPlan(plan);
            return planItem;
        }).toList());
    }

    public T createPlan(PlanCreateOrUpdateDTO planCreateOrUpdateDTO, UUID userId) {
        Module module = moduleService.findModuleById(planCreateOrUpdateDTO.getModuleId(), userId);

        T newPlan = (T) setPlanFieldsFromDTO(planCreateOrUpdateDTO, createNewPlan(), userId);
        addPlanItemsFromDTO(planCreateOrUpdateDTO, newPlan);

        return planRepository.save(newPlan);
    }

    public T updatePlan(PlanCreateOrUpdateDTO planCreateOrUpdateDTO, UUID planId, UUID userId) {
        T plan = planRepository.findByIdAndModuleUserId(planId, userId)
                .orElseThrow(() -> new NotFoundException("Plan not found"));

        Module module = moduleService.findModuleById(planCreateOrUpdateDTO.getModuleId(), userId);

        // Using the same method to set the fields from the DTO
        setPlanFieldsFromDTO(planCreateOrUpdateDTO, plan, userId);
        addPlanItemsFromDTO(planCreateOrUpdateDTO, plan);

        return planRepository.save(plan);
    }

    public void deletePlan(UUID planId, UUID userId) {
        planRepository.deleteByIdAndModuleUserId(planId, userId);
    }

    public List<T> listPlansByUserId(UUID userId) {
        return planRepository.findByModuleUserId(userId);
    }

    public T findPlanById(UUID planId, UUID userId) {
        return planRepository.findByIdAndModuleUserId(planId, userId)
                .orElseThrow(() -> new NotFoundException("Plan not found"));
    }

    protected abstract String generatePrompt(String input);

    /**
     * Hook method to prepare the final input string we send to generatePrompt(...).
     * By default, just returns the original user input.
     * Subclasses can override to add user-specific data or other dynamic info.
     */
    protected String preparePromptInput(PlanAiGenerateDTO dto, UUID userId) {
        // Default behavior: return dto.getPrompt() unchanged
        return dto.getPrompt();
    }

    public T aiGeneratePlan(PlanAiGenerateDTO planAiGenerateDTO, UUID userId) {
        Module module = moduleService.findModuleById(planAiGenerateDTO.getModuleId(), userId);

        String finalInput = preparePromptInput(planAiGenerateDTO, userId);
        String promptToSend = generatePrompt(finalInput);
        String response = openAiService.getResponse(promptToSend);

        JsonNode jsonContent;
        try {
            jsonContent = objectMapper.readTree(response);
        } catch (IOException e) {
            throw new OpenAIAPIError(String.format("Invalid OpenAI response format: %s", e.getMessage()));
        }

        if (jsonContent.isArray() && jsonContent.size() > 0) {
            jsonContent = jsonContent.get(0);
        }

        T studyPlan = createNewPlan();
        studyPlan.setTitle(jsonContent.get("title").asText());
        studyPlan.setModule(module);

        if (jsonContent.has("topics") && jsonContent.get("topics").isArray()) {
            List<U> items = new ArrayList<>();
            for (JsonNode topic : jsonContent.get("topics")) {
                U planItem = createNewPlanItem();

                // Campos básicos (presentes em todos os cenários)
                planItem.setTitle(topic.get("title").asText());
                planItem.setDescription(topic.get("description").asText());

                // Hook method para preencher campos adicionais
                // (por padrão, não faz nada; subclasses podem sobrescrever)
                populatePlanItemFromJson(planItem, topic);

                planItem.setPlan(studyPlan);
                items.add(planItem);
            }
            studyPlan.getPlanItems().addAll(items);
        } else {
            throw new OpenAIAPIError("Expected topics array in OpenAI response");
        }

        planRepository.save(studyPlan);

        return studyPlan;
    }

    /**
     * HOOK METHOD:
     * Permite que subclasses preencham campos adicionais sem sobrescrever todo o método.
     */
    protected void populatePlanItemFromJson(U planItem, JsonNode topicJson) {
        // Padrão: não faz nada.
        // Subclasses podem sobrescrever para lidar com campos adicionais.
    }

}
