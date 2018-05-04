package com.sqshine.readinglist.service;

import com.github.pagehelper.PageInfo;
import com.sqshine.readinglist.domain.model.SysUser;

import java.util.List;

/**
 * @author sqshine
 */
public interface IUserService extends IService<SysUser,Long> {

	List<SysUser> queryUserList(SysUser user);

	PageInfo<SysUser> queryUserListPaged(SysUser user, Integer page, Integer pageSize);

	SysUser queryUserByIdCustom(String userId);

	void saveUserTransactional(SysUser user);
}