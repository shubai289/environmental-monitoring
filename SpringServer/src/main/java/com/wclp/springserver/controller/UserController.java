package com.wclp.springserver.controller;

import com.wclp.springserver.pojo.Page;
import com.wclp.springserver.pojo.User;
import com.wclp.springserver.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Resource
    UserService userServiceImpl;

    @ResponseBody
    @PostMapping("login")
    public String login(@RequestBody User user, HttpServletRequest request){

        String rs = userServiceImpl.login(user);
        if (rs.equals("done")) {
            request.getSession().setAttribute("userInfo", rs);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5*60);//以秒为单位，即在没有活动5分钟后，session将失效
            return "done";
        } else if (rs.equals("pwdError")){
            return "pwdError";
        }else if (rs.equals("phoneError")){
            return "phoneError";
        }
        return "get";
    }

    @ResponseBody
    @PostMapping("register")
    public String register(@RequestParam Integer phone, @RequestParam String password){

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        int result;

        result = userServiceImpl.register(user);
        System.out.println(result);

        if(result == -1){
            return "exist";
        } else if(result == 1){
            return "done";
        } else {
            return "error";
        }
    }

    @RequestMapping("/toUserList")
    public ModelAndView toUserList(HttpServletRequest request){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(null==page || "".equals(page)){
            page="1";
        }
        if(null==rows || "".equals(rows)){
            rows="10";
        }
        ModelAndView mav = new ModelAndView("admin/bs_userList");
        Page<User> list = userServiceImpl.findUserByPage(Integer.parseInt(page), Integer.parseInt(rows));
        mav.addObject("userList",list);
        return mav;
    }
}


