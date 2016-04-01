package com.auction.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IBidNotificationDao;
import com.auction.dao.IMessageDao;
import com.auction.model.Bid;
import com.auction.model.BidNotification;
import com.auction.model.Message;
import com.auction.service.IMessageService;
import com.auction.service.common.BaseService;

@Service("messageService")
@Transactional
public class MessageServiceImpl extends BaseService<Message> implements IMessageService {

  @Resource(name = "messageDao")
  private IMessageDao messageDao;

  @Resource(name = "bidNotificationDao")
  private IBidNotificationDao bidNotificationDao;

  public boolean saveBidSuccessNotification(int bidId) {
    // TODO Auto-generated method stub
    BidNotification bNotification = new BidNotification();
    Bid bid = new Bid();
    bid.setId(bidId);
    bNotification.setBid(bid);
    bNotification.setIsRead(false);
    bidNotificationDao.save(bNotification);
    return true;
  }

  public int getUnreadMessageCount(int userId) {
    // TODO Auto-generated method stub
    String mhql = "select count(*) from " + Message.class.getName() + " as m where m.isRead = false";
    int mCount = messageDao.findCount(mhql);
    String nhql = "select count(*) from " + BidNotification.class.getName() + " as b where b.isRead = false";
    int nCount = bidNotificationDao.findCount(nhql);
    return mCount + nCount;
  }

  public List<BidNotification> getUnreadBidNotifications(int userId) {
    // TODO Auto-generated method stub
    String hql = "from " + BidNotification.class.getName() + " as b where b.isRead = false";
    List<BidNotification> notifications = bidNotificationDao.find(hql);
    return notifications;
  }

  public List<BidNotification> getHistoryBidNotification(int userId) {
    // TODO Auto-generated method stub
    String hql = "from " + BidNotification.class.getName() + " as b where b.isRead = true";
    List<BidNotification> notifications = bidNotificationDao.find(hql);
    return notifications;
  }

  public boolean markBidNotificationAsRead(int notificationId) {
    // TODO Auto-generated method stub
    BidNotification bn = bidNotificationDao.get(BidNotification.class, notificationId);
    if (bn == null) {
      return false;
    }
    // 将该竞价通知消息设置为已读。
    bn.setIsRead(true);
    bidNotificationDao.update(bn);
    return true;
  }

}
