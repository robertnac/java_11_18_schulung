package de.trion.trainings.training.filter;

import de.trion.trainings.training.Training;

/**
 * Filter der überprüft, ob ein Training einem Filterkriterium entspricht.
 * <p>
 * Hinweis: In der Praxis sollte stattdessen besser ein {@link java.util.function.Predicate<Training>}
 * verwendet werden.
 */
@FunctionalInterface
public interface TrainingFilter {

    boolean test(Training training);

    static boolean matchAll(Training training) {
        return true;
    }

}
