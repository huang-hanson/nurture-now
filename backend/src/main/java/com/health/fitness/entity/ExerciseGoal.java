package com.health.fitness.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("exercise_goals")
public class ExerciseGoal {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String goalType; // daily/weekly/monthly

    private Integer durationMinutes;

    private Integer calories;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;
}
