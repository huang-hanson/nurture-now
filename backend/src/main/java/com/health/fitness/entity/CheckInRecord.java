package com.health.fitness.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("check_in_records")
public class CheckInRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private LocalDate checkInDate;

    private Integer consecutiveDays;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableLogic
    private Integer deleted;
}
