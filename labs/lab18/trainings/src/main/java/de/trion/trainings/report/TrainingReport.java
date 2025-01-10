package de.trion.trainings.report;

public class TrainingReport {

    private final String numberOfTrainingsPerLocation;
    private final String averageFreeSeats;

    public TrainingReport(String numberOfTrainingsPerLocation, String averageFreeSeats) {
        this.numberOfTrainingsPerLocation = numberOfTrainingsPerLocation;
        this.averageFreeSeats = averageFreeSeats;
    }

    public String getNumberOfTrainingsPerLocation() {
        return numberOfTrainingsPerLocation;
    }

    public String getAverageFreeSeats() {
        return averageFreeSeats;
    }

}
