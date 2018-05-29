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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
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
@AutoConfigureMockMvc
//@WebMvcTest(CountryController.class)
//@Import(CountryService.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryController countryController;

    //@MockBean
    //private CountryService countryService;

    private List<Country> countries = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 10; i++) {
            Country country = new Country();
            country.setCountrycode("country_code:" + i);
            country.setCountryname("country_name:" + i);
            country.setId(i);
            countries.add(country);
        }
    }

    @Test
    public void getAllTest() throws Exception {
        //when(countryService.getAll(1, 10)).thenReturn(countries);
        when(countryController.getAll()).thenReturn(countries);

        MvcResult mvcResult = mockMvc.perform(get("/country"))
                //.content(JacksonUtil.toJSONString(countries))
                //.contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JacksonUtil.toJSONString(countries)))
                .andExpect(jsonPath("$.*", hasSize(countries.size())))
                .andExpect(content().string(containsString("country_name:1")))
                .andExpect(jsonPath("$.[0].id", is(0)))
                .andExpect(jsonPath("$.[0].countryname", is("country_name:0")))
                .andExpect(jsonPath("$.[0].countrycode", is("country_code:0")))
                .andExpect(jsonPath("$.[9].id", is(9)))
                .andReturn();
        //String result = mvcResult.getResponse().getContentAsString();

        //List<Country> countryList = JacksonUtil.parseList(result, Country.class);

        //assertThat(result, is("[]"));
        //JSONObject jsonObject = JSONObject.parseObject(result);
        //assertEquals(true, jsonObject.containsKey("code"));
        //assertEquals(true, jsonObject.containsKey("msg"));
        //assertEquals(true, jsonObject.containsKey("data"));
        //.andExpect(jsonPath(
        //        "$.cupSize").value("B"))
        //.andExpect(jsonPath(
        //        "$.age").value(19))
        //.andExpect(jsonPath(
        //        "$.money").value(22.22))
    }

    @Test
    public void getByIdTest() throws Exception {
        Country country = countries.get(1);
        when(countryController.getById(1)).thenReturn(country);
        mockMvc.perform(get("/country/id/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.countryname", is("country_name:1")))
                .andExpect(jsonPath("$.countrycode", is("country_code:1")));

    }

    @Test
    public void getListByPage() {
    }

    @Test
    @Transactional
    public void saveTest() {
    }

    @Test
    public void getT() {
    }


    // 参考代码 https://hellokoding.com/restful-apis-example-with-spring-boot-integration-test-with-mockmvc-ui-integration-with-vuejs/
  /*
  @Test
    public void createANewStock() throws Exception {
        Stock stock = new Stock("S" + System.currentTimeMillis(), new BigDecimal( System.currentTimeMillis()));
        given(stockAPI.createANewStock(stock)).willReturn(ResponseEntity.ok(stock));

        this.mockMvc.perform(post("/api/v1/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(stock.getName()))
                .andExpect(jsonPath("currentPrice").value(stock.getCurrentPrice()));
    }

    @Test
    public void updatePriceOfAStock() throws Exception {
        Stock stock = new Stock("S" + System.currentTimeMillis(), new BigDecimal( System.currentTimeMillis()));
        BigDecimal updatingPrice = stock.getCurrentPrice().add(BigDecimal.ONE);
        Stock updatedStock = stock.clone();
        updatedStock.setCurrentPrice(updatingPrice);
        given(stockAPI.updatePriceOfAStock(stock.getId(), updatingPrice)).willReturn(ResponseEntity.ok(updatedStock));

        this.mockMvc.perform(put("/api/v1/stocks/" + stock.getId())
                .param("currentPrice", stock.getCurrentPrice().add(BigDecimal.ONE).toString())
                .content(new ObjectMapper().writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(stock.getName()))
                .andExpect(jsonPath("currentPrice").value(updatedStock.getCurrentPrice()));
    }*/
}