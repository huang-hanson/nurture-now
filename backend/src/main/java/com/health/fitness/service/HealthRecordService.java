package com.health.fitness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.health.fitness.entity.HealthRecord;
import com.health.fitness.mapper.HealthRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    public HealthRecord saveRecord(HealthRecord record) {
        healthRecordMapper.insert(record);
        return record;
    }

    public List<HealthRecord> getUserRecords(Long userId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<HealthRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthRecord::getUserId, userId);
        if (startDate != null) {
            wrapper.ge(HealthRecord::getRecordDate, startDate);
        }
        if (endDate != null) {
            wrapper.le(HealthRecord::getRecordDate, endDate);
        }
        wrapper.orderByAsc(HealthRecord::getRecordDate);
        return healthRecordMapper.selectList(wrapper);
    }

    public Map<String, Object> getStats(Long userId, LocalDate startDate, LocalDate endDate) {
        List<HealthRecord> records = getUserRecords(userId, startDate, endDate);

        int totalCaloriesConsumed = 0;
        int totalCaloriesBurned = 0;
        double totalWeight = 0;
        int weightCount = 0;

        for (HealthRecord record : records) {
            if (record.getCaloriesConsumed() != null) {
                totalCaloriesConsumed += record.getCaloriesConsumed();
            }
            if (record.getCaloriesBurned() != null) {
                totalCaloriesBurned += record.getCaloriesBurned();
            }
            if (record.getWeight() != null) {
                totalWeight += record.getWeight().doubleValue();
                weightCount++;
            }
        }

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalDays", records.size());
        stats.put("totalCaloriesConsumed", totalCaloriesConsumed);
        stats.put("totalCaloriesBurned", totalCaloriesBurned);
        stats.put("averageWeight", weightCount > 0 ? totalWeight / weightCount : 0);
        stats.put("records", records);

        return stats;
    }
}
