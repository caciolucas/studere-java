package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import com.studere.studerejava.framework.repositories.GoalRepository;
import com.studere.studerejava.framework.services.GoalService;
import com.studere.studerejava.framework.services.ModuleService;
import com.studere.studerejava.studere.models.Activity;
import com.studere.studerejava.studere.models.dto.ActivityCreateOrUpdateDTO;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class ActivityService extends GoalService<Activity, ActivityCreateOrUpdateDTO> {

    public ActivityService(GoalRepository<Activity> goalRepository, ModuleService moduleService) {
        super(goalRepository, moduleService);
    }

    @Override
    protected Activity createNewGoal() {
        return new Activity();
    }

    @Override
    public void setOtherFields(Activity goal, ActivityCreateOrUpdateDTO goalCreateOrUpdateDTO) {
        goal.setDueAt(goalCreateOrUpdateDTO.getDueAt());
    }

    @Override
    protected void validateGoal(ActivityCreateOrUpdateDTO goalCreateOrUpdateDTO) throws MethodArgumentNotValidException {

        if (goalCreateOrUpdateDTO.getDescription().length() >= 255) {
            BindingResult bindingResult =
                    new BeanPropertyBindingResult(goalCreateOrUpdateDTO, "goalCreateOrUpdateDTO");

            bindingResult.rejectValue(
                    "description",          // The field name
                    "description.tooLong",  // A message/code key (optional)
                    "Description cannot exceed 255 characters" // Default error message
            );

            MethodParameter methodParameter = null;
            try {
                methodParameter = new MethodParameter(
                        this.getClass().getDeclaredMethod("validateGoal", GoalCreateOrUpdateDTO.class),
                        0
                );
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            throw new MethodArgumentNotValidException(methodParameter, bindingResult);
        }
    }
}
