package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {
    private final UserProvider userProvider;

    public TrainingMapper(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    Training toEntity(TrainingWithUserId training) {
        return new Training(
                userProvider.getUserById(training.userId()).orElseThrow(() -> new UserNotFoundException(training.userId())),
                training.startTime(),
                training.endTime(),
                training.activityType(),
                training.distance(),
                training.averageSpeed()
        );
    }
}
