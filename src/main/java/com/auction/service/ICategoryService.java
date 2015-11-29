package com.auction.service;

import java.util.List;

import com.auction.model.Category;

public interface ICategoryService {
  /**
   * 根据分页信息存取category实体
   * @param pageNo
   * @param pageSize
   * @return List<Category>
   */
  public List<Category> loadPart(int pageNo, int pageSize);
  
  /**
   * 统计 category 记录的总数
   * @return 
   */
  public int count();
  
  /**
   * 根据名称unique的原则创建一个新的category
   * @param category
   * @return
   */
  public boolean newCategory(Category category);
  
  /**
   * 根据类别id删除商品类别信息
   * @param categoryId
   * @return
   */
  public boolean deleteCategory(int categoryId);
  
  /**
   * 找出所有符合条件的父类别信息，在这里假设最多只能有两层类别，
   * 这个函数实际上就是需要找出所有的一级类别。
   * @return
   */
  public List<Category> getParentCategories();
  
  /**
   * 根据id信息取出category的实体
   * @param categoryId
   * @return 实体存在，那么返回对应id的实体，实体不存在，那么放回null
   */
  public Category getCategory(int categoryId);
  
  /**
   * 更新商品类别信息
   * @param category
   * @return
   */
  public boolean updateCategory(Category category);
}
