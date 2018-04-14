package com.xsh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author:xieshuang
 * @Description:
 * @Date:Create in 15:50 2018/4/13
 * @Modified By :
 */
@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public String head(){
        return "index.jsp";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelAndView model)throws Exception {
        return "index.jsp";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String get(ModelAndView model)throws Exception {
        return "hello world";
    }
}
