package com.wclp.springserver.service;

import com.wclp.springserver.pojo.Device;

import java.util.List;

public interface DeviceService {

    /**
     * 获取设备信息
     */
    Device getDeviceInfo(String Key);
    /**
     * 获取设备列表
     */
    List<Device> getAllDevice();
    /**
     * LED操作
     */
    String Switch_Status(String lightStatus, String Key);
}
