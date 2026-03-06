package com.health.fitness.controller;

import com.health.fitness.common.Result;
import com.health.fitness.entity.CheckInRecord;
import com.health.fitness.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
@CrossOrigin
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @PostMapping("/{userId}")
    public Result<CheckInRecord> checkIn(@PathVariable Long userId) {
        return Result.success(checkInService.checkIn(userId));
    }

    @GetMapping("/records/{userId}")
    public Result<List<CheckInRecord>> getRecords(@PathVariable Long userId, @RequestParam(defaultValue = "7") int limit) {
        return Result.success(checkInService.getUserCheckInRecords(userId, limit));
    }

    @GetMapping("/today/{userId}")
    public Result<CheckInRecord> getTodayCheckIn(@PathVariable Long userId) {
        CheckInRecord record = checkInService.getTodayCheckIn(userId);
        return Result.success(record);
    }
}
