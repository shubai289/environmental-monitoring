package com.wclp.springserver.service.impl;

import com.wclp.springserver.mapper.DeviceMapper;
import com.wclp.springserver.pojo.Device;
import com.wclp.springserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"DeviceServiceImpl"})
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public Device getDeviceInfo(String Key) {
        return deviceMapper.getDeviceInfo(Key);
    }

    @Override
    public List<Device> getAllDevice() {
        return deviceMapper.getAllDevice();
    }

    @Override
    public String Switch_Status(String lightStatus, String Key) {
        if(lightStatus.equals("on")){
            deviceMapper.Switch_Status("off", Key);
            return "off";
        }else if (lightStatus.equals("off")){
            deviceMapper.Switch_Status("on", Key);
            return "on";
        }else {
            return "error";
        }

    }
}
