package com.studere.studerejava.studere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.studere.studerejava.framework",
        "com.studere.studerejava.studere"
})
@EnableJpaRepositories(basePackages = {
        "com.studere.studerejava.framework.repositories",
        "com.studere.studerejava.studere.repositories"
})
public class StudereJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudereJavaApplication.class, args);
    }
}
