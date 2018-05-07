package com.sqshine.readinglist.service;

import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Condition;

import java.io.Serializable;
import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 *
 * @author sqshine
 */
public interface IService<T,ID extends Serializable> {
    /**
     * 持久化
     *
     * @param entity 实体
     */
    void save(T entity);

    /**
     * 批量持久化
     *
     * @param entities 实体列表
     */
    void saveAll(List<T> entities);

    /**
     * 通过主鍵刪除
     *
     * @param id 主键
     */
    void deleteById(ID id);

    /**
     * 批量刪除 eg：ids -> “1,2,3,4”
     *
     * @param ids 多个主键
     */
    void deleteByIds(String ids);

    /**
     * 更新
     *
     * @param entity 实体
     */
    void update(T entity);

    /**
     * 通过ID查找
     *
     * @param id id
     * @return 实体
     */
    T findById(ID id);

    /**
     * 通过多个ID查找//eg：ids -> “1,2,3,4”
     *
     * @param ids ids
     * @return 列表
     */
    List<T> findByIds(String ids);

    /**
     * 根据条件查找
     *
     * @param condition 条件
     * @return 列表
     */
    List<T> findByCondition(Condition condition);//

    /**
     * 获取所有
     *
     * @return 列表
     */
    List<T> findAll();

    /**
     * 根据条件查询一条数据，如果有多条数据会抛出异常
     *
     * @param entity 实体
     * @return 实体
     */
    T getOne(T entity);


    /**
     * 根据条件查询数据列表
     *
     * @param entity 实体
     * @return 列表
     */
    List<T> findAll(T entity);

    /**
     * 分页查询
     *
     * @param page  page
     * @param rows  rows
     * @param entity entity
     * @return PageInfo
     */
    PageInfo<T> findAll(Integer page, Integer rows, T entity);


}
