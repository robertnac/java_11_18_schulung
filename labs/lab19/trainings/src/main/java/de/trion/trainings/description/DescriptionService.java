package de.trion.trainings.description;

import de.trion.trainings.training.Training;
import org.springframework.stereotype.Service;

record RemoteTraining(String url) implements TrainingLocation {
}

record OnSiteTraining(Address address) implements TrainingLocation {
}

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
        return switch (location) {
            case RemoteTraining(String url) -> String.format("Join Link: %s", url);
            case OnSiteTraining(Address(String city, String street)) ->
                    String.format("Training Location: %s, %s", city, street);
        };
    }

}
