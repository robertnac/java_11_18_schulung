package de.trion.trainings.training;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TrainingRepository {

    private final JdbcClient jdbcClient;

    public TrainingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Training> findAll() {

        var sql = """
                SELECT id,
                       topic,
                       location,
                       instructor,
                       start_time,
                       available_seats
                  FROM tbl_trainings;
                """;

        return jdbcClient.sql(sql)
                .query(Training.class)
                .list();

    }

    /**
     * Ermittelt ein Training anhand seiner ID.
     *
     * @return Training mit der gesuchten ID; null, falls kein Training gefunden wurde.
     */
    public Optional<Training> findById(String id) {

        return jdbcClient.sql("""
                        SELECT id,
                               topic,
                               location,
                               instructor,
                               start_time,
                               available_seats
                        FROM tbl_trainings
                        WHERE id = :id;
                        """)
                .param("id", id)
                .query(Training.class)
                .optional();
    }

    public Training save(Training training) {

        if (training.getId() == null) {
            return insert(training);
        }

        return update(training);
    }

    private Training update(Training training) {

        int updateCount = jdbcClient.sql("""
                        UPDATE tbl_trainings
                        SET topic = :topic,
                            location = :location,
                            instructor = :instructor,
                            start_time = :start_time,
                            available_seats = :available_seats
                        WHERE id = :id;
                        """)
                .params(Map.of(
                        "topic", training.getTopic(),
                        "location", training.getLocation(),
                        "instructor", training.getInstructor(),
                        "start_time", training.getStartTime(),
                        "available_seats", training.getAvailableSeats(),
                        "id", training.getId()
                ))
                .update();

        if (updateCount != 1) {
            throw new UnknownTrainingException(training.getId());
        }

        return findById(training.getId())
                .orElseThrow(() -> new UnknownTrainingException(training.getId()));

    }

    private Training insert(Training training) {

        jdbcClient.sql("""
                        INSERT INTO tbl_trainings
                        (id, topic, location, instructor, start_time, available_seats)
                        VALUES (:id, :topic, :location, :instructor, :start_time, :available_seats);
                        """)
                .params(Map.of(
                        "id", UUID.randomUUID().toString(),
                        "topic", training.getTopic(),
                        "location", training.getLocation(),
                        "instructor", training.getInstructor(),
                        "start_time", training.getStartTime(),
                        "available_seats", training.getAvailableSeats()
                ))
                .update();

        return findById(training.getId())
                .orElseThrow(() -> new UnknownTrainingException(training.getId()));

    }

}
