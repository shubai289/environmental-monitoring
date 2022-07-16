package com.wclp.springserver.mapper;


import com.wclp.springserver.pojo.Device;
import com.wclp.springserver.pojo.IotData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceMapper {
    Device getDeviceInfo(@Param("Key")String Key);

    void UpdateDeviceInfo(Device data);

    void InsertDevice(Device data);

    void DeviceStatus(Device data);

    void InsertData(IotData iotdata);

    List<Device> getAllDevice();

    void Switch_Status(@Param("lightStatus")String lightStatus, @Param("Key")String Key);

    int findTotalCount();

}
