package com.auction.service;

import java.util.List;
import java.util.Map;

import com.auction.model.Bid;

public interface IBidService {

  /**
   * 为竞价用户创建一个新的竞价信息，如果用户在该商品下有过竞价，那么将原有的竞价信息替换。
   * @param bid  竞价信息的实体
   * @return  返回一个包含处理结果信息的map，key:result 处理结果是否成功（value：0 处理成功；1 处理失败，用户出价低于
   * 最低竞拍价；2 处理失败，数据库错误）
   */
  public Map<String, Object> saveUserBid(Bid bid);

  /**
   * 取出用户之前对某个商品的竞价信息。
   * @param userId  登陆用户的id信息。
   * @param productId  登陆用户将要进行竞价的商品id信息。
   * @return  若竞价信息存在，那么返回竞价信息实体；否则返回null。
   */
  public Bid getBid(int userId, int productId);
  
  /**
   * 获得某一个商品下的所有竞价信息。
   * @param productId  商品的id信息。
   * @return  返回该商品下竞价信息的list列表，若无竞价信息，那么返回空的list。
   */
  public List<Bid> getAllBidsByProduct(int productId);

  /**
   * 获取某用户正在进行的竞价信息，竞价信息以时间顺序降序排序。
   * @param userId  用户实体的id信息。
   * @return  若竞价信息存在，那么返回竞价信息实体；若无竞价信息，那么返回空的list。
   */
  public List<Bid> getGoingOnBids(int userId);

  /**
   * 获取某用户已经完成的竞价信息（包括竞价的失败与否），竞价信息以时间顺序降序排列。
   * @param userId  用户实体的id信息。
   * @return 若竞价信息存在，那么返回竞价信息实体；若无竞价信息，那么返回空的list。
   */
  public List<Bid> getHistoryBids(int userId);
  
  /**
   * 获取某用户已经成交的竞价信息，竞价信息以时间顺序降序排列。
   * @param userId  用户实体的id信息。
   * @return  若竞价信息存在，那么返回竞价信息实体；若无竞价信息，那么返回空的list。
   */
  public List<Bid> getDealBids(int userId);

  /**
   * 将相应的bid的竞价状态改为竞价成功。
   * @param bid  相应的竞价实体信息
   * @return  如果bid信息更新成功，那么返回true；否则返回false。
   */
  public boolean setBidDeal(Bid bid);

  /**
   * 更新商品的竞价。
   * @param bidId  需要更新竞价价格的 bid 实体id。
   * @param price  更改之后的竞价价格值。
   * @return  竞价价格修改成功，那么返回true，否则返回false。
   */
  public boolean modifyBidPrice(int bidId, float price);
}
