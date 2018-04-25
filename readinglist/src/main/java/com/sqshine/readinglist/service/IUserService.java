package com.sqshine.readinglist.service;

import com.sqshine.readinglist.domain.model.SysUser;

import java.util.List;

/**
 * @author sqshine
 */
public interface IUserService {

	void saveUser(SysUser user) ;

	void updateUser(SysUser user);

	void deleteUser(String userId);

	SysUser queryUserById(String userId);

	List<SysUser> queryUserList(SysUser user);

	List<SysUser> queryUserListPaged(SysUser user, Integer page, Integer pageSize);

	SysUser queryUserByIdCustom(String userId);

	void saveUserTransactional(SysUser user);
}