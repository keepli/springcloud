package cn.itcast.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/user")
    public String test(){
        return "{\n" +
                "  \"id\": 8,\n" +
                "  \"userName\": \"itcast\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"name\": \"李狗蛋\",\n" +
                "  \"age\": 21,\n" +
                "  \"sex\": 2,\n" +
                "  \"birthday\": \"2000-07-07T16:00:00.000+0000\",\n" +
                "  \"note\": \"IT\",\n" +
                "  \"created\": \"2010-07-07T16:00:00.000+0000\",\n" +
                "  \"updated\": \"2015-07-07T14:00:00.000+0000\"\n" +
                "}";
    }
}
