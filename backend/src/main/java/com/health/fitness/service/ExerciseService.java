package com.health.fitness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.health.fitness.entity.Exercise;
import com.health.fitness.mapper.ExerciseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseMapper exerciseMapper;

    public List<Exercise> getExercises(String category, String search) {
        LambdaQueryWrapper<Exercise> wrapper = new LambdaQueryWrapper<>();
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Exercise::getCategory, category);
        }
        if (search != null && !search.isEmpty()) {
            wrapper.like(Exercise::getName, search);
        }
        return exerciseMapper.selectList(wrapper);
    }

    public Exercise getExerciseById(Long id) {
        return exerciseMapper.selectById(id);
    }
}
