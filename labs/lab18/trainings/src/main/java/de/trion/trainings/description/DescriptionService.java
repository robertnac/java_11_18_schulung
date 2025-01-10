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
        if (location instanceof RemoteTraining(String url)) {
            return String.format("Join Link: %s", url);
        }
        if (location instanceof OnSiteTraining(Address(String city, String street))) {
            return String.format("Training Location: %s, %s", city, street);
        }
        throw new IllegalArgumentException("Unknown Training Type");
    }

}
