package com.sqshine.readinglist.domain.mapper;

import com.sqshine.readinglist.domain.model.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sqshine
 */
@Repository
public interface SysUserMapperCustom {

	List<SysUser> queryUserSimplyInfoById(String id);
}