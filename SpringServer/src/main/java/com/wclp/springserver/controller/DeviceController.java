package com.wclp.springserver.controller;


import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.wclp.springserver.mqtt.IMqttSender;
import com.wclp.springserver.pojo.Device;
import com.wclp.springserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class DeviceController {

    @Resource
    DeviceService deviceServiceImpl;

    @Autowired
    IMqttSender mqttSender;

    @Autowired
    Gson gson;

    @ResponseBody
    @RequestMapping("/DeviceList")
    public String DeviceList(){

        Map<String, Object> map = new HashMap<>();
        List<Device> list = deviceServiceImpl.getAllDevice();
        map.put("deviceList", list);
        //System.out.println(map);
        String json = JSON.toJSONString(list);
        System.out.println(json);

        return json;
    }

    @ResponseBody
    @RequestMapping(value = "Refresh")
    public String Refresh(HttpServletRequest request){

        String productKey = request.getParameter("productKey");
        String payload = request.getParameter("payload");
        String topic = request.getParameter("topic");
        Device info = deviceServiceImpl.getDeviceInfo(productKey);
        mqttSender.sendToMqtt(topic,payload);

        return JSON.toJSONString(info);
    }

    @ResponseBody
    @RequestMapping(value = "Switch_lightStatus")
    public String Switch_Status(HttpServletRequest request){

        String topic = request.getParameter("topic");
        String payload = request.getParameter("payload");

        mqttSender.sendToMqtt(topic,payload);

        return "done";
    }

    @ResponseBody
    @RequestMapping(value = "changeDeviceName")
    public String changeDeviceName(HttpServletRequest request){

        String topic = request.getParameter("topic");
        String payload = request.getParameter("payload");

        mqttSender.sendToMqtt(topic,payload);

        return "done";
    }
}
