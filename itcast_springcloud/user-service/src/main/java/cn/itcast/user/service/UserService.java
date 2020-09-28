package cn.itcast.user.service;

import cn.itcast.user.mapper.UserMapper;
import cn.itcast.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务层
 */
public interface UserService {

    /**
     * 根据id查询
     * @return
     */
    User findById(Integer id);
}
