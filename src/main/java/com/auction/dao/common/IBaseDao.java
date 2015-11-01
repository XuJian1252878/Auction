package com.auction.dao.common;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
  //[public][static][final]变量
  //[public][abstract]方法
  
  //获取一个变量实体
  T get(Class<T> entityClazz, Serializable id);
  
  //查询实体的总个数
  int findCount(Class<T> entityClazz);
  
  //保存一个实体
  Serializable save(T entity);
  
  //更新一个实体
  boolean update(T entity);
  
  //删除一个实体
  boolean delete(T entity);
  
  //根据id删除一个实体
  boolean delete(Class<T> entityClazz, Serializable id);
  
  //找到所有的实体
  List<T> findAll(Class<T> entityClazz);
  
  //保存或者更新实体
  boolean saveOrUpdate(T entity);
}
