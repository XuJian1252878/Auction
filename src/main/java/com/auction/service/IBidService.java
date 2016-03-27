package com.auction.service;

import java.io.Serializable;

import com.auction.model.Bid;

public interface IBidService {

  /**
   * 为竞价用户创建一个新的竞价信息。
   * @param bid  竞价信息的实体
   * @return  若创建成功，那么返回新建实体的id信息。否则返回null。
   */
  public Serializable createBid(Bid bid);

}
