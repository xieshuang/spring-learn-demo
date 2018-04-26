package com.xsh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:xieshuang
 * @Description:
 * @Date:Create in 16:30 2018/4/26
 * @Modified By :
 */
@Controller
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController {

    @RequestMapping(value = "getUserById", method = RequestMethod.GET)
    @ResponseBody
    public Object checkUser(@RequestParam("id")String id){
        try {
            return userService.selectByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("出错了:", e);
        }
        return null;
    }
}
