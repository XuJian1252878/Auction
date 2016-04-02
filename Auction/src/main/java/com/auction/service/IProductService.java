package com.auction.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.auction.model.Bid;
import com.auction.model.Product;

public interface IProductService {
  
  /**
   * 创建一个新的product实体
   * @param product 将要持久化的新创建的product实体。
   * @return  如果实体创建成功，那么返回该实体的主键Serializable对象，否则就返回null。
   */
  public Serializable createProduct(Product product);

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

  /**
   * 更新一个商品的详情信息。
   * @param product  需要更新的商品实体。
   * @return  true表示更新成功；false表示更新失败。
   */
  public boolean updateProduct(Product product);

  /**
   * 根据标签的内容找到被该标签标注的商品的信息。
   * @param tags  传入的标签信息，是一个字符串，以某一个delimeter分割。
   * @param pageNo  显示第几页的查询结果。
   * @param pageSize  每一页显示多少个查询结果。
   * @return  返回一个包含商品信息的list列表，如果没有符合条件的商品信息，那么返回一个空的list。
   */
  public List<Product> getProductByTags(String tags, int pageNo, int pageSize);

  /**
   * 统计出被tags标签标注的商品的总数。
   * @param tags  传入的标签信息，是一个字符串，以某一个delimeter分割。
   * @return  返回被tags标签标注的商品的总数。
   */
  public int getProductCountByTags(String tags);
}
