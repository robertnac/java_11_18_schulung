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
        if (location instanceof RemoteTraining) {
            RemoteTraining remoteTraining = (RemoteTraining) location;
            return String.format("Join Link: %s", remoteTraining.url());
        }
        if (location instanceof OnSiteTraining) {
            OnSiteTraining onSiteTraining = (OnSiteTraining) location;
            return String.format("Training Location: %s, %s", onSiteTraining.address().city(), onSiteTraining.address().street());
        }
        throw new IllegalArgumentException("Unknown Training Type");
    }

}
