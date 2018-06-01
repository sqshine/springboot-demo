package com.sqshine.readinglist.domain.mapper;

import com.sqshine.readinglist.domain.model.Country;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sqshine
 */
@Repository
public interface CountryMapper {

    /**
     * 获取所有Country
     *
     * @return List<Country>
     */
    @Select("select * from country")
    List<Country> getAll();

    /**
     * 获取指定id的country
     *
     * @param id id
     * @return Country
     */
    @Select("select * from country where id = #{id}")
    Country getById(@Param("id") Integer id);

    /**
     * 返回自动生成的主键
     *
     * @param country Country
     * @return 成功条数
     */
    @Insert("insert into country(countryname,countrycode) values(#{countryname},#{countrycode})")
    @Options(useGeneratedKeys = true, keyColumn = "Id")
    Integer save(Country country);

}