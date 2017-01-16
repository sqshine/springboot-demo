package com.sqshine.readinglist.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sqshine.readinglist.domain.model.Country;
import com.sqshine.readinglist.service.ICountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public List<Country> getAll() {
        return countryService.getAll(1, Integer.MAX_VALUE);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Country getById(@PathVariable int id) {
        return countryService.getById(id);
    }

    @GetMapping("/{page}/{pageSize}")
    public PageInfo<Country> getListByPage(@PathVariable("page") int pageNum, @PathVariable int pageSize) {
        List<Country> countries = countryService.getAll(pageNum, pageSize);
        logger.debug("List<Country>======{}", JSON.toJSONString(countries));
        return new PageInfo<>(countries);
    }

    @GetMapping("/add")
    public Country save() {
        Country country = new Country();
        country.setCountrycode("ZH");
        country.setCountryname("china");
        countryService.save(country);
        return country;
    }
}
