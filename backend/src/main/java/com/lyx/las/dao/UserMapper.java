package com.lyx.las.dao;

import com.lyx.las.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    void create(User user) throws DataAccessException;

    User find(@Param("id") int id);

    void update(@Param("user") User user);

    void destroy(@Param("id") int id);

    User findByUsername(String username);

    User findByAccessToken(String token);
}
