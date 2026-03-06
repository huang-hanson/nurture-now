package com.health.fitness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.health.fitness.entity.User;
import com.health.fitness.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User saveOrUpdateUser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getWechatOpenId, user.getWechatOpenId());
        User existing = userMapper.selectOne(wrapper);

        if (existing != null) {
            user.setId(existing.getId());
            userMapper.updateById(user);
            return user;
        } else {
            userMapper.insert(user);
            return user;
        }
    }

    public User getUserByOpenId(String openId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getWechatOpenId, openId);
        return userMapper.selectOne(wrapper);
    }

    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
}
