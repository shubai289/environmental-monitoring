package com.wclp.springserver.mapper;

import com.wclp.springserver.pojo.DeviceMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {

    boolean checkMessage(@Param("id")int id, int checked);

    boolean delMessage(@Param("id")int id);

    void NewMessage(DeviceMsg msg);

    List<DeviceMsg> getMessageList();
}
