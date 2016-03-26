package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.ICategoryDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.Category;
import com.auction.model.Product;

@Repository("categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements ICategoryDao {

  public int getProductCount(int categoryId) {
    // TODO Auto-generated method stub
    // hql中通过外键的查询方法。
    String hql = "select count(*) from " + Product.class.getName() + " as p where p.category.id = " + categoryId;
    return findCount(hql);
  }

}
