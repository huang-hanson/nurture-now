package com.health.fitness.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("health_records")
public class HealthRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private BigDecimal weight;

    private Integer caloriesConsumed;

    private Integer caloriesBurned;

    private LocalDate recordDate;

    private String notes;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableLogic
    private Integer deleted;
}
