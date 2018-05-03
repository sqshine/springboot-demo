package com.sqshine.readinglist.web;

import com.sqshine.readinglist.domain.model.Result;
import com.sqshine.readinglist.domain.model.SysUser;
import com.sqshine.readinglist.service.IUserService;
import com.sqshine.readinglist.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @author sqshine
 */
@RestController
@RequestMapping("/u")
public class SysUserContoller {

    @Autowired
    private IUserService userService;

    @RequestMapping("/save")
    public Result saveUser() {

        String userId = "1002";

        SysUser user = new SysUser();
        user.setId(userId);
        user.setUsername("imooc" + new Date());
        user.setNickname("imooc" + new Date());
        user.setPassword("abc123");
        user.setIsDelete(0);
        user.setRegistTime(new Date());

        userService.saveUser(user);

        return ResultUtil.success("Save OK");
    }

    @RequestMapping("/update")
    public Result updateUser() {

        SysUser user = new SysUser();
        user.setId("10011001");
        user.setUsername("10011001-updated" + new Date());
        user.setNickname("10011001-updated" + new Date());
        user.setPassword("10011001-updated");
        user.setIsDelete(0);
        user.setRegistTime(new Date());

        userService.updateUser(user);

        return ResultUtil.success("update OK");
    }

    @RequestMapping("/delete")
    public Result deleteUser(String userId) {

        userService.deleteUser(userId);

        return ResultUtil.success("delete OK");
    }

    @RequestMapping("/{userId}")
    public SysUser queryUserById(@PathVariable String userId) {

        return userService.queryUserById(userId);
    }

    @RequestMapping("/queryUserList")
    public List<SysUser> queryUserList() {

        SysUser user = new SysUser();
        user.setUsername("imooc");
        user.setNickname("lee");

        return userService.queryUserList(user);
    }

    @RequestMapping("/queryUserListPaged")
    public List<SysUser> queryUserListPaged(Integer page) {

        if (page == null) {
            page = 1;
        }

        int pageSize = 2;

        SysUser user = new SysUser();
//		user.setNickname("lee");

        return userService.queryUserListPaged(user, page, pageSize);
    }

    @RequestMapping("/queryUserByIdCustom")
    public SysUser queryUserByIdCustom(String userId) {

        return userService.queryUserByIdCustom(userId);
    }

    @RequestMapping("/saveUserTransactional")
    public Result saveUserTransactional() {

        String userId = "10";
        SysUser user = new SysUser();
        user.setId(userId);
        user.setUsername("lee" + new Date());
        user.setNickname("lee" + new Date());
        user.setPassword("abc123");
        user.setIsDelete(0);
        user.setRegistTime(new Date());

        userService.saveUserTransactional(user);

        return ResultUtil.success("save ok");
    }
}
