package de.trion.trainings.training.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingSearchServiceTest {

    @Autowired
    private TrainingSearchService service;

    @Test
    void isFuzzyMatch_returns_true_for_equal_strings() {
        String trainingName = "test";
        String query = "test";

        assertThat(service.isFuzzyMatch(trainingName, query)).isTrue();
    }

    @Test
    void isFuzzyMatch_returns_false_for_different_strings() {
        String trainingName = "test";
        String query = "trion";

        assertThat(service.isFuzzyMatch(trainingName, query)).isFalse();
    }

    @Test
    void isFuzzyMatch_returns_true_for_word_match() {
        String trainingName = "trion training";
        String query = "trion";

        assertThat(service.isFuzzyMatch(trainingName, query)).isTrue();
    }
}