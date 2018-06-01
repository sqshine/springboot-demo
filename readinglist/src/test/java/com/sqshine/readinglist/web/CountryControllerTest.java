/*
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CountryControllerTest
 * Author:   sqshine
 * Date:     2018/5/28 16:27
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sqshine.readinglist.web;

import com.sqshine.readinglist.domain.model.Country;
import com.sqshine.readinglist.service.impl.CountryService;
import com.sqshine.readinglist.util.JacksonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈${DESCRIPTION}〉
 *
 * @author sqshine
 * @create 2018/5/28
 * @since 1.0.0
 * 参考代码 https://hellokoding.com/restful-apis-example-with-spring-boot-integration-test-with-mockmvc-ui-integration-with-vuejs/
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
//@MybatisTest
@Import({CountryService.class})
@AutoConfigureMybatis
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
    }

    @Test
    public void getAllTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/country"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("Afghanistan")))
                .andExpect(jsonPath("$.*", hasSize(183)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].countryname", is("Angola")))
                .andExpect(jsonPath("$.[0].countrycode", is("AO")))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        List<Country> countryList = JacksonUtil.parseList(result, Country.class);
        assertThat(countryList.size()).isEqualTo(183);
        Country country = countryList.get(0);
        assertThat(country.getCountrycode()).isEqualTo("AO");
    }

    @Test
    public void getByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/country/id/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("countryname", equalTo("Angola")))
                .andExpect(jsonPath("$.countrycode", is("AO")))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        Country country = JacksonUtil.parseObject(result, Country.class);

        assertThat(country.getId()).isEqualTo(1);
    }

    @Test
    public void getListByPage() {
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        Country country = new Country();
        MvcResult mvcResult = mockMvc.perform(post("/country/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JacksonUtil.toJSONString(country)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", greaterThan(0)))
                .andExpect(jsonPath("countryname", is("中国")))
                .andExpect(jsonPath("$.countrycode", is("ZH")))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        Country country1 = JacksonUtil.parseObject(result, Country.class);
        assertThat(country1.getId()).isGreaterThan(0);
    }

    @Test
    public void getStringTest() throws Exception {
        mockMvc.perform(get("/country/getString")
                .param("str", "地动天摇"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("地动天摇 我自远方来，为你而驻足！"));
    }
}