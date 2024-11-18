package com.javalover;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "COURSE SERVICE",version = "v 3.0",description = "Course API Crud Operation"))
public class CourseOmkarProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseOmkarProjectApplication.class, args);
    }

}
