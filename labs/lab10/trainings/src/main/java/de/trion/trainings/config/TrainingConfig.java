package de.trion.trainings.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "trion.training")
public class TrainingConfig {
    private String startPageHeading;

    public String getStartPageHeading() {
        return startPageHeading;
    }

    public void setStartPageHeading(String startPageHeading) {
        this.startPageHeading = startPageHeading;
    }
}
