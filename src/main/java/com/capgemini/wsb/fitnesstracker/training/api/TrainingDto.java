package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.annotation.Nullable;

import java.util.Date;

public record TrainingDto
        (
    @Nullable Long id,
    User user,
    Date startDate,
    Date endDate,
    ActivityType activityType,
    double distance,
    double averageSpeed
        ) {}
