package com.skill.fusion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkillFusionApplication {
    private static final Logger logger = LoggerFactory.getLogger(SkillFusionApplication.class);

    public static void main(String[] args) {
        logger.trace("Trace level: Starting SkillFusionApplication...");
        logger.debug("Debug level: SkillFusionApplication main method entered.");
        logger.info("Info level: SkillFusionApplication is starting.");
        logger.warn("Warn level: Example of a warning message.");
        logger.error("Error level: Example of an error message.");

        SpringApplication.run(SkillFusionApplication.class, args);
        logger.info("SkillFusionApplication started successfully!");
    }
}
