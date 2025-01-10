package de.trion.trainings.report;

import de.trion.trainings.training.Training;
import de.trion.trainings.training.TrainingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainingReportService {

    private final TrainingService trainingService;

    public TrainingReportService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    public TrainingReport getReport() {

        List<Training> trainings = trainingService.findAll();

        String numberOfTrainingsPerLocation = calculateNumberOfTrainingsPerLocation(trainings);
        String averageFreeSeats = calculateAverageFreeSeats(trainings);

        return new TrainingReport(numberOfTrainingsPerLocation, averageFreeSeats);
    }

    public String calculateNumberOfTrainingsPerLocation(List<Training> trainings) {

        Map<String, Long> trainingsAtLocation = new HashMap<>();

        for (Training training : trainings) {

            String location = training.getLocation();

            if (!trainingsAtLocation.containsKey(location)) {
                trainingsAtLocation.put(location, 1L);
                continue;
            }

            long newCount = trainingsAtLocation.get(location) + 1;
            trainingsAtLocation.put(location, newCount);

        }

        StringBuilder trainingsAtLocationString = new StringBuilder();

        for (Map.Entry<String, Long> entry : trainingsAtLocation.entrySet()) {
            trainingsAtLocationString.append(String.format("%s: %d %n", entry.getKey(), entry.getValue()));
        }

        return trainingsAtLocationString.toString();
    }


    public String calculateAverageFreeSeats(List<Training> trainings) {
        int freeSeatsSum = 0;

        for (Training training : trainings) {
            freeSeatsSum += training.getAvailableSeats();
        }

        double avg = (double) freeSeatsSum / trainings.size();
        return String.format("%.2f", avg);
    }


}
