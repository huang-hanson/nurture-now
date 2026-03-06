package com.health.fitness.controller;

import com.health.fitness.common.Result;
import com.health.fitness.entity.TrainingPlan;
import com.health.fitness.entity.TrainingPlanItem;
import com.health.fitness.service.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plan")
@CrossOrigin
public class TrainingPlanController {

    @Autowired
    private TrainingPlanService trainingPlanService;

    @PostMapping("/create")
    public Result<TrainingPlan> createPlan(@RequestBody Map<String, Object> request) {
        TrainingPlan plan = new TrainingPlan();
        plan.setUserId(Long.valueOf(request.get("userId").toString()));
        plan.setTitle((String) request.get("title"));
        plan.setDescription((String) request.get("description"));
        plan.setStartDate(java.time.LocalDate.parse(request.get("startDate").toString()));
        if (request.get("endDate") != null) {
            plan.setEndDate(java.time.LocalDate.parse(request.get("endDate").toString()));
        }
        plan.setStatus("active");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> itemsData = (List<Map<String, Object>>) request.get("items");

        List<TrainingPlanItem> items = null;
        if (itemsData != null && !itemsData.isEmpty()) {
            items = new java.util.ArrayList<>();
            for (Map<String, Object> itemData : itemsData) {
                TrainingPlanItem item = new TrainingPlanItem();
                item.setExerciseId(Long.valueOf(itemData.get("exerciseId").toString()));
                item.setSets(itemData.get("sets") != null ? Integer.valueOf(itemData.get("sets").toString()) : null);
                item.setReps(itemData.get("reps") != null ? Integer.valueOf(itemData.get("reps").toString()) : null);
                item.setDuration(itemData.get("duration") != null ? Integer.valueOf(itemData.get("duration").toString()) : null);
                items.add(item);
            }
        }

        return Result.success(trainingPlanService.createPlan(plan, items));
    }

    @GetMapping("/user/{userId}")
    public Result<List<TrainingPlan>> getUserPlans(@PathVariable Long userId) {
        return Result.success(trainingPlanService.getUserPlans(userId));
    }

    @GetMapping("/detail/{planId}")
    public Result<TrainingPlan> getPlanDetail(@PathVariable Long planId) {
        return Result.success(trainingPlanService.getPlanDetail(planId));
    }

    @PutMapping("/status/{planId}")
    public Result<TrainingPlan> updatePlanStatus(@PathVariable Long planId, @RequestBody Map<String, String> request) {
        return Result.success(trainingPlanService.updatePlanStatus(planId, request.get("status")));
    }
}
