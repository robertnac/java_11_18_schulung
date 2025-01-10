package de.trion.trainings.training.search;

import de.trion.trainings.training.Training;
import de.trion.trainings.training.TrainingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingSearchService {

    private final TrainingService trainingService;

    public TrainingSearchService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    public List<TrainingSearchResult> findByName(String name) {

        List<Training> trainings = trainingService.findAll();
        List<TrainingSearchResult> results = new ArrayList<>();

        // Empty query = match all
        if (name == null || name.isEmpty()) {
            for (Training training : trainings) {
                results.add(new TrainingSearchResult(MatchType.EXACT, training));
            }
            return results;
        }

        results = findExactMatches(trainings, name);
        results.addAll(findFuzzyMatches(trainings, name));

        return results;
    }

    private List<TrainingSearchResult> findExactMatches(List<Training> trainings, String name) {
        return trainings.stream()
                .filter(training -> isExactMatch(training, name))
                .map(training -> new TrainingSearchResult(MatchType.EXACT, training))
                .collect(Collectors.toList());
    }

    private boolean isExactMatch(Training training, String name) {
        return training.getTopic().equalsIgnoreCase(name);
    }


    private List<TrainingSearchResult> findFuzzyMatches(List<Training> trainings, String name) {
        return trainings.stream()
                .filter(training -> !isExactMatch(training, name))
                .filter(training -> isFuzzyMatch(training, name))
                .map(training -> new TrainingSearchResult(MatchType.FUZZY, training))
                .toList();
    }

    private boolean isFuzzyMatch(Training training, String name) {
        return isFuzzyMatch(training.getTopic(), name);
    }

    public boolean isFuzzyMatch(String trainingName, String query) {
        String[] trainingNameParts = trainingName.split("\\s");
        String[] queryNameParts = query.split("\\s");

        for (String trainingNamePart : trainingNameParts) {
            for (String queryNamePart : queryNameParts) {
                if (queryNamePart.equalsIgnoreCase(trainingNamePart)) {
                    return true;
                }
            }
        }

        return false;
    }

}
