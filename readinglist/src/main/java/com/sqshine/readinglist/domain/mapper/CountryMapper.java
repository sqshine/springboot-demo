package com.sqshine.readinglist.domain.mapper;

import com.sqshine.readinglist.domain.model.Country;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CountryMapper {
    @Select("select * from country")
    List<Country> getAll();

    @Select("select * from country where id = #{id}")
    Country getById(@Param("id") int id);

    @Insert("insert into country(countryname,countrycode) values(#{countryname},#{countrycode})")
    @Options(useGeneratedKeys = true, keyColumn = "Id")//返回自动生成的主键
    void save(Country country);

}