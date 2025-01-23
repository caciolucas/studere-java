package com.studere.studerejava.culinare;

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
        SpringApplication.run(CulinareJavaApplication.class, args);
    }
}
