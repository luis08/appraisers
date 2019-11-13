package com.appraisers;

import com.appraisers.resources.AssignmentResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = AssignmentResource.class)
public class AssignmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentsApplication.class, args);
	}
}
