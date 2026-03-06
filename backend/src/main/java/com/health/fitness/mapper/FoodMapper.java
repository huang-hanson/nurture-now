package com.health.fitness.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.health.fitness.entity.Food;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}
