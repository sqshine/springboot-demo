package com.sqshine.readinglist.web;

import com.github.pagehelper.PageInfo;
import com.sqshine.readinglist.domain.model.Result;
import com.sqshine.readinglist.domain.model.SysUser;
import com.sqshine.readinglist.service.IUserService;
import com.sqshine.readinglist.util.DateUtil;
import com.sqshine.readinglist.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SysUserContoller.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/save")
    public Result saveUser() {

        String userId = "1010";

        SysUser user = new SysUser();
        user.setId(userId);
        user.setUsername("test" + DateUtil.getTime());
        user.setNickname("test" + DateUtil.getTime());
        user.setPassword("abc123");
        user.setIsDelete(0);
        user.setRegistTime(new Date());

        userService.save(user);

        return ResultUtil.success("Save OK");
    }

    @RequestMapping("/update")
    public Result updateUser() {

        SysUser user = new SysUser();
        user.setId("1001");
        user.setUsername("10011001-updated" + DateUtil.getTime());
        user.setNickname("10011001-updated" + DateUtil.getTime());
        user.setPassword("10011001-updated");
        user.setIsDelete(0);
        user.setRegistTime(new Date());

        userService.update(user);

        return ResultUtil.success("update OK");
    }

    @RequestMapping("/delete")
    public Result deleteUser(Long userId) {
        userService.deleteById(userId);
        return ResultUtil.success("delete OK");
    }

    @RequestMapping("/ds")
    public Result deleteUsers() {
        userService.deleteByIds("10,1005");
        return ResultUtil.success("delete OK");
    }

    @RequestMapping("/{userId}")
    public SysUser getUserById(@PathVariable Long userId) {

        return userService.findById(userId);
    }

    @RequestMapping("/getUsersByIds")
    public List<SysUser> getUsersByIds(String ids) {

        return userService.findByIds(ids);
    }

    @RequestMapping("/queryUserList")
    public List<SysUser> queryUserList() {

        SysUser user = new SysUser();
        user.setUsername("test");
        user.setNickname("lee");

        return userService.queryUserList(user);
    }

    @RequestMapping("/queryUserListPaged")
    public PageInfo<SysUser> queryUserListPaged(Integer page) {

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

        String userId = "101";
        SysUser user = new SysUser();
        user.setId(userId);
        user.setUsername("sq" + DateUtil.getTime());
        user.setNickname("sq" + DateUtil.getTime());
        user.setPassword("abc123");
        user.setIsDelete(0);
        user.setRegistTime(new Date());

        userService.saveUserTransactional(user);

        return ResultUtil.success("save ok");
    }
}
