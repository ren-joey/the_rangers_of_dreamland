package com.trod.mapper;

import com.trod.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM user")
    List<User> findAll();

    // FIXME: SQL SYNTAX ERROR
    @Insert({
            "<script>",
            "INSERT INTO user(username, email, password) VALUES(#{username}, #{email}, #{password})",
            "INSERT INTO game_role(role_enum, user_id) VALUES(#{gameRole.roleEnum}, LAST_INSERT_ID())",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE user SET username = #{username}, email = #{email} WHERE id = #{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteById(@Param("id") Long id);
}
