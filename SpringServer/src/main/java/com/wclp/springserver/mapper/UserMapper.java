package com.wclp.springserver.mapper;


import com.wclp.springserver.pojo.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {

    User selById(@Param("ID")String ID);

    int InsertUsers(User user);

    User selByPhone(@Param("phone") Integer phone);

    List<User> findUserByPage(@Param("start") int start, @Param("rows")int rows);

    int findTotalCount();
}