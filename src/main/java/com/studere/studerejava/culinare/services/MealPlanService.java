package com.studere.studerejava.culinare.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.studere.studerejava.culinare.models.Ingredient;
import com.studere.studerejava.culinare.models.Meal;
import com.studere.studerejava.culinare.models.MealPlan;
import com.studere.studerejava.culinare.models.dto.MealDTO;
import com.studere.studerejava.framework.models.dto.request.PlanAiGenerateDTO;
import com.studere.studerejava.framework.models.dto.request.PlanCreateOrUpdateDTO;
import com.studere.studerejava.framework.models.dto.request.PlanItemDTO;
import com.studere.studerejava.framework.repositories.PlanRepository;
import com.studere.studerejava.framework.services.ModuleService;
import com.studere.studerejava.framework.services.OpenAiService;
import com.studere.studerejava.framework.services.PlanService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MealPlanService extends PlanService<MealPlan, Meal> {

    public MealPlanService(PlanRepository<MealPlan> planRepository,
                           ModuleService moduleService,
                           OpenAiService openAiService) {
        super(planRepository, moduleService, openAiService);
    }

    /**
     * Hook method to prepare the final input string we send to generatePrompt(...).
     * For example, parse a comma-separated list of ingredients and build a
     * more detailed prompt for the AI.
     */
    @Override
    protected String preparePromptInput(PlanAiGenerateDTO dto, UUID userId) {
        // Example: we assume dto.getPrompt() is something like: "milk, eggs, flour"
        String rawIngredients = dto.getPrompt();
        if (rawIngredients == null || rawIngredients.isBlank()) {
            // Fallback
            return "No ingredients provided.";
        }

        // Split by commas, trim whitespace
        String[] ingredientsArray = rawIngredients.split(",");
        List<String> ingredientsList = Arrays.stream(ingredientsArray)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        // Build a string mentioning these ingredients:
        // e.g. "The user wants to cook with: milk, eggs, flour."
        String joinedIngredients = String.join(", ", ingredientsList);

        return String.format(
                "The user wants to create a meal plan using the following ingredients: %s.\n" +
                        "Consider breakfasts, lunches, and dinners. Provide instructions and suggestions.\n",
                joinedIngredients
        );
    }

    /**
     * Example of how to tell the LLM to output a structured JSON that
     * represents a MealPlan, including meals and their ingredients.
     */
    @Override
    protected String generatePrompt(String input) {
        // 'input' now contains the custom text from preparePromptInput(...)
        // We'll embed it in AI instructions:
        return String.format("""
                        Baseado nas informações fornecidas, retorne um JSON no seguinte formato
                        que representará um plano de refeições para o usuário, incluindo os ingredientes
                        especificados. Cada objeto deve ter a seguinte estrutura:
                        
                        {
                          "title": "Meal Plan Title",
                          "meals": [
                            {
                              "title": "Meal Name or Type (e.g., Breakfast)",
                              "description": "A short description or instructions",
                              "ingredients": [
                                {
                                  "name": "Ingredient name",
                                  "quantity": 0,
                                  "unit": "grams"
                                }
                              ]
                            }
                          ]
                        }
                        
                        %s
                        
                        NÃO RETORNE EM MARKDOWN, RETORNE PURAMENTE O JSON A SER PARSEADO.
                        O RETORNO DEVE SER SOMENTE O JSON COM O CONTEÚDO, SEM MAIS TEXTO.
                        """,
                input
        );
    }

    @Override
    protected void addPlanItemsFromDTO(PlanCreateOrUpdateDTO<? extends PlanItemDTO> dto, MealPlan plan) {
        // 1. Clear existing plan items
        plan.getPlanItems().clear();

        // 2. Convert each DTO item into a Meal entity
        plan.getPlanItems().addAll(
                dto.getItems().stream().map(itemDto -> {
                    Meal meal = createNewPlanItem();  // Calls your override that returns `new Meal()`

                    // Basic fields from PlanItemDTO
                    meal.setTitle(itemDto.getTitle());
                    meal.setDescription(itemDto.getDescription());
                    meal.setPlan(plan);

                    // Now handle the custom fields if it's a MealDTO
                    if (itemDto instanceof MealDTO mealDTO) {
                        // Convert MealDTO.ingredients -> List<Ingredient>
                        List<Ingredient> ingredientEntities = new ArrayList<>();
                        if (mealDTO.getIngredients() != null) {
                            ingredientEntities = mealDTO.getIngredients().stream().map(ingDto -> {
                                Ingredient ing = new Ingredient();
                                ing.setName(ingDto.getName());
                                ing.setProteinsPerUnit(ingDto.getProteinsPerUnit());
                                ing.setCarbsPerUnit(ingDto.getCarbsPerUnit());
                                ing.setFatsPerUnit(ingDto.getFatsPerUnit());
                                ing.setCaloriesPerUnit(ingDto.getCaloriesPerUnit());

                                return ing;
                            }).toList();
                        }
                        meal.setIngredients(ingredientEntities);
                    }

                    return meal;
                }).toList()
        );
    }


    @Override
    protected void populatePlanItemFromJson(Meal planItem, JsonNode topicJson) {
        // The base logic in aiGeneratePlan(...) sets planItem.setTitle(...) etc.
        // Now we handle additional fields from the JSON:

        if (topicJson.has("ingredients") && topicJson.get("ingredients").isArray()) {
            List<Ingredient> ingredientList = new ArrayList<>();
            for (JsonNode ingNode : topicJson.get("ingredients")) {
                Ingredient ing = new Ingredient();
                ing.setName(ingNode.get("name").asText(""));
                // if you have quantity or other fields:
                // you could create a MealIngredient or set a custom field, etc.

                ingredientList.add(ing);
            }
            planItem.setIngredients(ingredientList);
        }

        // if there's a "prepTime" field or something else:
        if (topicJson.has("prepTime")) {
            int prepTime = topicJson.get("prepTime").asInt(0);
            // planItem.setPrepTime(prepTime);
        }
    }


    @Override
    protected MealPlan createNewPlan() {
        return new MealPlan();
    }

    @Override
    protected Meal createNewPlanItem() {
        return new Meal();
    }
}
