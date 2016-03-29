package com.auction.service;

import java.util.List;

import com.auction.model.BidNotification;

public interface IMessageService {
  /**
   * 存储用户竞价成功的消息进入数据库。
   * @param bidId  竞价成功消息的id。
   * @return  返回竞价成功消息是否存入数据库，如果是返回true，不是返回false。
   */
  public boolean saveBidSuccessNotification(int bidId);

  /**
   * 获得某一个用户未读消息的数量。
   * @param userId  用户实体的id。
   * @return 返回用户未读消息的总数，int类型。 
   */
  public int getUnreadMessageCount(int userId);

  /**
   * 获取全部未读的竞价消息。
   * @userId  用户实体的id。
   * @return  返回未读竞价消息的list，如果没有未读的竞价消息，则返回一个空的list。
   */
  public List<BidNotification> getUnreadBidNotifications(int userId);

  /**
   * 获取全部的历史竞价消息。
   * @param userId  用户实体的id。
   * @return  返回历史竞价消息的list，如果没有历史竞价消息，则返回一个空的list。
   */
  public List<BidNotification> getHistoryBidNotification(int userId);
  
  /**
   * 将竞价通知消息标记为已读。
   * @param notificationId  需要标记为已读的竞价通知消息id。
   * @return  如果标记已读成功，那么返回true；否则就返回false。
   */
  public boolean markBidNotificationAsRead(int notificationId);
}
