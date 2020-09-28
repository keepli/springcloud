package cn.itcast.consumer.client;

import cn.itcast.consumer.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 声明当前类是一个Feign客户端，指定服务名为user-service
 * 会通过动态代理为我们生成一个实现类
 */
@FeignClient("user-service")
public interface UserClient {

    //http://user-service/user/5
    @GetMapping("/user/{id}")
    User findById(@PathVariable Integer id);
}
