package cn.zhangspace.springbootrest.controller;


import cn.zhangspace.springbootrest.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;

@RestController
public class UserRestController {

    @GetMapping("/user/{userName}")
    public User user(@PathVariable String userName,
                     @RequestParam(required = false) String address){
        User user = new User();
        user.setName(userName);
        user.setAddress(address);
        return user;
    }

    @PostMapping(value = "/user/json/to/properties",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = "application/properties+user")
    public User jsonToProperties(@RequestBody User user){
        return  user;
    }

    @PostMapping(value = "/user/properties/to/json",
            consumes = "application/properties+user",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User propertiesToJson(@RequestBody User user){
        return  user;
    }

}
