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
    //private Class<T> modelClass;

    @Autowired
    protected MyMapper<T> mapper;

   /*public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }*/

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(T model) {
        mapper.insertSelective(model);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(List<T> models) {
        mapper.insertList(models);
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
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T findById(ID id) {
        return mapper.selectByPrimaryKey(id);
    }

/*    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException, IllegalAccessException, NoSuchFieldException, InstantiationException {

            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);

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
    public T queryOne(T model) {
        return mapper.selectOne(model);
    }

    @Override
    public List<T> queryListByWhere(T model) {
        return mapper.select(model);
    }

    @Override
    public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T model) {
        PageHelper.startPage(page, rows);
        List<T> list = this.queryListByWhere(model);
        return new PageInfo<>(list);
    }
}
