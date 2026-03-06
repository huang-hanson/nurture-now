package com.health.fitness.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("exercises")
public class Exercise {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer caloriesBurned;

    private String category;

    private Integer duration;

    private String imageUrl;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableLogic
    private Integer deleted;
}
