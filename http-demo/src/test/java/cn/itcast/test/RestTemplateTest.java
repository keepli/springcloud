package cn.itcast.test;

import cn.itcast.HttpDemoApplication;
import cn.itcast.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpDemoApplication.class)
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test(){
        //restTemplate简单的作用就是通过url获取json并且反序列化
        String url = "http://localhost:8080/user";
        User user = restTemplate.getForObject(url, User.class);
        System.out.println(user);
    }
}
