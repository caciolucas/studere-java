package com.studere.studerejava.culinare;

import com.studere.studerejava.studere.StudereJavaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.studere.studerejava.framework",
        "com.studere.studerejava.culinare"
})
@EnableJpaRepositories(basePackages = {
        "com.studere.studerejava.framework.repositories",
        "com.studere.studerejava.culinare.repositories"
})
public class CulinareJavaApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StudereJavaApplication.class);
        app.setAdditionalProfiles("culinare");  // or pass as a JVM arg
        app.run(args);
    }
}
