package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;


    @GetMapping()
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public List<Training> getTrainingsByID(@PathVariable long id) {
        return trainingService.getTrainingsByUserId(id);
    }

    @GetMapping("/activityType")
    public List<Training> getTrainingsByActivityType(@RequestParam ("activityType") ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType);
    }

    @GetMapping("/finished/{aftertime}")
    public List<Training> getTrainingsAfter(@PathVariable  @DateTimeFormat(pattern = "yyyy-MM-dd") Date aftertime) {
        return trainingService.getFinishedTrainings(aftertime);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Training saveTraining(@RequestBody TrainingWithUserId training) {
        Training trainingFullData = trainingMapper.toEntity(training);
        return trainingService.saveTraining(trainingFullData);
    }

    @PutMapping("/{id}")
    public Training updateTraining(@PathVariable Long id, @RequestBody TrainingWithUserId training) {
        Training trainingFullData = trainingMapper.toEntity(training);
        return trainingService.updateTraining(id, trainingFullData);
    }

}