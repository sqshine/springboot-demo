package com.sqshine.readinglist.service.impl;

import com.github.pagehelper.PageHelper;
import com.sqshine.readinglist.domain.mapper.SysUserMapper;
import com.sqshine.readinglist.domain.mapper.SysUserMapperCustom;
import com.sqshine.readinglist.domain.model.SysUser;
import com.sqshine.readinglist.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author sqshine
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserMapperCustom sysUserMapperCustom;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(SysUser user) {

        sysUserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(SysUser user) {
//		sysUserMapper.updateByPrimaryKeySelective(user);
        sysUserMapper.updateByPrimaryKey(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(String userId) {
        sysUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public SysUser queryUserById(String userId) {

        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SysUser> queryUserList(SysUser user) {


        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(user.getUsername())) {
//			criteria.andEqualTo("username", user.getUsername());
            criteria.andLike("username", "%" + user.getUsername() + "%");
        }

        if (StringUtils.isNotBlank(user.getNickname())) {
            criteria.orLike("nickname", "%" + user.getNickname() + "%");
        }

        return sysUserMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SysUser> queryUserListPaged(SysUser user, Integer page, Integer pageSize) {
        // 开始分页
        PageHelper.startPage(page, pageSize);

        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(user.getNickname())) {
            criteria.andLike("nickname", "%" + user.getNickname() + "%");
        }
        example.orderBy("registTime").desc();

        return sysUserMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public SysUser queryUserByIdCustom(String userId) {

        List<SysUser> userList = sysUserMapperCustom.queryUserSimplyInfoById(userId);

        if (userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserTransactional(SysUser user) {

        sysUserMapper.insert(user);

        user.setIsDelete(1);
        sysUserMapper.updateByPrimaryKeySelective(user);
    }
}