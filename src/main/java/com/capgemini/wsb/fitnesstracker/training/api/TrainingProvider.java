package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;

public interface TrainingProvider {

    List<Training> getAllTrainings();
    List<Training> getTrainingsByUserId(Long userId);
    List<Training> getCompletedTrainingsSince(Date date);
    List<Training> filterByActivity(ActivityType activityType);


}
