package cn.itcast.consumer.controller;

import cn.itcast.consumer.client.UserClient;
import cn.itcast.consumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Feign的处理器
 */
@RestController
@RequestMapping("/cf")
public class ControllerFeignConsumer {

    @Autowired
    private UserClient userClient;

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){
        return userClient.findById ( id );
    }
}
