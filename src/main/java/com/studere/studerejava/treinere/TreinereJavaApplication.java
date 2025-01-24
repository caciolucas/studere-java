package com.studere.studerejava.treinere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.studere.studerejava.framework",
        "com.studere.studerejava.treinere"
})
@EnableJpaRepositories(basePackages = {
        "com.studere.studerejava.framework.repositories",
        "com.studere.studerejava.treinere.repositories"
})
public class TreinereJavaApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TreinereJavaApplication.class);
        app.setAdditionalProfiles("treinere");  // or pass as a JVM arg
        app.run(args);
    }
}
