package com.health.fitness.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("foods")
public class Food {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer calories;

    private String category;

    private String imageUrl;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableLogic
    private Integer deleted;
}
