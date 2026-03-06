package com.health.fitness.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.health.fitness.entity.Exercise;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExerciseMapper extends BaseMapper<Exercise> {
}
