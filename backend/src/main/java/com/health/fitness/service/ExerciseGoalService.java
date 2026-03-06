package com.health.fitness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.health.fitness.entity.ExerciseGoal;
import com.health.fitness.mapper.ExerciseGoalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseGoalService {

    @Autowired
    private ExerciseGoalMapper exerciseGoalMapper;

    public ExerciseGoal saveOrUpdateGoal(ExerciseGoal goal) {
        LambdaQueryWrapper<ExerciseGoal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExerciseGoal::getUserId, goal.getUserId());
        wrapper.eq(ExerciseGoal::getGoalType, goal.getGoalType());
        ExerciseGoal existing = exerciseGoalMapper.selectOne(wrapper);

        if (existing != null) {
            goal.setId(existing.getId());
            exerciseGoalMapper.updateById(goal);
            return goal;
        } else {
            exerciseGoalMapper.insert(goal);
            return goal;
        }
    }

    public List<ExerciseGoal> getUserGoals(Long userId) {
        LambdaQueryWrapper<ExerciseGoal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExerciseGoal::getUserId, userId);
        return exerciseGoalMapper.selectList(wrapper);
    }

    public ExerciseGoal getUserGoalByType(Long userId, String goalType) {
        LambdaQueryWrapper<ExerciseGoal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExerciseGoal::getUserId, userId);
        wrapper.eq(ExerciseGoal::getGoalType, goalType);
        return exerciseGoalMapper.selectOne(wrapper);
    }
}
