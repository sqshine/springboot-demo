package com.sqshine.readinglist.service;


import com.sqshine.readinglist.domain.model.Country;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICountryService {
    List<Country> getAll(int pageNum, int pageSize);

    Country getById(@Param("id") int id);

    Integer save(Country country);
}
