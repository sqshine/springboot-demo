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
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 〈一句话功能简述〉<br>
 * 〈${DESCRIPTION}〉
 *
 * @author sqshine
 * @create 2018/5/25
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@Slf4j
public class JacksonUtilTest {

    private List<User> users = new ArrayList<>();
    private Map<String, User> map = new HashMap<>();
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";


    @Before
    public void setup() {
        User user = new User(null, "李", null, 10, null, null);
        User user1 = new User(2L, "王", "五", 20, new Date(), true);
        users.add(user);
        users.add(user1);
        map.put("u1", user);
        map.put("u2", user1);
    }

    @Test
    public void toJSONStringTest() throws JsonProcessingException {
        String jsonString = JacksonUtil.toJSONString(users);
        assertThat(jsonString).contains("李");
        log.info(jsonString);
    }

    @Test
    public void toJSONStringWithDateFormatTest() throws JsonProcessingException {
        String jsonString = JacksonUtil.toJSONStringWithDateFormat(users, DATEFORMAT);
        assertThat(jsonString).contains("李");
        log.info(jsonString);
    }

    @Test
    public void parseMapTest() throws IOException {
        String jsonString = JacksonUtil.toJSONString(map);
        log.info(jsonString);
        Map<String, User> map1 = JacksonUtil.parseMap(jsonString, Map.class, String.class, User.class);
        User u1 = map1.get("u1");
        assertThat(u1.getFirstname()).isEqualTo("李");
        Map<String, User> map2 = JacksonUtil.parseMap(jsonString, String.class, User.class);
        User u2 = map2.get("u2");
        assertThat(u2.getFirstname()).isEqualTo("王");
    }


    @Test
    public void parseListTest() throws IOException {
        String jsonString = JacksonUtil.toJSONString(users);
        List<User> userList = JacksonUtil.parseList(jsonString, User.class);
        assertThat(userList.get(0).getFirstname()).isEqualTo("李");
        assertThat(userList.size()).isEqualTo(2);
    }
}