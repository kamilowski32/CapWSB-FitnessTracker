package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }


    @Override
    public List<Training> getFinishedTrainings(Date date) {
        return trainingRepository.findByAfterTime(date);
    }

    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    public Training updateTraining(Long id, Training training) {
        Training old = trainingRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException(id));

        old.setUser(training.getUser());
        old.setActivityType(training.getActivityType());
        old.setStartTime(training.getStartTime());
        old.setEndTime(training.getEndTime());
        old.setDistance(training.getDistance());
        old.setAverageSpeed(training.getAverageSpeed());
        return trainingRepository.save(old);
    }
}
