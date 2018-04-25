package com.sqshine.readinglist.domain.mapper;

import com.sqshine.readinglist.domain.model.SysUser;

import java.util.List;

/**
 * @author sqshine
 */
public interface SysUserMapperCustom {

	List<SysUser> queryUserSimplyInfoById(String id);
}