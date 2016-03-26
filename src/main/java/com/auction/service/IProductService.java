package com.auction.service;

import java.util.List;

import com.auction.model.Product;

public interface IProductService {
  
  /**
   * 创建一个新的product实体
   * @param product
   * @return
   */
  public boolean createProduct(Product product);

  /**
   * 根据分页信息存取product实体。
   * 如果pageNo值和pageSize的值同时为-1， 那么表示返回全部的结果。
   * @param pageNo
   * @param pageSize
   * @return
   */
  public List<Product> loadProduct(int pageNo, int pageSize);

  /**
   * 根据商品的id信息获取id的实体
   * @param productId  提供的商品id信息
   * @return  若实体存在，那么返回实体对象；否则返回null。
   */
  public Product getProductById(int productId);
}
