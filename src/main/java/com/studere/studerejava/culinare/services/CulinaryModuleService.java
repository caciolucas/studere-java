package com.studere.studerejava.culinare.services;

import com.studere.studerejava.culinare.models.CulinaryModule;
import com.studere.studerejava.framework.repositories.ModuleRepository;
import com.studere.studerejava.framework.services.ModuleService;
import org.springframework.stereotype.Service;

@Service
public class CulinaryModuleService extends ModuleService<CulinaryModule> {
    public CulinaryModuleService(ModuleRepository<CulinaryModule> moduleRepository) {
        super(moduleRepository);
    }

    @Override
    protected CulinaryModule createNewModule() {
        return new CulinaryModule();
    }
}
