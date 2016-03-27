package com.auction.service;

import java.io.Serializable;
import java.util.List;

import com.auction.model.Bid;

public interface IBidService {

  /**
   * 为竞价用户创建一个新的竞价信息，如果用户在该商品下有过竞价，那么将原有的竞价信息替换。
   * @param bid  竞价信息的实体
   * @return  若创建成功，那么返回新建实体的id信息。否则返回null。
   */
  public Serializable saveUserBid(Bid bid);

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
}
