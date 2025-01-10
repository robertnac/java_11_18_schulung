package de.trion.trainings.training;

import de.trion.trainings.training.filter.TrainingFilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingFilterServiceTest {

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
}