package com.sqshine.readinglist.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sqshine.readinglist.domain.model.Country;
import com.sqshine.readinglist.service.ICountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sqshine
 */
@RestController
@RequestMapping("/country")
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public List<Country> getAll() {
        return countryService.getAll(1, Integer.MAX_VALUE);
    }

    @CrossOrigin
    @GetMapping({"/id/{id}", "/id"})
    public Country getById(@PathVariable(required = false, value = "id") Integer id) {
        if (id == null) {
            id = 1;
        }
        return countryService.getById(id);
    }

    @GetMapping("/{page}/{pageSize}")
    public PageInfo<Country> getListByPage(@PathVariable("page") int pageNum, @PathVariable int pageSize) {
        List<Country> countries = countryService.getAll(pageNum, pageSize);
        logger.debug("List<Country>======{}", JSON.toJSONString(countries));
        return new PageInfo<>(countries);
    }

    @PostMapping("/add")
    public Country save(@RequestBody Country country) {
        country.setCountrycode("ZH");
        country.setCountryname("中国");
        countryService.save(country);
        return country;

    }

    /**
     * 直接返回String，配置使用fastjson时，需要加上produces = MediaType.APPLICATION_JSON_UTF8_VALUE，不会出现乱码
     *
     * @return String
     */
    @GetMapping(value = "/getString", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getString(@RequestParam("str") String str) {
        str += " 我自远方来，为你而驻足！";
        return str;
    }
}
