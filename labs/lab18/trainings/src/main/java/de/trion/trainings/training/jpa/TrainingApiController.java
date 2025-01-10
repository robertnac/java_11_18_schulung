package de.trion.trainings.training.jpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainingApiController {

    private final TrainingJpaRepository trainingJpaRepository;

    public TrainingApiController(TrainingJpaRepository trainingJpaRepository) {
        this.trainingJpaRepository = trainingJpaRepository;
    }

    @GetMapping("/catalog/{query}")
    public List<CompactTraining> getCatalog(@PathVariable String query) {
        return trainingJpaRepository.findByTopicContainingIgnoreCase(query, CompactTraining.class);
    }

}
