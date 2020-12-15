package com.mhgf.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Mario Freitas
 */
@SpringBootApplication(scanBasePackages = "com.mhgf.*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
