package com.auction.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.ICategoryDao;
import com.auction.model.Category;
import com.auction.service.ICategoryService;
import com.auction.service.common.BaseService;

@Service("categoryService")
@Transactional
public class CategoryService extends BaseService<Category> implements ICategoryService {

  @Resource(name = "categoryDao")
  private ICategoryDao categoryDao;

  public List<Category> loadPart(int pageNo, int pageSize) {
    // TODO Auto-generated method stub
    String hql = "from " + Category.class.getName() + " as c order by c.id desc";
    List<Category> resultList = categoryDao.listPart(pageNo, pageSize, hql);
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

}
