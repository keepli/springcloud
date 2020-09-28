package cn.itcast.consumer.controller;

import cn.itcast.consumer.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
@Slf4j
@DefaultProperties(defaultFallback = "defaultFallback")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){

        //String url = "http://localhost:9091/user/"+id;

        /*
        //获取eureka中注册的user-service的实例
        //1.通过application-name发现注册的服务
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances ( "user-service" );
        //2.因为该服务只注册了一个实例，就是只有一个地址，所以取第一个元素就行
        ServiceInstance serviceInstance = serviceInstanceList.get ( 0 );
        //3.通过拼接方式获取url
        String url = "http://" + serviceInstance.getHost () + ":" + serviceInstance.getPort () + "/user/" + id;
        */

        //通过eureka中注册的user-service的服务名来直接获取实例（需要为这个restTemplate添加负载均衡注解）
        String url = "http://user-service/user/" + id;

        User user = restTemplate.getForObject ( url, User.class );
        return user;
    }

    @GetMapping("/findByIdHystrix/{id}")
    //服务降级，到了规定时间无响应会调用回调方法，两个方法返回类型和参数要保持一致
    //@HystrixCommand(fallbackMethod = "findByIdFallback")
    @HystrixCommand
    public String findByIdHystrix(@PathVariable Integer id){
        //超时时长的配置测试
        /*try {
            Thread.sleep ( 2000 );
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }*/

        //断路器原理测试（阈值）
        if(id==1){
            throw new RuntimeException ( "太忙了" );
        }

        String url = "http://user-service/user/" + id;
        return  restTemplate.getForObject ( url, String.class );
    }

    public String findByIdFallback(Integer id){
        log.error ( "查询用户信息失败。id:{}",id );
        return "对不起服务太拥挤了！";

        //也可以返回pojo对象
        /*User user = new User ( );
        user.setAge ( 26 );
        user.setName ( "李狗蛋" );
        return user;*/
    }

    /**
     * 默认的回调方法
     * @return
     */
    public String defaultFallback(){
        return "默认提示：对不起服务太拥挤了！";
    }
}
