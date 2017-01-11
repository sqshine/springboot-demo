package com.sqshine.readinglist.service;

import com.github.pagehelper.PageHelper;
import com.sqshine.readinglist.domain.mapper.CountryMapper;
import com.sqshine.readinglist.domain.model.Country;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryService implements ICountryService {

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public List<Country> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return countryMapper.getAll();
    }

    @Override
    public Country getById(@Param("id") int id) {
        return countryMapper.getById(id);
    }

    @Override
    @Transactional
    public Integer save(Country country) {
        return countryMapper.save(country);
    }

}
