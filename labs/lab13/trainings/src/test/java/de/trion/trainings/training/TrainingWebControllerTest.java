package de.trion.trainings.training;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainingWebControllerTest {

    @Autowired
    private TrainingWebController controller;

    @Test
    public void testTrainingDescription_ShouldNotBeEmpty() {
        String trainingId = "00ffdf86-49d9-4d7b-8a6d-995369cd3d67";

        ModelAndView mav = controller.training(trainingId);
        var model = mav.getModel();

        assertThat(model).containsKey("description");
        assertThat(model.get("description")).isNotNull();
        assertThat(model.get("description").toString()).isNotBlank();
    }

}