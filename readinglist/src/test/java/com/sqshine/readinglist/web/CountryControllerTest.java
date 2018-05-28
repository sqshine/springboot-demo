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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 〈一句话功能简述〉<br>
 * 〈${DESCRIPTION}〉
 *
 * @author sqshine
 * @create 2018/5/28
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@WebMvcTest(CountryController.class)
//@Import(CountryService.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    private List<Country> countries = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 10; i++) {
            Country country = new Country();
            country.setCountrycode("testcode:" + i);
            country.setCountryname("testname:" + i);
            country.setId(i);
            countries.add(country);
        }
    }

    @Test
    public void getAllTest() throws Exception {
        //when(countryService.getAll(1, 10)).thenReturn(countries);

        MvcResult mvcResult = mockMvc.perform(get("/country"))
                //.content(JacksonUtil.toJSONString(countries))
                //.contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //List<Country> countryList = JacksonUtil.parseList(result, Country.class);

        assertThat(result,is("[]"));
        //JSONObject jsonObject = JSONObject.parseObject(result);
        //Assert.assertEquals(true, jsonObject.containsKey("code"));
        //Assert.assertEquals(true, jsonObject.containsKey("msg"));
        //Assert.assertEquals(true, jsonObject.containsKey("data"));
        //.andExpect(jsonPath(
        //        "$.cupSize").value("B"))
        //.andExpect(jsonPath(
        //        "$.age").value(19))
        //.andExpect(jsonPath(
        //        "$.money").value(22.22))


        //mockMvc.perform(get("/country")).andDo(print()).andExpect(status().isOk());
        //.andExpect(content().string(containsString("testcode:0")));
    }

    @Test
    public void getById() {
    }

    @Test
    public void getListByPage() {
    }

    @Test
    public void save() {
    }

    @Test
    public void getT() {
    }
}