package de.trion.trainings.description;

import de.trion.trainings.training.Training;
import org.springframework.stereotype.Service;

@Service
public class DescriptionService {

    private final TrainingLocationService trainingLocationService;

    DescriptionService(TrainingLocationService trainingLocationService) {
        this.trainingLocationService = trainingLocationService;
    }

    public String getDescription(Training training) {
        TrainingLocation location = trainingLocationService.getTrainingLocation(training);
        return getDescription(location);
    }

    String getDescription(TrainingLocation location) {
        // if (location instanceof RemoteTraining) {
        // ...
        // }

        return "TODO";
    }

}
