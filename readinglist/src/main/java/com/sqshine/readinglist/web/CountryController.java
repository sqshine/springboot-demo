package com.sqshine.readinglist.web;

import com.github.pagehelper.PageHelper;
import com.sqshine.readinglist.domain.model.Country;
import com.sqshine.readinglist.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public List<Country> getAll() {
        return countryService.getAll();
    }

    @GetMapping("/{id}")
    public Country getById(@PathVariable int id) {
        return countryService.getById(1);
    }

    @GetMapping("/{page}/{pageSize}")
    public List<Country> getListByPage(@PathVariable("page") int pageNume,@PathVariable int pageSize) {
        PageHelper.startPage(pageNume, pageSize);
        return countryService.getAll();
    }
}
