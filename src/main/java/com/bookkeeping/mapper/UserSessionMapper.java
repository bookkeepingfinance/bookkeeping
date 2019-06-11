package com.bookkeeping.mapper;

import com.bookkeeping.entity.UserSession;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionMapper {
    @Select(value =
            "<script>select * " +
                    "from user_session " +
                    "<where>" +
                    "<if test='sessionId != null'> and session_id = #{sessionId} </if>" +
                    "<if test='userId != null'> and user_id = #{userId} </if>" +
                    "</where>" +
                    "order by id desc " +
                    "limit 1 " +
                    ";" +
                    "</script>")
    Optional<UserSession> find(UserSession session);

    @Update(value = "update user_session " +
            "set session_data = #{sessionData} " +
            "where user_id = {userId} and session_id = #{sessionId} " +
            ";")
    Integer update(UserSession us);

    @Insert(value = "insert into user_session(user_id, session_id, session_data) " +
            "values(#{userId},#{sessionId},#{sessionData})")
    Integer insert(UserSession us);
}
