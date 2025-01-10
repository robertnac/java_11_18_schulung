package de.trion.trainings.description;

public sealed interface TrainingLocation
        permits RemoteTraining, OnSiteTraining {
}
