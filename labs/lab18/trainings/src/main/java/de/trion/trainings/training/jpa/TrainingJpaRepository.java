package de.trion.trainings.training.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingJpaRepository extends JpaRepository<TrainingEntity, String> {

    <T> List<T> findByTopicContainingIgnoreCase(String topic, Class<T> type);

}
