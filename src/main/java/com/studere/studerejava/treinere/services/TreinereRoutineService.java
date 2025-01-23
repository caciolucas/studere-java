package com.studere.studerejava.treinere.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.studere.studerejava.framework.models.dto.request.PlanAiGenerateDTO;
import com.studere.studerejava.framework.models.dto.request.PlanCreateOrUpdateDTO;
import com.studere.studerejava.framework.models.dto.request.PlanItemDTO;
import com.studere.studerejava.framework.repositories.PlanRepository;
import com.studere.studerejava.framework.services.ModuleService;
import com.studere.studerejava.framework.services.OpenAiService;
import com.studere.studerejava.framework.services.PlanService;
import com.studere.studerejava.treinere.models.Exercise;
import com.studere.studerejava.treinere.models.TreinereProgram;
import com.studere.studerejava.treinere.models.TreinereRoutine;
import com.studere.studerejava.treinere.models.TreinereUser;
import com.studere.studerejava.treinere.models.dto.ExerciseDTO;
import com.studere.studerejava.treinere.models.dto.TreinereRoutineCreateOrUpdateDTO;
import com.studere.studerejava.treinere.repositories.TreinereUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreinereRoutineService extends PlanService<TreinereRoutine, Exercise> {
    private final TreinereUserRepository treinereUserRepository;

    public TreinereRoutineService(PlanRepository<TreinereRoutine> planRepository, ModuleService<TreinereProgram> moduleService, OpenAiService openAiService, TreinereUserRepository treinereUserRepository) {
        super(planRepository, moduleService, openAiService);
        this.treinereUserRepository = treinereUserRepository;
    }

    @Override
    protected String preparePromptInput(PlanAiGenerateDTO dto, UUID userId) {
        // 1. Get the user from DB (or another service)
        TreinereUser treinereUser = treinereUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("TreinereUser not found for userId=" + userId));

        // 2. Merge user details with the prompt.
        // You can do a more elegant JSON or string builder approach as needed:
        String basePrompt = dto.getPrompt();
        return String.format("""
                        %s
                        User details:
                        - Idade: %d
                        - Sexo: %s
                        - Peso: %.2f
                        - Altura: %.2f
                        - Objetivo: %s
                        - Experiência: %s
                        """,
                basePrompt,
                treinereUser.getAge(),
                treinereUser.getSex(),
                treinereUser.getWeight(),
                treinereUser.getHeight(),
                treinereUser.getObjective(),
                treinereUser.getExperience()
        );
    }

    @Override
    public String generatePrompt(String input) {
        // TODO: Change the prompt generation logic to include users information

        return String.format("""
                Baseada na descrição informada, retorne um json no formato abaixo que representará um plano de treino para o individual abaixo:
                
                [{{
                  "title": "Task name",
                  "topics": [
                    {{
                      "title": "Task activity name",
                      "description": "Description of this task",
                      "weight_suggestion": 0,
                      "sets_suggestion": 0,
                      "reps_suggestion": 0
                    }}
                  ]
                }}]
                
                %s
                
                NÃO RETORNE EM MARKDOWN, RETORNE PURAMENTE O JSON A SER PARSEADO
                O RETORNO DEVE SER SOMENTE O JSON COM O CONTEÚDO, NÃO RETORNE NADA ALÉM DO JSON, NENHUM OUTRO CONTEÚDO
                """, input);
    }

    @Override
    protected void populatePlanItemFromJson(Exercise planItem, JsonNode topicJson) {
        // Campos adicionais do JSON que só fazem sentido em "TreinereRoutine"
        if (topicJson.has("weight_suggestion")) {
            planItem.setWeightSuggestion((float) topicJson.get("weight_suggestion").asDouble(0));
        }
        if (topicJson.has("sets_suggestion")) {
            planItem.setSetsSuggestion(topicJson.get("sets_suggestion").asInt(0));
        }
        if (topicJson.has("reps_suggestion")) {
            planItem.setRepsSuggestion(topicJson.get("reps_suggestion").asInt(0));
        }

        // Caso queira lidar com edge cases (por ex. campos faltando)
        // é só ajustar a lógica aqui
    }

    @Override
    protected TreinereRoutine createNewPlan() {
        return new TreinereRoutine();
    }

    @Override
    protected Exercise createNewPlanItem() {
        return new Exercise();
    }

    @Override
    protected TreinereRoutine setPlanFieldsFromDTO(PlanCreateOrUpdateDTO<? extends PlanItemDTO> dto, TreinereRoutine plan, UUID userId) {
        super.setPlanFieldsFromDTO(dto, plan, userId);

        if (dto instanceof TreinereRoutineCreateOrUpdateDTO treinereDTO) {
            plan.setType(treinereDTO.getType());
        }

        return plan;
    }

    @Override
    protected void addPlanItemsFromDTO(PlanCreateOrUpdateDTO<? extends PlanItemDTO> planCreateOrUpdateDTO, TreinereRoutine treinereRoutine) {
        treinereRoutine.getPlanItems().clear();
        treinereRoutine.getPlanItems().addAll(planCreateOrUpdateDTO.getItems().stream().map(item -> {
            Exercise planItem = createNewPlanItem();
            planItem.setTitle(item.getTitle());
            planItem.setDescription(item.getDescription());
            planItem.setPlan(treinereRoutine);
            if (item instanceof ExerciseDTO exerciseDTO) {
                planItem.setWeightSuggestion(exerciseDTO.getWeightSuggestion());
                planItem.setRepsSuggestion(exerciseDTO.getRepsSuggestion());
                planItem.setSetsSuggestion(exerciseDTO.getSetsSuggestion());
            }
            return planItem;
        }).toList());
    }


}
