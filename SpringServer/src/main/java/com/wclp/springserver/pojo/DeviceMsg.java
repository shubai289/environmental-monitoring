package com.wclp.springserver.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMsg {

    int id;

    private String message;

    private String createtime;

    private int checked;

    private String deviceName;

}
