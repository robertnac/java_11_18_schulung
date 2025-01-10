package de.trion.trainings.training;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Repository
public class TrainingRepository {

    private final JdbcClient jdbcClient;

    public TrainingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Training> findAll() {

        var sql = "SELECT id,\n" +
                  "       topic,\n" +
                  "       location,\n" +
                  "       instructor,\n" +
                  "       start_time,\n" +
                  "       available_seats\n" +
                  "  FROM tbl_trainings;\n";

        return jdbcClient.sql(sql)
                .query(Training.class)
                .list();

    }

    /**
     * Ermittelt ein Training anhand seiner ID.
     *
     * @return Training mit der gesuchten ID; null, falls kein Training gefunden wurde.
     */
    public Training findById(String id) {

        Set<Training> training = jdbcClient.sql("SELECT id,\n" +
                                                "       topic,\n" +
                                                "       location,\n" +
                                                "       instructor,\n" +
                                                "       start_time,\n" +
                                                "       available_seats\n" +
                                                "FROM tbl_trainings\n" +
                                                "WHERE id = :id;\n")
                .param("id", id)
                .query(Training.class)
                .set();

        if (training.isEmpty()) {
            return null;
        }

        return training.iterator().next();

    }

    public Training save(Training training) {

        if (training.getId() == null) {
            return insert(training);
        }

        return update(training);
    }

    private Training update(Training training) {

        int updateCount = jdbcClient.sql("UPDATE tbl_trainings\n" +
                                         "SET topic = :topic,\n" +
                                         "    location = :location,\n" +
                                         "    instructor = :instructor,\n" +
                                         "    start_time = :start_time,\n" +
                                         "    available_seats = :available_seats\n" +
                                         "WHERE id = :id;\n")
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

        return findById(training.getId());

    }

    private Training insert(Training training) {

        jdbcClient.sql("INSERT INTO tbl_trainings\n" +
                       "(id, topic, location, instructor, start_time, available_seats)\n" +
                       "VALUES (:id, :topic, :location, :instructor, :start_time, :available_seats);\n")
                .params(Map.of(
                        "id", UUID.randomUUID().toString(),
                        "topic", training.getTopic(),
                        "location", training.getLocation(),
                        "instructor", training.getInstructor(),
                        "start_time", training.getStartTime(),
                        "available_seats", training.getAvailableSeats()
                ))
                .update();

        return findById(training.getId());

    }

}
