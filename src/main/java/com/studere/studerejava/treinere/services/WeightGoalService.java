package com.studere.studerejava.treinere.services;

import com.studere.studerejava.framework.repositories.GoalRepository;
import com.studere.studerejava.framework.services.GoalService;
import com.studere.studerejava.framework.services.ModuleService;
import com.studere.studerejava.treinere.models.WeightGoal;
import com.studere.studerejava.treinere.models.dto.WeightGoalCreateOrUpdateDTO;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class WeightGoalService extends GoalService<WeightGoal, WeightGoalCreateOrUpdateDTO> {
    public WeightGoalService(GoalRepository<WeightGoal> goalRepository, ModuleService moduleService) {
        super(goalRepository, moduleService);
    }

    @Override
    protected WeightGoal createNewGoal() {
        return new WeightGoal();
    }

    @Override
    protected void validateGoal(WeightGoalCreateOrUpdateDTO goalCreateOrUpdateDTO) throws MethodArgumentNotValidException {
        // Check if it's actually a WeightGoalCreateOrUpdateDTO
        if (goalCreateOrUpdateDTO instanceof WeightGoalCreateOrUpdateDTO weightDto) {

            // Validate that both weights are non-negative
            // and target is bigger than initial
            BindingResult bindingResult =
                    new BeanPropertyBindingResult(weightDto, "weightGoalCreateOrUpdateDTO");

            // Custom validations
            if (weightDto.getInitialWeight() < 0) {
                bindingResult.rejectValue(
                        "initialWeight",
                        "initialWeight.negative",
                        "Initial weight cannot be negative"
                );
            }

            if (weightDto.getTargetWeight() < 0) {
                bindingResult.rejectValue(
                        "targetWeight",
                        "targetWeight.negative",
                        "Target weight cannot be negative"
                );
            }

            if (weightDto.getTargetWeight() <= weightDto.getInitialWeight()) {
                bindingResult.rejectValue(
                        "targetWeight",
                        "targetWeight.notGreaterThanInitial",
                        "Target weight must be greater than initial weight"
                );
            }

            if (bindingResult.hasErrors()) {
                MethodParameter methodParameter = getMethodParameter();
                throw new MethodArgumentNotValidException(methodParameter, bindingResult);
            }
        } else {
            throw new IllegalArgumentException("WeightGoalService can only handle WeightGoalCreateOrUpdateDTO");
        }
    }


}
