package com.xsh.controller;

import com.github.pagehelper.PageInfo;
import com.xsh.pojo.db.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
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

    /**
     * 根据id获取用户对象
     * @param id
     * @return
     */
    @RequestMapping(value = "getUserById", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserById(@RequestParam("id")String id){
        try {
            return userService.selectByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("出错了:", e);
        }
        return null;
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "getUserListByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object getUserListByPage(){
        try {
            User user = new User();
            return userService.getUserListByPage(user, new RowBounds(0, 5));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("出错了:", e);
        }
        return null;
    }
}
