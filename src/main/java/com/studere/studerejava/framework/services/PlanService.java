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
import com.studere.studerejava.framework.repositories.PlanRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    protected abstract String generatePrompt(String input);

    public T createPlan(PlanCreateOrUpdateDTO planCreateOrUpdateDTO, UUID userId) {
        T newPlan = createNewPlan();
        Module module = moduleService.findModuleById(planCreateOrUpdateDTO.getModuleId(), userId);

        newPlan.setTitle(planCreateOrUpdateDTO.getTitle());
        newPlan.setModule(module);


        newPlan.setPlanItems(planCreateOrUpdateDTO.getItems().stream().map(item -> {
            U planItem = createNewPlanItem();
            planItem.setTitle(item.getTitle());
            planItem.setDescription(item.getDescription());
            planItem.setPlan(newPlan);
            return planItem;
        }).collect(Collectors.toList()));
        return planRepository.save(newPlan);
    }

    public T updatePlan(PlanCreateOrUpdateDTO planCreateOrUpdateDTO, UUID planId, UUID userId) {
        T plan = planRepository.findByIdAndUserId(planId, userId)
                .orElseThrow(() -> new NotFoundException("Plan not found"));

        Module module = moduleService.findModuleById(planCreateOrUpdateDTO.getModuleId(), userId);

        plan.setTitle(planCreateOrUpdateDTO.getTitle());
        plan.setModule(module);

        plan.getPlanItems().clear();
        plan.getPlanItems().addAll(planCreateOrUpdateDTO.getItems().stream().map(item -> {
            U planItem = createNewPlanItem();
            planItem.setTitle(item.getTitle());
            planItem.setDescription(item.getDescription());
            planItem.setPlan(plan);
            return planItem;
        }).collect(Collectors.toList()));

        return planRepository.save(plan);
    }

    public void deletePlan(UUID planId, UUID userId) {
        planRepository.deleteByIdAndUserId(planId, userId);
    }

    public List<T> listPlansByUserId(UUID userId) {
        return planRepository.findByUserId(userId);
    }

    public T aiGeneratePlan(PlanAiGenerateDTO planAiGenerateDTO, UUID userId) {
        Module module = moduleService.findModuleById(planAiGenerateDTO.getModuleId(), userId);
        String response = openAiService.getResponse(this.generatePrompt(planAiGenerateDTO.getPrompt()));

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
                planItem.setTitle(topic.get("title").asText());
                planItem.setDescription(topic.get("description").asText());
                planItem.setPlan(studyPlan);
                items.add(planItem);
            }
        } else {
            throw new OpenAIAPIError("Expected topics array in OpenAI response");
        }

        planRepository.save(studyPlan);

        return studyPlan;

    }

    public T findPlanById(UUID planId, UUID userId) {
        return planRepository.findByIdAndUserId(planId, userId)
                .orElseThrow(() -> new NotFoundException("Plan not found"));
    }

}
