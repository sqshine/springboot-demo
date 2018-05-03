package com.sqshine.readinglist.service;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 *
 * @author sqshine
 */
public interface IService<T> {
    /**
     * 持久化
     * @param model 实体
     */
    void save(T model);

    /**
     * 批量持久化
     * @param models 实体列表
     */
    void save(List<T> models);

    /**
     * 通过主鍵刪除
     * @param id 主键
     */
    void deleteById(Integer id);

    /**
     * 批量刪除 eg：ids -> “1,2,3,4”
     * @param ids 多个主键
     */
    void deleteByIds(String ids);

    /**
     * 更新
     * @param model 实体
     */
    void update(T model);

    /**
     * 通过ID查找
     * @param id id
     * @return 实体
     */
    T findById(Integer id);

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName 参数名
     * @param value 值
     * @return 实体
     * @throws TooManyResultsException 异常
     * @throws IllegalAccessException 异常
     * @throws NoSuchFieldException 异常
     * @throws InstantiationException 异常
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException, IllegalAccessException, NoSuchFieldException, InstantiationException;

    /**
     * 通过多个ID查找//eg：ids -> “1,2,3,4”
     * @param ids ids
     * @return 列表
     */
    List<T> findByIds(String ids);

    /**
     * 根据条件查找
     * @param condition 条件
     * @return 列表
     */
    List<T> findByCondition(Condition condition);//

    /**
     * 获取所有
     * @return 列表
     */
    List<T> findAll();
}
