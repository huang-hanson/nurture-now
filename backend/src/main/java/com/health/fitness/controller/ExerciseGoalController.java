package com.health.fitness.controller;

import com.health.fitness.common.Result;
import com.health.fitness.entity.ExerciseGoal;
import com.health.fitness.service.ExerciseGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goal")
@CrossOrigin
public class ExerciseGoalController {

    @Autowired
    private ExerciseGoalService exerciseGoalService;

    @PostMapping("/save")
    public Result<ExerciseGoal> saveGoal(@RequestBody ExerciseGoal goal) {
        return Result.success(exerciseGoalService.saveOrUpdateGoal(goal));
    }

    @GetMapping("/user/{userId}")
    public Result<List<ExerciseGoal>> getUserGoals(@PathVariable Long userId) {
        return Result.success(exerciseGoalService.getUserGoals(userId));
    }

    @GetMapping("/user/{userId}/{goalType}")
    public Result<ExerciseGoal> getUserGoalByType(@PathVariable Long userId, @PathVariable String goalType) {
        return Result.success(exerciseGoalService.getUserGoalByType(userId, goalType));
    }
}
