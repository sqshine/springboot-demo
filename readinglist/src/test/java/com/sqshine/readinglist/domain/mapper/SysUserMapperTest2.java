/*
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: SysUserMapperTest
 * Author:   sqshine
 * Date:     2018/5/28 12:33
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sqshine.readinglist.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sqshine.readinglist.domain.model.SysUser;
import com.sqshine.readinglist.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 〈一句话功能简述〉<br>
 * 〈${DESCRIPTION}〉
 *
 * @author sqshine
 * @create 2018/5/28
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class SysUserMapperTest2 {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void queryUserListPaged() throws JsonProcessingException {

        List<SysUser> userList = sysUserMapper.selectAll();

        assertEquals(6, userList.size());

        log.debug("pageinfo:{}", JacksonUtil.toJSONString(new PageInfo<>(userList)));
    }
}