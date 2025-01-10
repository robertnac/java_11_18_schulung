package de.trion.trainings.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "trion.training")
public record TrainingConfig(String startPageHeading) {
}
