package de.trion.trainings.training;

import de.trion.trainings.training.filter.TrainingFilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.convention.TestBean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingFilterServiceTest {

    @TestBean
    Clock clock;

    @Autowired
    private TrainingFilterService trainingFilterService;

    @Test
    void findTrainingsForFilter_returns_trainings_for_today() {
        String filterName = "today";

        List<Training> trainings = trainingFilterService.findTrainingsForFilter(filterName);

        assertThat(trainings).hasSize(1);
        assertThat(trainings).allSatisfy(training -> {
            assertThat(training.getTopic()).isEqualTo("Java 11-21");
            assertThat(training.getId()).isEqualTo("00ffdf86-49d9-4d7b-8a6d-995369cd3d67");
        });

    }

    /**
     * Stellt eine Clock mit einer festen Zeit für den Test bereit.
     */
    private static Clock clock() {
        return Clock.fixed(
                Instant.parse("2024-12-16T08:00:00.00Z"),
                ZoneId.of("Europe/Berlin")
        );
    }
}