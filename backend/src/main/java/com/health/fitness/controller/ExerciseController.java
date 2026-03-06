package com.health.fitness.controller;

import com.health.fitness.common.Result;
import com.health.fitness.entity.Exercise;
import com.health.fitness.service.ExerciseService;
import com.health.fitness.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercise")
@CrossOrigin
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private LLMService llmService;

    @GetMapping("/list")
    public Result<List<Exercise>> getExercises(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        return Result.success(exerciseService.getExercises(category, search));
    }

    @GetMapping("/{id}")
    public Result<Exercise> getExerciseById(@PathVariable Long id) {
        return Result.success(exerciseService.getExerciseById(id));
    }

    @PostMapping("/ai/query")
    public Result<Map<String, Object>> queryExerciseByAI(@RequestBody Map<String, String> request) {
        String exerciseName = request.get("name");
        Map<String, Object> result = llmService.queryExerciseCalories(exerciseName);
        return Result.success("查询成功", result);
    }
}
