package com.driveit.driveit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DriveitApplication {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DriveitApplication.class);
        Environment env = app.run(args).getEnvironment();

        String port = env.getProperty("server.port");
        String swaggerPath = env.getProperty("springdoc.api-docs.path");

        System.out.println("L'application est lancée.");
        System.out.println("Swagger UI est accessible à: http://localhost:" + port + swaggerPath);
    }
}
