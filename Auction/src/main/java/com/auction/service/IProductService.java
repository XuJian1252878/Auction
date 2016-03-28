package com.auction.service;

import java.util.List;
import java.util.Map;

import com.auction.model.Bid;
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

  /**
   * 获得用户上传的，并且正在被竞价的商品列表。商品列表按照竞拍的数量对商品进行降序排序。
   * @param userId  上传商品的用户id信息。
   * @return  返回一个包含商品实体的list列表，如果没有符合条件的商品信息，那么返回一个空的list。
   */
  public List<Product> getGoingOnProductsByUser(int userId);

  /**
   * 获得用户上传的，并且竞价过程已经结束的<商品, 成交竞价>map。map按照商品上传的时间降序排序。
   * @param userId  上传商品的用户id信息。
   * @return  返回一个包含<商品, 成交竞价>map，如果没有符合条件的商品信息，那么返回一个空的Map。
   */
  public Map<Product, Bid> getHistoryProductsByUser(int userId);
}
