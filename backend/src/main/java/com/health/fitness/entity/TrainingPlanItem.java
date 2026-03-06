package com.health.fitness.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("training_plan_items")
public class TrainingPlanItem {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long planId;

    private Long exerciseId;

    private Integer sets;

    private Integer reps;

    private Integer duration;

    private Integer orderNum;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableLogic
    private Integer deleted;
}
