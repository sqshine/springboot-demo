/*
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CountryMapperTest
 * Author:   sqshine
 * Date:     2018/5/28 12:20
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sqshine.readinglist.domain.mapper;

import com.sqshine.readinglist.domain.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
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
public class CountryMapperTest {
    @Autowired
    private CountryMapper countryMapper;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() {
        List<Country> countries = countryMapper.getAll();
        //log.debug("countries={}", JacksonUtil.toJSONString(countries));
        assertThat(countries).isNotNull();
    }

    @Test
    public void getById() {
    }

    @Test
    public void save() {
    }
}