package com.health.fitness.controller;

import com.health.fitness.common.Result;
import com.health.fitness.entity.Food;
import com.health.fitness.service.FoodService;
import com.health.fitness.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food")
@CrossOrigin
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private LLMService llmService;

    @GetMapping("/list")
    public Result<List<Food>> getFoods(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        return Result.success(foodService.getFoods(category, search));
    }

    @GetMapping("/{id}")
    public Result<Food> getFoodById(@PathVariable Long id) {
        return Result.success(foodService.getFoodById(id));
    }

    @PostMapping("/ai/query")
    public Result<Map<String, Object>> queryFoodByAI(@RequestBody Map<String, String> request) {
        String foodName = request.get("name");
        Map<String, Object> result = llmService.queryFoodCalories(foodName);

        // 保存到数据库
        Food food = new Food();
        food.setName((String) result.get("name"));
        food.setCalories((Integer) result.get("calories"));
        food.setCategory((String) result.get("category"));
        food.setDescription((String) result.get("description"));
        foodService.saveFood(food);

        return Result.success("查询成功", result);
    }
}
