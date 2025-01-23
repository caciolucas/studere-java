package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.framework.models.Goal;
import com.studere.studerejava.framework.models.Module;
import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import com.studere.studerejava.framework.repositories.GoalRepository;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.UUID;

public abstract class GoalService<T extends Goal, D extends GoalCreateOrUpdateDTO> {
    protected final GoalRepository<T> goalRepository;
    protected final ModuleService moduleService;

    public GoalService(GoalRepository<T> goalRepository, ModuleService moduleService) {
        this.goalRepository = goalRepository;
        this.moduleService = moduleService;
    }

    protected abstract T createNewGoal();

    protected void setOtherFields(T goal, D goalCreateOrUpdateDTO) {
        // Override this method in the subclass if you have any additional fields to set
    }

    public T createGoal(D goalCreateOrUpdateDTO, UUID userId) throws MethodArgumentNotValidException {
        T newGoal = createNewGoal();

        validateGoal(goalCreateOrUpdateDTO);

        Module module = moduleService.findModuleById(goalCreateOrUpdateDTO.getModuleId(), userId);

        newGoal.setTitle(goalCreateOrUpdateDTO.getTitle());
        newGoal.setDescription(goalCreateOrUpdateDTO.getDescription());
        newGoal.setModule(module);
        setOtherFields(newGoal, goalCreateOrUpdateDTO);

        return goalRepository.save(newGoal);
    }

    protected abstract void validateGoal(D goalCreateOrUpdateDTO) throws MethodArgumentNotValidException;

    public T updateGoal(D goalCreateOrUpdateDTO, UUID goalId, UUID userId) throws MethodArgumentNotValidException {
        T goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new NotFoundException("Goal not found"));

        validateGoal(goalCreateOrUpdateDTO);

        goal.setTitle(goalCreateOrUpdateDTO.getTitle());
        goal.setDescription(goalCreateOrUpdateDTO.getDescription());
        return goalRepository.save(goal);
    }

    protected MethodParameter getMethodParameter() {
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
