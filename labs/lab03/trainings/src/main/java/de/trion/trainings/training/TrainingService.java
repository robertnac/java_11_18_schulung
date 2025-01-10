package de.trion.trainings.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Training getById(String id) {
        Training training = trainingRepository.findById(id);

        if (training == null) {
            throw new UnknownTrainingException("ID not found: " + id);
        }

        return training;
    }

    public Training save(Training training) {
        return trainingRepository.save(training);
    }

    public List<Training> findAll() {
        return trainingRepository.findAll();
    }
}
