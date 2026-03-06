package com.health.fitness.controller;

import com.health.fitness.common.Result;
import com.health.fitness.entity.HealthRecord;
import com.health.fitness.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/record")
@CrossOrigin
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @PostMapping("/save")
    public Result<HealthRecord> saveRecord(@RequestBody HealthRecord record) {
        return Result.success(healthRecordService.saveRecord(record));
    }

    @GetMapping("/user/{userId}")
    public Result<List<HealthRecord>> getUserRecords(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(healthRecordService.getUserRecords(userId, startDate, endDate));
    }

    @GetMapping("/stats/{userId}")
    public Result<Map<String, Object>> getStats(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(healthRecordService.getStats(userId, startDate, endDate));
    }
}
