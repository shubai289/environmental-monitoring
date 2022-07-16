package com.wclp.springserver.service.impl;

import com.wclp.springserver.mapper.MessageMapper;
import com.wclp.springserver.pojo.DeviceMsg;
import com.wclp.springserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"MessageServiceImpl"})
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<DeviceMsg> getMessageList() {
        return messageMapper.getMessageList();
    }

    @Override
    public boolean checkMessage(int id) {
        return messageMapper.checkMessage(id, 1);
    }

    @Override
    public boolean delMessage(int id) {
        return messageMapper.delMessage(id);
    }
}
