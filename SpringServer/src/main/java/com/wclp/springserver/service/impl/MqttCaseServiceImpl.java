package com.wclp.springserver.service.impl;

import com.google.gson.Gson;
import com.wclp.springserver.mapper.DeviceMapper;
import com.wclp.springserver.mapper.MessageMapper;
import com.wclp.springserver.pojo.Device;
import com.wclp.springserver.pojo.DeviceMsg;
import com.wclp.springserver.pojo.IotData;
import com.wclp.springserver.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

/**
 * MQTT接收消息
 */

@Service
public class MqttCaseServiceImpl implements MessageHandler {

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    MessageMapper messageMapper;
    /**
     * MessageHeaders:
     * public static final String PREFIX = "mqtt_";
     * public static final String QOS = "mqtt_qos";
     * public static final String ID = "mqtt_id";
     * public static final String RECEIVED_QOS = "mqtt_receivedQos";
     * public static final String DUPLICATE = "mqtt_duplicate";
     * public static final String RETAINED = "mqtt_retained";
     * public static final String RECEIVED_RETAINED = "mqtt_receivedRetained";
     * public static final String TOPIC = "mqtt_topic";
     * public static final String RECEIVED_TOPIC = "mqtt_receivedTopic";
     * public static final String MESSAGE_EXPIRY_INTERVAL = "mqtt_messageExpiryInterval";
     * public static final String TOPIC_ALIAS = "mqtt_topicAlias";
     * public static final String RESPONSE_TOPIC = "mqtt_responseTopic";
     * public static final String CORRELATION_DATA = "mqtt_correlationData";
     */
    @Autowired
    Gson gson;

    IotData iotData = new IotData();

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = (String) message.getPayload();
        Device entity = gson.fromJson(payload, Device.class);
        System.out.println("headers:" + topic + " 接收的数据:" + payload);
        assert topic != null;
        if (topic.contains("mqtt-report")) {
            if (entity!=null){
                //不是遗嘱数据
                if (!entity.getTypes().equals("will")) {
                    switch (entity.getTypes()) {
                        case "insert":
                            System.out.println(entity.getProductKey());
                            if (deviceMapper.getDeviceInfo(entity.getProductKey()) == null) {
                                System.out.println("来自esp8266的insert数据");
                                //写入数据库 ...
                                entity.setCreatetime(DateUtils.getCurrentDateStr());
                                iotData.setCreatetime(DateUtils.getCurrentDateStr());
                                entity.setStatus(1);
                                deviceMapper.InsertDevice(entity);
                                iotData.setDeviceKey(entity.getProductKey());
                                iotData.setTemperature(entity.getTemperature());
                                iotData.setHumidity(entity.getHumidity());
                                iotData.setDeviceData(entity.getDeviceData());
                                deviceMapper.InsertData(iotData);
                            }
                            break;
                        case "update":
                            System.out.println("来自esp8266的update数据");
                            //写入数据库 ...
                            entity.setCreatetime(DateUtils.getCurrentDateStr());
                            iotData.setCreatetime(DateUtils.getCurrentDateStr());
                            deviceMapper.UpdateDeviceInfo(entity);
                            iotData.setDeviceKey(entity.getProductKey());
                            iotData.setTemperature(entity.getTemperature());
                            iotData.setHumidity(entity.getHumidity());
                            iotData.setDeviceData(entity.getDeviceData());
                            deviceMapper.InsertData(iotData);
                            break;
                        case "report":
                            System.out.println("来自esp8266的report数据");
                            //写入数据库 ...
                            entity.setCreatetime(DateUtils.getCurrentDateStr());
                            deviceMapper.UpdateDeviceInfo(entity);
                            DeviceMsg msg = new DeviceMsg();
                            msg.setChecked(0);
                            msg.setCreatetime(entity.getCreatetime());
                            msg.setDeviceName(entity.getDeviceName());
                            msg.setMessage(entity.getMessage());
                            messageMapper.NewMessage(msg);
                            break;
                    }
                }
            }else {
                System.out.println("序列化失败");
            }
        }else if(topic.contains("mqtt-will")){
            if(entity.getTypes().equals("will")){
                System.out.println("来自esp8266的will数据");
                if(entity.getMessage().equals("offline")){
                    entity.setStatus(0);
                }else if(entity.getMessage().equals("online")){
                    entity.setStatus(1);
                }
                deviceMapper.DeviceStatus(entity);
            }
        }
    }
}

