package com.health.fitness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.health.fitness.entity.TrainingPlan;
import com.health.fitness.entity.TrainingPlanItem;
import com.health.fitness.mapper.TrainingPlanItemMapper;
import com.health.fitness.mapper.TrainingPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingPlanService {

    @Autowired
    private TrainingPlanMapper trainingPlanMapper;

    @Autowired
    private TrainingPlanItemMapper trainingPlanItemMapper;

    public TrainingPlan createPlan(TrainingPlan plan, List<TrainingPlanItem> items) {
        trainingPlanMapper.insert(plan);

        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                TrainingPlanItem item = items.get(i);
                item.setPlanId(plan.getId());
                item.setOrderNum(i + 1);
                trainingPlanItemMapper.insert(item);
            }
        }

        return plan;
    }

    public List<TrainingPlan> getUserPlans(Long userId) {
        LambdaQueryWrapper<TrainingPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainingPlan::getUserId, userId);
        wrapper.orderByDesc(TrainingPlan::getCreatedAt);
        return trainingPlanMapper.selectList(wrapper);
    }

    public TrainingPlan getPlanDetail(Long planId) {
        TrainingPlan plan = trainingPlanMapper.selectById(planId);
        if (plan != null) {
            LambdaQueryWrapper<TrainingPlanItem> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TrainingPlanItem::getPlanId, planId);
            wrapper.orderByAsc(TrainingPlanItem::getOrderNum);
            plan.setItems(trainingPlanItemMapper.selectList(wrapper));
        }
        return plan;
    }

    public TrainingPlan updatePlanStatus(Long planId, String status) {
        TrainingPlan plan = new TrainingPlan();
        plan.setId(planId);
        plan.setStatus(status);
        trainingPlanMapper.updateById(plan);
        return plan;
    }
}
