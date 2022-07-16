package com.wclp.springserver.service.impl;

import com.wclp.springserver.mapper.UserMapper;
import com.wclp.springserver.pojo.Page;
import com.wclp.springserver.pojo.User;
import com.wclp.springserver.service.UserService;
import com.wclp.springserver.utils.IdUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"UserServiceImpl"})
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public String login(User user) {
        User getUser = userMapper.selByPhone(user.getPhone());
        System.out.println(getUser);
        if (getUser != null) {
            if (getUser.getPassword().equals(user.getPassword())) {
                return "done";
            } else {
                return "pwdError";
            }
        } else {
            return "phoneError";
        }
    }

    @Override
    public int register(User user) {
        //判断手机号是否已存在
        User u = userMapper.selByPhone(user.getPhone());
        if (u != null) {
            return -1;
        }
        //生成新Uid，判断Uid是否已存在
        String ID = IdUtils.getId();
        while(true){
            User u1 = userMapper.selById(ID);
            if(u1 != null) {
                ID = IdUtils.getId();
            }else {
                user.setUid(ID);
                break;
            }
        }

        return userMapper.InsertUsers(user);
    }

    @Override
    public Page<User> findUserByPage(int page, int rows) {
        Page<User> pageBean = new Page<>();
        if (page <= 1) {
            page = 1;
        }
        int start;
        start = (page - 1) * 10;
        int totalCount = userMapper.findTotalCount();
        //计算总页数
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        pageBean.setTotalPage(totalPage);
        List<User> list = userMapper.findUserByPage(start, rows);
        pageBean.setTotalCount(totalCount);
        pageBean.setCurrentPage(page);
        pageBean.setRows(rows);
        pageBean.setList(list);
        return pageBean;
    }
}
