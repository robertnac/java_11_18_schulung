package de.trion.trainings.description;

import de.trion.trainings.training.Training;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
class TrainingLocationService {

    private static final String REMOTE = "Remote";
    private static final String ON_SITE = "On-Site";

    private final Random random = new Random();

    private final List<Address> addresses = List.of(
            new Address("Berlin", "Alexanderplatz 1"),
            new Address("München", "Marienplatz 5"),
            new Address("Hamburg", "Reeperbahn 10"),
            new Address("Köln", "Domplatz 4"),
            new Address("Stuttgart", "Schlossplatz 3"),
            new Address("Frankfurt", "Zeil 12"),
            new Address("Dresden", "Neumarkt 2"),
            new Address("Leipzig", "Markt 8"),
            new Address("Düsseldorf", "Königsallee 7"),
            new Address("Nürnberg", "Hauptmarkt 9")
    );

    TrainingLocation getTrainingLocation(Training training) {
        if (training.getLocation().equals(ON_SITE)) {
            // TODO: return new OnSiteTraining(getRandomAddress());
        } else if (training.getLocation().equals(REMOTE)) {
            // TODO return new RemoteTraining(getRandomJoinUrl());
        }

        return null;
    }

    private Address getRandomAddress() {
        int index = random.nextInt(addresses.size());
        return addresses.get(index);
    }

    private String getRandomJoinUrl() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return "https://trion-training.de/join/" + uuid;
    }

}
