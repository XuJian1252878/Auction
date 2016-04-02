package com.auction.service;

import java.util.List;

import com.auction.model.Category;
import com.auction.model.Product;

public interface ICategoryService {
  /**
   * 根据分页信息存取category实体
   * @param pageNo
   * @param pageSize 如果pageNo值和pageSize的值同时为-1， 那么表示返回全部的结果。
   * @return List<Category>
   */
  public List<Category> loadCategory(int pageNo, int pageSize);

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

  /**
   * 根据分页限制返回某一商品类别下的商品。
   * @param categoryId 商品类别的id信息。
   * @param pageNo 要取出的是哪一页的数据
   * @param pageSize 每一页数据的数量是多少。如果pageNo值和pageSize的值同时为-1， 那么表示返回全部的结果。
   * @param containsBid true表示返回的结果中包含已竞价完成的商品以及未竞价完成的商品；false 表示返回的结果中仅包含还未成交的商品。
   * @return
   */
  public List<Product> loadProducts(int categoryId, int pageNo, int pageSize, boolean containsBid);

  /**
   * 获得该商品类别下商品的总数。
   * @param categoryId  商品类别的id信息。
   * @param containsBiding 是否包含已完成竞价的商品。true表示包含，false表示不包含。
   * @return 该商品类别下的商品总数。
   */
  public int getProductCount(int categoryId, boolean containsBiding);
}
