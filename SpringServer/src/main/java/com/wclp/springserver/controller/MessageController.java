package com.wclp.springserver.controller;


import com.alibaba.fastjson2.JSON;
import com.wclp.springserver.pojo.DeviceMsg;
import com.wclp.springserver.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

@Controller
public class MessageController {

    @Resource
    MessageService messageServiceImpl;

    @ResponseBody
    @RequestMapping("/getMessageList")
    public String getMessageList(){

        List<DeviceMsg> list = messageServiceImpl.getMessageList();

        String json = JSON.toJSONString(list);
        System.out.println(json);

        return json;
    }

    @ResponseBody
    @RequestMapping("/messageCheck")
    public String messageCheck(@RequestParam int id){
        if(messageServiceImpl.checkMessage(id)){
            return "done";
        }else {
            return "checked";
        }
    }

    @ResponseBody
    @RequestMapping("/messageDel")
    public String messageDelete(@RequestParam int id){
        if(messageServiceImpl.checkMessage(id)){
            return "done";
        }else {
            return "checked";
        }
    }
}
