package de.trion.trainings.training;

public class UnknownTrainingException extends RuntimeException {
   public UnknownTrainingException(IndexOutOfBoundsException ex) {
      super(ex);
   }

   public UnknownTrainingException(String message) {
      super(message);
   }
}
