package cn.zhangspace.springbootrest.controller;


import cn.zhangspace.springbootrest.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
