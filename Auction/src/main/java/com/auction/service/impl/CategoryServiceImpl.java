package com.auction.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.ICategoryDao;
import com.auction.dao.IProductDao;
import com.auction.model.Category;
import com.auction.model.Product;
import com.auction.service.ICategoryService;
import com.auction.service.common.BaseService;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl extends BaseService<Category> implements ICategoryService {

  @Resource(name = "categoryDao")
  private ICategoryDao categoryDao;
  
  @Resource(name = "productDao")
  private IProductDao productDao;

  public List<Category> loadCategory(int pageNo, int pageSize) {
    // TODO Auto-generated method stub
    String hql = "from " + Category.class.getName() + " as c order by c.id desc";
    List<Category> resultList = null;
    if (pageSize == -1 && pageNo == -1) {
      resultList = categoryDao.findAll(Category.class);
    } else {
      resultList = categoryDao.listPart(pageNo, pageSize, hql);
    }
    return resultList;
  }

  public int count() {
    // TODO Auto-generated method stub
    return categoryDao.findCount(Category.class);
  }

  public boolean newCategory(Category category) {
    // TODO Auto-generated method stub
    String hql = "from " + Category.class.getName() + " where name = ? ";
    Category c = categoryDao.loadModel(hql, category.getName());
    if (c != null) {
      return false;
    }
    // 向数据库中添加新的category实体
    categoryDao.save(category);
    return true;
  }

  public boolean deleteCategory(int categoryId) {
    // TODO Auto-generated method stub
    return categoryDao.delete(Category.class, categoryId);
  }

  public List<Category> getParentCategories() {
    // TODO Auto-generated method stub
    // 首先取出所有的category
    List<Category> categories = categoryDao.findAll(Category.class);
    for (Category category : categories) {
      // 找到第一级类别
      if (category.getParentCategory() != null) {
        categories.remove(category);
      }
    }
    return categories;
  }

  public Category getCategory(int categoryId) {
    // TODO Auto-generated method stub
    // session 的get方法返回对应的实体或者null。
    return categoryDao.get(Category.class, categoryId);
  }

  public boolean updateCategory(Category category) {
    // TODO Auto-generated method stub
    // 这里一直是返回true的，因为session的update方法返回的是void。
    return categoryDao.update(category);
  }

  public List<Product> loadProducts(int categoryId, int pageNo, int pageSize, boolean containsBid) {
    // TODO Auto-generated method stub
    List<Product> products = null;
    String hql = "from " + Product.class.getName() + " as p where p.category.id = ?";  // 取出全部的商品信息。
    if (!containsBid) {
      // 仅取出还未拍卖成功的商品信息。
      hql = "from " + Product.class.getName() + " as p where p.category.id = ? and p.isDeal = false";
    }
    if (pageNo == -1 && pageSize == -1) {
      products = productDao.find(hql, categoryId);
    } else {
      products = productDao.listPart(pageNo, pageSize, hql, categoryId);
    }
    return products;
  }

  public int getProductCount(int categoryId, boolean containsBiding) {
    // TODO Auto-generated method stub
    return categoryDao.getProductCount(categoryId, containsBiding);
  }

}
