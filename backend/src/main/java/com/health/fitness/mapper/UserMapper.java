package com.health.fitness.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.health.fitness.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
