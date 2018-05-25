/*
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JacksonUtilTest
 * Author:   sqshine
 * Date:     2018/5/25 15:31
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sqshine.readinglist.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sqshine.readinglist.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈${DESCRIPTION}〉
 *
 * @author sqshine
 * @create 2018/5/25
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JacksonUtilTest {

    private List<User> users = new ArrayList<>();

    @Before
    public void setup() {
        users.add(new User(null, "李", null, 10, null, null));
        users.add(new User(2L, "王", "五", 20, new Date(), true));
    }

    @Test
    public void toJSONString() throws JsonProcessingException {
        String jsonString = JacksonUtil.toJSONString(users);
        log.info(jsonString);
    }

    @Test
    public void toJSONStringWithDateFormat() {
    }

    @Test
    public void parseObject() {
    }

    @Test
    public void parseMap() {
    }

    @Test
    public void parseMap1() {
    }

    @Test
    public void parseMap2() {
    }

    @Test
    public void parseList() {
    }
}