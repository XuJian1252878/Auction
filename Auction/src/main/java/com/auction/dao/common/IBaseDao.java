package com.auction.dao.common;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
  // [public][static][final]变量
  // [public][abstract]方法

  /**
   * 根据数据库记录的id信息获得对应的数据库对象。
   * @param entityClazz  数据库对象对应的类信息。
   * @param id  数据库对象的id信息
   * @return  a persistent instance or null
   */
  T get(Class<T> entityClazz, Serializable id);

  // 查询实体的总个数
  int findCount(Class<T> entityClazz);

  // 查询满足条件的实体总个数。
  int findCount(String hql);

  /**
   * Persist the given transient instance, first assigning a generated identifier. 
   * (Or using the current value of the identifier property if the assigned generator is used.) 
   * This operation cascades to associated instances if the association is mapped with cascade="save-update"
   * @param entity  a transient instance of a persistent class
   * @return  the generated identifier
   */
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

  /**
   * 取出符合查询条件的唯一实体。
   * @param hql  指定条件的hql语句。
   * @param params  hql语句中的参数信息。
   * @return
   */
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
   * @param hql  可能包含有占位符的hql语句，注意这里hql语句只支持?占位符，不支持自定义占位符名称。
   * @param params  填充占位符所需要的值。
   * @return 包含实体类的List对象，不可能是null（没有查询结果的情况下是一个空的List）
   */
  public List<T> find(String hql, Object... params);
}
