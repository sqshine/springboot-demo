package com.sqshine.readinglist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sqshine.readinglist.util.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.io.Serializable;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author sqshine
 */
public abstract class AbstractService<T, ID extends Serializable> implements IService<T, ID> {

    /**
     * 当前泛型真实类型的Class
     */
    //private Class<T> entityClass;

    @Autowired
    protected MyMapper<T> mapper;

  /* public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) pt.getActualTypeArguments()[0];
    }*/

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(T entity) {
        mapper.insertSelective(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAll(List<T> entities) {
        mapper.insertList(entities);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(ID id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(T entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public T findById(ID id) {
        return mapper.selectByPrimaryKey(id);
    }

   /* @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException, IllegalAccessException, NoSuchFieldException, InstantiationException {

            T entity = entityClass.newInstance();
            Field field = entityClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
            return mapper.selectOne(entity);

    }*/

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public T getOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public List<T> findAll(T entity) {
        return mapper.select(entity);
    }

    @Override
    public PageInfo<T> findAll(Integer page, Integer rows, T entity) {
        PageHelper.startPage(page, rows);
        List<T> list = this.findAll(entity);
        return new PageInfo<>(list);
    }
}
