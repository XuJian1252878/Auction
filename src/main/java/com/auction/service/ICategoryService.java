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
}
