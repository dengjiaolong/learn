package com.huawei.controller;

import com.huawei.annotation.Authorization;
import com.huawei.entity.User;
import com.huawei.validgroup.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Authorization(value = {"test","mean"})
    @PostMapping("/login/{id}")
    public String login(@PathVariable("id") Integer id,
                        @RequestBody User user,
                        @RequestParam(value = "token", required = false) String token) {
        return "操作成功";
    }


    @GetMapping("/test")
    public String  testSingleParam(@RequestParam( value = "id" ,required = false) @NotNull(message = "ID不能为空") Integer id,
                                   @RequestParam(value = "name",required = false) @NotNull(message = "姓名不能为空")  String name){

       return "访问成功";

    }

    @PostMapping("/test")
    public String testJavaBean(@RequestBody  @Validated(value = Update.class) User user){
        System.out.println(user);
        return "访问成功";
    }

}
