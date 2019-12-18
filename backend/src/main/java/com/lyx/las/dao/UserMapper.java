package com.lyx.las.dao;

import com.lyx.las.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    // 创建用户
    void create(User user) throws DataAccessException;

    // 根据ID查找用户
    User find(@Param("id") int id);

    // 更新用户
    void update(@Param("user") User user);

    // 根据ID删除用户
    void destroy(@Param("id") int id);

    // 根据姓名查找用户
    User findByUsername(String username);

    // 根据 access_token 查找用户
    User findByAccessToken(String accessToken);
}
