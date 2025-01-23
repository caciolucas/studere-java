package com.studere.studerejava.culinare.services;

import com.studere.studerejava.culinare.models.CookingGoal;
import com.studere.studerejava.culinare.models.dto.CulinaryGoalCreateOrUpdateDTO;
import com.studere.studerejava.culinare.models.enums.ChefLevel;
import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import com.studere.studerejava.framework.repositories.GoalRepository;
import com.studere.studerejava.framework.services.GoalService;
import com.studere.studerejava.framework.services.ModuleService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class CookingGoalService extends GoalService<CookingGoal, CulinaryGoalCreateOrUpdateDTO> {
    public CookingGoalService(GoalRepository<CookingGoal> goalRepository, ModuleService moduleService) {
        super(goalRepository, moduleService);
    }

    @Override
    protected CookingGoal createNewGoal() {
        return new CookingGoal();
    }

    @Override
    protected void setOtherFields(CookingGoal goal, CulinaryGoalCreateOrUpdateDTO goalCreateOrUpdateDTO) {
        if (goalCreateOrUpdateDTO instanceof CulinaryGoalCreateOrUpdateDTO dto) {
            goal.setChefLevel(dto.getChefLevel());
            goal.setCertificateId(dto.getCertificateId());
        }
    }

    @Override
    protected void validateGoal(CulinaryGoalCreateOrUpdateDTO goalCreateOrUpdateDTO) throws MethodArgumentNotValidException {
        // 1. Perform any generic checks (like you do for description, etc.) if needed.
        // 2. Then do a cast or instanceof check for CulinaryGoalCreateOrUpdateDTO if you have a specialized DTO.
        //    Or if you pass the actual domain DTO, just cast it:

        if (goalCreateOrUpdateDTO instanceof CulinaryGoalCreateOrUpdateDTO dto) {
            // Cross-field check:
            if (dto.getChefLevel() == ChefLevel.PRO && (dto.getCertificateId() == null || dto.getCertificateId().isBlank())) {
                // Build a validation error the same way you handle others:
                BindingResult bindingResult = new BeanPropertyBindingResult(dto, "culinaryGoalDTO");
                bindingResult.rejectValue(
                        "certificateId",
                        "certificateId.requiredForPro",
                        "A valid certificate ID is required when chefLevel is PRO."
                );
                MethodParameter methodParameter = resolveMethodParameter();
                throw new MethodArgumentNotValidException(methodParameter, bindingResult);
            }
        }
    }

    private MethodParameter resolveMethodParameter() {
        try {
            return new MethodParameter(
                    this.getClass().getDeclaredMethod("validateGoal", GoalCreateOrUpdateDTO.class),
                    0
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
