package de.trion.trainings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TrainingsApplication {

    public static void main(String[] args) {

        String info = """
                ----------------------------------------
                    Welcome to Java 11-21 Update!
                ----------------------------------------
                Java Version:       %s
                Java Vendor:        %s
                OS Name:            %s
                OS Version:         %s
                User Name:          %s
                Available CPUs:     %d
                ----------------------------------------
                """.formatted(
                System.getProperty("java.version"),
                System.getProperty("java.vendor"),
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("user.name"),
                Runtime.getRuntime().availableProcessors()
        );

        System.out.println(info);

        SpringApplication.run(TrainingsApplication.class, args);
    }

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
