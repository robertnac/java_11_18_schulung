package de.trion.trainings.training.filter;

import de.trion.trainings.training.Training;
import de.trion.trainings.training.TrainingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingFilterService {

    private final TrainingService trainingService;

    public TrainingFilterService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    public List<Training> findTrainingsForFilter(String filterName) {
        List<Training> trainings = trainingService.findAll();
        TrainingFilter filter = getFilterByName(filterName);
        return applyFilter(trainings, filter);
    }

    private TrainingFilter getFilterByName(String filterName) {

        if (filterName == null) {
            filterName = "all";
        }

        TrainingFilter filter;

        switch (filterName) {

            case "today":
                filter = training -> training.getStartTime().toLocalDate().equals(LocalDate.now());
                break;

            case "freeSeats":
                filter = training -> training.getAvailableSeats() > 0;
                break;

            case "all":
            default:
                filter = training -> true;
                break;
        }

        return filter;

    }

    private List<Training> applyFilter(List<Training> trainings, TrainingFilter filter) {

        List<Training> filteredTrainings = new ArrayList<>();

        for (Training training : trainings) {
            if (filter.test(training)) {
                filteredTrainings.add(training);
            }
        }

        return filteredTrainings;
    }

}
