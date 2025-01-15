package com.trod.mapper;

import com.trod.entity.GameRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GameRoleMapper {
    @Insert("INSERT INTO game_role(role, user_id) VALUES(#{role}, #{user.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(GameRole gameRole);

    @Update("UPDATE game_role SET role = #{role} WHERE id = #{id}")
    void update(GameRole gameRole);
}
