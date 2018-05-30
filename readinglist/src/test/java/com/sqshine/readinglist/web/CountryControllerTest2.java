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
import com.sqshine.readinglist.util.JacksonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈${DESCRIPTION}〉
 *
 * @author sqshine
 * @create 2018/5/28
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountryControllerTest2 {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getAllTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/country"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(183)))
                .andExpect(content().string(containsString("Afghanistan")))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].countryname", is("Angola")))
                .andExpect(jsonPath("$.[0].countrycode", is("AO")))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        List<Country> countryList = JacksonUtil.parseList(result, Country.class);
        assertThat(countryList.size(), equalTo(183));
        Country country = countryList.get(0);
        assertThat(country.getCountrycode(), equalTo("AO"));
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

        assertThat(country.getId(), equalTo(1));
    }

    @Test
    public void getListByPage() {
    }

    @Test
    @Transactional
    public void saveTest() {
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