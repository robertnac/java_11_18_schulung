package de.trion.trainings.training.search;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingSearchServiceTest {

    @Autowired
    private TrainingSearchService service;

    @ParameterizedTest
    @CsvSource(delimiter = '|', useHeadersInDisplayName = true,
            textBlock = """
                    Training Name  | Query | Expected
                    test           | test  | true
                    test           | trion | false
                    trion training | trion | true
                    """)
    void testIsFuzzyMatch(String trainingName, String query, boolean expected) {
        assertThat(
                service.isFuzzyMatch(trainingName, query)
        ).isEqualTo(expected);
    }
}