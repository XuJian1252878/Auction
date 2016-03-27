package com.auction.service;

import java.io.Serializable;

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
}
