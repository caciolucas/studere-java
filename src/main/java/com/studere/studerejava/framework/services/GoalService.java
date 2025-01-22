package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.framework.models.Goal;
import com.studere.studerejava.framework.models.Module;
import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import com.studere.studerejava.framework.repositories.GoalRepository;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.UUID;

public abstract class GoalService<T extends Goal> {
    protected final GoalRepository<T> goalRepository;
    protected final ModuleService moduleService;

    public GoalService(GoalRepository<T> goalRepository, ModuleService moduleService) {
        this.goalRepository = goalRepository;
        this.moduleService = moduleService;
    }

    protected abstract T createNewGoal();

    public T createGoal(GoalCreateOrUpdateDTO goalCreateOrUpdateDTO, UUID userId) throws MethodArgumentNotValidException {
        T newGoal = createNewGoal();

        validateGoal(goalCreateOrUpdateDTO);

        Module module = moduleService.findModuleById(goalCreateOrUpdateDTO.getModuleId(), userId);

        newGoal.setTitle(goalCreateOrUpdateDTO.getTitle());
        newGoal.setDescription(goalCreateOrUpdateDTO.getDescription());
        return goalRepository.save(newGoal);
    }

    protected abstract void validateGoal(GoalCreateOrUpdateDTO goalCreateOrUpdateDTO) throws MethodArgumentNotValidException;

    public T updateGoal(GoalCreateOrUpdateDTO goalCreateOrUpdateDTO, UUID goalId, UUID userId) throws MethodArgumentNotValidException {
        T goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new NotFoundException("Goal not found"));

        validateGoal(goalCreateOrUpdateDTO);

        goal.setTitle(goalCreateOrUpdateDTO.getTitle());
        goal.setDescription(goalCreateOrUpdateDTO.getDescription());
        return goalRepository.save(goal);
    }
}
