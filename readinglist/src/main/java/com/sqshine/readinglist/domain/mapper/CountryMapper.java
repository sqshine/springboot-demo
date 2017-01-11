package com.sqshine.readinglist.domain.mapper;

import com.sqshine.readinglist.domain.model.Country;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CountryMapper {
    @Select("select * from country")
    List<Country> getAll();

    @Select("select * from country where id = #{id}")
    Country getById(@Param("id") int id);
}