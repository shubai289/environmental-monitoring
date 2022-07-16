package com.wclp.springserver.service;

import com.wclp.springserver.pojo.DeviceMsg;

import java.util.List;

public interface MessageService {
    List<DeviceMsg> getMessageList();

    boolean checkMessage(int id);

    boolean delMessage(int id);
}
