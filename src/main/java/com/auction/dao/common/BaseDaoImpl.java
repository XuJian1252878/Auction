package com.auction.dao.common;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class BaseDaoImpl<T> implements IBaseDao<T> {

  /**
   * @Resource(name="sessionFactory") 默认实现了sessionFactory的get和set方法
   * 而
   * @Autowired 标签则不会帮助我们实现，我们需要自己实现get，set方法便于spring框架的依赖注入。
   * @Autowired
     public void setSessionFactory(SessionFactory sessionFactory)
     {
         this.sessionFactory = sessionFactory;
     }
     public SessionFactory getSessionFactory()
     {
         return this.sessionFactory;
     }
   */
  
  @Resource(name="sessionFactory")
  private SessionFactory sessionFactory;
  
  @SuppressWarnings("unchecked")
  public T get(Class<T> entityClazz, Serializable id) {
    // TODO Auto-generated method stub
    // 返回对应的实体或者null。
    return (T)sessionFactory.getCurrentSession().get(entityClazz, id);
  }

  @SuppressWarnings("unchecked")
  protected List<T> find(String hql, Object... params) {
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    // 向query实例中添加参数。
    for(int index = 0, len = params.length; index < len; index++) {
      query.setParameter(index, params[index]);
    }
    return (List<T>)query.list();
  }

  public int findCount(Class<T> entityClazz) {
    // TODO Auto-generated method stub
    // 计算对应数据库表中元组的个数
    String hql = "select count(*) from " + entityClazz.getName();
    Long recordCount = (Long) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
    return recordCount != null ? recordCount.intValue() : -1;
  }

  public Serializable save(T entity) {
    // TODO Auto-generated method stub
    return sessionFactory.getCurrentSession().save(entity);
  }

  public boolean update(T entity) {
    // TODO Auto-generated method stub
    sessionFactory.getCurrentSession().update(entity);
    return true;
  }

  public boolean delete(T entity) {
    // TODO Auto-generated method stub
    sessionFactory.getCurrentSession().delete(entity);
    return true;
  }

  public boolean delete(Class<T> entityClazz, Serializable id) {
    // TODO Auto-generated method stub
    T entity = get(entityClazz, id);
    if(entity == null) {
      return false;
    }
    sessionFactory.getCurrentSession().delete(entity);
    return true;
  }

  @SuppressWarnings("unchecked")
  public List<T> findAll(Class<T> entityClazz) {
    // TODO Auto-generated method stub
    String hql = " from " + entityClazz.getName();
    return sessionFactory.getCurrentSession().createQuery(hql).list();
  }

  public boolean saveOrUpdate(T entity) {
    // TODO Auto-generated method stub
    sessionFactory.getCurrentSession().saveOrUpdate(entity);
    return true;
  }

  /**
   * 根据HQL查找指定的对象，该对象只能有一个
   */
  public T loadModel(String hql, Object... params) {
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    // 向query实例中添加参数。
    for(int index = 0, len = params.length; index < len; index++) {
      query.setParameter(index, params[index]);
    }
    // 设置只取出一个对象
    query.setFirstResult(0);
    query.setMaxResults(1);
    @SuppressWarnings("unchecked")
    List<T> resultList = query.list();
    if (resultList.size() > 0) {
      return resultList.get(0);
    }
    return null;
  }
  
  /**
   * 根据传入的分页数据导出对应信息
   */
  public List<T> listPart(int pageNo, int pageSize, String hql, Object...params) {
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    for (int index = 0, len = params.length; index < len; index++) {
      query.setParameter(index, params[index]);
    }
    query.setMaxResults(pageSize);
    query.setFirstResult((pageNo - 1)*pageSize);
    @SuppressWarnings("unchecked")
    List<T> resultList = query.list();
    return resultList;
  }

  /**
   * 
   */
  public boolean merge(T entity) {
    // TODO Auto-generated method stub
    sessionFactory.getCurrentSession().merge(entity);
    return true;
  }
}
