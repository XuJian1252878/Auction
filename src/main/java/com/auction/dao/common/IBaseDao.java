package com.auction.dao.common;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
  // [public][static][final]变量
  // [public][abstract]方法

  // 获取一个变量实体
  T get(Class<T> entityClazz, Serializable id);

  // 查询实体的总个数
  int findCount(Class<T> entityClazz);

  // 保存一个实体
  Serializable save(T entity);

  // 更新一个实体
  boolean update(T entity);

  // 更新一个实体
  boolean merge(T entity);

  // 删除一个实体
  boolean delete(T entity);

  // 根据id删除一个实体
  boolean delete(Class<T> entityClazz, Serializable id);

  // 找到所有的实体
  List<T> findAll(Class<T> entityClazz);

  // 保存或者更新实体
  boolean saveOrUpdate(T entity);

  // 取出唯一的一个符合条件的实体
  public T loadModel(String hql, Object... params);

  /**
   * 根据分页数据取出对应的实体
   * @param pageNo
   * @param pageSize
   * @param hql hql语句，可包含参数
   * @param params hql语句中的参数值
   * @return 包含实体类的List对象，不可能是null（没有查询结果的情况下是一个空的List）
   */
  public List<T> listPart(int pageNo, int pageSize, String hql, Object...params);
  
  /**
   * 取得符合hql查询语句的所有该类实体。
   * @param hql
   * @param params
   * @return 包含实体类的List对象，不可能是null（没有查询结果的情况下是一个空的List）
   */
  public List<T> find(String hql, Object... params);
}
