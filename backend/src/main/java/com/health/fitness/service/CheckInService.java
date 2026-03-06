package com.health.fitness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.health.fitness.entity.CheckInRecord;
import com.health.fitness.mapper.CheckInRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CheckInService {

    @Autowired
    private CheckInRecordMapper checkInRecordMapper;

    public CheckInRecord checkIn(Long userId) {
        LocalDate today = LocalDate.now();

        LambdaQueryWrapper<CheckInRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckInRecord::getUserId, userId);
        wrapper.eq(CheckInRecord::getCheckInDate, today);
        CheckInRecord existing = checkInRecordMapper.selectOne(wrapper);

        if (existing != null) {
            return existing;
        }

        // 计算连续签到天数
        Integer consecutiveDays = calculateConsecutiveDays(userId);

        CheckInRecord record = new CheckInRecord();
        record.setUserId(userId);
        record.setCheckInDate(today);
        record.setConsecutiveDays(consecutiveDays);
        checkInRecordMapper.insert(record);

        return record;
    }

    private Integer calculateConsecutiveDays(Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        LambdaQueryWrapper<CheckInRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckInRecord::getUserId, userId);
        wrapper.eq(CheckInRecord::getCheckInDate, yesterday);
        CheckInRecord yesterdayRecord = checkInRecordMapper.selectOne(wrapper);

        if (yesterdayRecord != null) {
            return yesterdayRecord.getConsecutiveDays() + 1;
        }

        return 1;
    }

    public List<CheckInRecord> getUserCheckInRecords(Long userId, int limit) {
        LambdaQueryWrapper<CheckInRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckInRecord::getUserId, userId);
        wrapper.orderByDesc(CheckInRecord::getCheckInDate);
        wrapper.last("LIMIT " + limit);
        return checkInRecordMapper.selectList(wrapper);
    }

    public CheckInRecord getTodayCheckIn(Long userId) {
        LocalDate today = LocalDate.now();
        LambdaQueryWrapper<CheckInRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckInRecord::getUserId, userId);
        wrapper.eq(CheckInRecord::getCheckInDate, today);
        return checkInRecordMapper.selectOne(wrapper);
    }
}
