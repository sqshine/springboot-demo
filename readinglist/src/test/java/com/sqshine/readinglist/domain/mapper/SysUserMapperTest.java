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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 〈一句话功能简述〉<br>
 * 〈${DESCRIPTION}〉
 *
 * @author sqshine
 * @create 2018/5/28
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void queryUserListPaged() throws JsonProcessingException {
        // 开始分页
        PageHelper.startPage(1, 2);

        List<SysUser> userList = sysUserMapper.selectAll();

        assertThat(userList.size()).isEqualTo(2);

        log.debug("pageinfo:{}", JacksonUtil.toJSONString(new PageInfo<>(userList)));
    }
}