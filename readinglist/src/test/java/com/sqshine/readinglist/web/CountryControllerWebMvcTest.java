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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
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
 * 检测是否可以使用 mock service 的方式
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
@AutoConfigureMybatis
public class CountryControllerWebMvcTest {

    private List<Country> countries = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Before
    public void setUp() {
        for (int i = 0; i < 10; i++) {
            Country country = new Country();
            country.setCountrycode("country_code:" + i);
            country.setId(i);
            country.setCountryname("country_name:" + i);
            countries.add(country);
        }
    }

    @Test
    public void getAllTest() throws Exception {
        given(countryService.getAll(anyInt(), anyInt())).willReturn(countries);
        MvcResult mvcResult = mockMvc.perform(get("/country"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JacksonUtil.toJSONString(countries)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(countries.size())))
                .andExpect(content().string(containsString("country_name:1")))
                .andExpect(jsonPath("$.[0].id", is(0)))
                .andExpect(jsonPath("$.[0].countryname", is("country_name:0")))
                .andExpect(jsonPath("$.[0].countrycode", is("country_code:0")))
                .andExpect(jsonPath("$.[9].id", is(9)))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        List<Country> countryList = JacksonUtil.parseList(result, Country.class);
        assertThat(countryList.size()).isEqualTo(10);
        Country country = countryList.get(0);
        assertThat(country.getId()).isEqualTo(0);
    }

    @Test
    public void getByIdTest() throws Exception {
        Country country = countries.get(1);
        given(countryService.getById(anyInt())).willReturn(country);
        mockMvc.perform(get("/country/id/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("countryname", equalTo("country_name:1")))
                .andExpect(jsonPath("$.countrycode", is("country_code:1")));
    }

    @Test
    public void getListByPage() {
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        Country country = countries.get(1);
        given(countryService.save(any(Country.class))).willReturn(2);
        mockMvc.perform(post("/country/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JacksonUtil.toJSONString(country)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("countryname", equalTo("中国")))
                .andExpect(jsonPath("$.countrycode", is("ZH")));
    }

    @Test
    public void getStringTest() throws Exception {
        String str = "地动天摇 我自远方来，为你而驻足！";
        //given(countryController.getString(anyString())).willReturn("天为乾，地为坤，百世轮回宇宙间,情仇似梦事如烟！");
        mockMvc.perform(get("/country/getString")
                .param("str", "地动天摇"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(str));
    }
}