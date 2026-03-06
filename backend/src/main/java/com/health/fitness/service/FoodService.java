package com.health.fitness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.health.fitness.entity.Food;
import com.health.fitness.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodMapper foodMapper;

    public List<Food> getFoods(String category, String search) {
        LambdaQueryWrapper<Food> wrapper = new LambdaQueryWrapper<>();
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Food::getCategory, category);
        }
        if (search != null && !search.isEmpty()) {
            wrapper.like(Food::getName, search);
        }
        return foodMapper.selectList(wrapper);
    }

    public Food getFoodById(Long id) {
        return foodMapper.selectById(id);
    }

    public Food saveFood(Food food) {
        foodMapper.insert(food);
        return food;
    }
}
