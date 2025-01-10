package de.trion.trainings;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaVersionTest {

    @Test
    void is_latest_lts_version() {
        int expectedVersion = 21;

        Runtime.Version version = Runtime.version();
        Optional<String> buildInfo = version.optional();
        int actualVersion = version.feature();

        assertThat(buildInfo).contains("LTS");
        assertThat(actualVersion).isEqualTo(expectedVersion);
    }

}
