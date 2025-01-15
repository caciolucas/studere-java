package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.framework.models.Module;
import com.studere.studerejava.framework.models.dto.request.ModuleCreateOrUpdateDTO;
import com.studere.studerejava.framework.repositories.ModuleRepository;

import java.util.List;
import java.util.UUID;

public abstract class ModuleService<T extends Module> {
    private final ModuleRepository<T> moduleRepository;

    public ModuleService(ModuleRepository<T> moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    protected abstract T createNewModule();

    public T createModule(ModuleCreateOrUpdateDTO moduleCreateOrUpdateDTO) {
        T newModule = createNewModule();
        newModule.setName(moduleCreateOrUpdateDTO.getName());
        newModule.setDescription(moduleCreateOrUpdateDTO.getDescription());
        return moduleRepository.save(newModule);
    }

    public T updateModule(ModuleCreateOrUpdateDTO moduleCreateOrUpdateDTO, UUID moduleId, UUID userId) {
        T module = moduleRepository.findByIdAndUserId(moduleId, userId)
                .orElseThrow(() -> new NotFoundException("Module not found"));

        module.setName(moduleCreateOrUpdateDTO.getName());
        module.setDescription(moduleCreateOrUpdateDTO.getDescription());
        return moduleRepository.save(module);
    }

    public void deleteModule(UUID moduleId, UUID userId) {
        moduleRepository.deleteByIdAndUserId(moduleId, userId);
    }

    public T getModuleById(UUID moduleId, UUID userId) {
        return moduleRepository.findByIdAndUserId(moduleId, userId)
                .orElseThrow(() -> new NotFoundException("Module not found"));
    }

    public List<T> listModulesByUserId(UUID userId) {
        return moduleRepository.findByUserId(userId);
    }


}
