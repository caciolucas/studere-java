package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.repositories.PlanRepository;
import com.studere.studerejava.framework.services.ModuleService;
import com.studere.studerejava.framework.services.OpenAiService;
import com.studere.studerejava.framework.services.PlanService;
import com.studere.studerejava.studere.models.Course;
import com.studere.studerejava.studere.models.StudyPlan;
import com.studere.studerejava.studere.models.StudyPlanTopic;
import org.springframework.stereotype.Service;

@Service
public class StudyPlanService extends PlanService<StudyPlan, StudyPlanTopic> {

    public StudyPlanService(PlanRepository<StudyPlan> planRepository, ModuleService<Course> moduleService, OpenAiService openAiService) {
        super(planRepository, moduleService, openAiService);
    }

    @Override
    public String generatePrompt(String input) {
        return String.format("""
                    Baseada na descrição informada, retorne um json no formato abaixo que representará um plano de estudo para os assuntos definidos na descrição:
                    [{{
                       "title": "Task name",
                       "topics": [
                          {{
                             "title": "Task activity name",
                             "description": "Description of this task",
                          }}
                       ]
                    }}]
                
                    %s
                
                    NÃO RETORNE EM MARKDOWN, RETORNE PURAMENTE O JSON A SER PARSEADO
                    O RETORNO DEVE SER SOMENTE O JSON COM O CONTEÚDO, NÃO RETORNE NADA ALÉM DO JSON, NENHUM OUTRO CONTEÚDO
                """, input);
    }

    @Override
    protected StudyPlan createNewPlan() {
        return new StudyPlan();
    }

    @Override
    protected StudyPlanTopic createNewPlanItem() {
        return new StudyPlanTopic();
    }
}
