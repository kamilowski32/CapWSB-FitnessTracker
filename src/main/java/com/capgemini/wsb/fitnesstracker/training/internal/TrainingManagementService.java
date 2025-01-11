package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TrainingManagementService implements TrainingProvider {

    private static final Logger log = LoggerFactory.getLogger(TrainingManagementService.class);
    private final TrainingRepository repository;

    public TrainingManagementService(TrainingRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Training> getAllTrainings() {
        log.info("Getting all trainings");
        return repository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        log.info("Getting all trainings by user {}", userId);
        return repository.findByUserId(userId);
    }


    @Override
    public List<Training> getCompletedTrainingsSince(Date date) {
        log.info("Getting completed trainings since {}", date);
        return repository.findByAfterTime(date);
    }

    @Override
    public List<Training> filterByActivity(ActivityType activityType) {
        log.info("Filtering by activity {}", activityType);
        return repository.findByActivityType(activityType);
    }

    public Training saveTraining(Training training) {
        log.info("Saving training {}", training);
        return repository.save(training);
    }

    public Training modifyTraining(Long id, Training training) {
        log.info("Modifying training with ID: {}", id);
        Training old = repository.findById(id).orElseThrow(() -> new TrainingNotFoundException(id));

        old.setUser(training.getUser());
        old.setActivityType(training.getActivityType());
        old.setStartTime(training.getStartTime());
        old.setEndTime(training.getEndTime());
        old.setDistance(training.getDistance());
        old.setAverageSpeed(training.getAverageSpeed());
        return repository.save(old);
    }
}
