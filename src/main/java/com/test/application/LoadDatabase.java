package com.test.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.application.department.Department;
import com.test.application.department.DepartmentRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(DepartmentRepository repo) {
		return args -> {
			log.info("Preloading " + repo.save(new Department("Doctor")));
			log.info("Preloading " + repo.save(new Department("Hygenist")));
			log.info("Preloading " + repo.save(new Department("Front Office")));
			log.info("Preloading " + repo.save(new Department("Doctor Assistant")));
			log.info("Preloading " + repo.save(new Department("Hygiene Assistant")));
			log.info("Preloading " + repo.save(new Department("Back Office")));
		};
	}
}
