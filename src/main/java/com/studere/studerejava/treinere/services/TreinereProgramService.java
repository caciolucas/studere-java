package com.studere.studerejava.treinere.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.studere.studerejava.framework.repositories.ModuleRepository;
import com.studere.studerejava.framework.services.ModuleService;
import com.studere.studerejava.treinere.models.TreinereProgram;

@Service
public class TreinereProgramService extends ModuleService<TreinereProgram> {
  
  public TreinereProgramService(ModuleRepository<TreinereProgram> moduleRepository) {
    super(moduleRepository);
  }

  @Override
  protected TreinereProgram createNewModule() {
      return new TreinereProgram();
  }

}
