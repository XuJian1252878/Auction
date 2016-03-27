package com.auction.dao;

import com.auction.dao.common.IBaseDao;
import com.auction.model.Category;

public interface ICategoryDao extends IBaseDao<Category> {

  // 获得该商品类别下商品的总数量。
  public int getProductCount(int categoryId);

}