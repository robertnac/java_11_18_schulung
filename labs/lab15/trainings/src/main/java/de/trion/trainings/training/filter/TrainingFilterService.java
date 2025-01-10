package de.trion.trainings.training.filter;

import de.trion.trainings.training.Training;
import de.trion.trainings.training.TrainingService;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingFilterService {

    private final TrainingService trainingService;
    private final Clock clock;

    public TrainingFilterService(TrainingService trainingService, Clock clock) {
        this.trainingService = trainingService;
        this.clock = clock;
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
                filter = training -> training.getStartTime().toLocalDate().equals(LocalDate.now(clock));
                break;

            case "freeSeats":
                filter = training -> training.getAvailableSeats() > 0;
                break;

            case "all":
            default:
                filter = TrainingFilter::matchAll;
                break;
        }

        return filter;

    }

    private List<Training> applyFilter(List<Training> trainings, TrainingFilter filter) {
        return trainings.stream()
                .filter(filter::test)
                .toList();
    }

}
