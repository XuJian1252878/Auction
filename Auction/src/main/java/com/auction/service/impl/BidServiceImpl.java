package com.auction.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IBidDao;
import com.auction.model.Bid;
import com.auction.service.IBidService;
import com.auction.service.common.BaseService;

@Service("bidService")
@Transactional // 将service至于一个transaction中执行。
public class BidServiceImpl extends BaseService<Bid> implements IBidService {

  @Resource(name = "bidDao")
  IBidDao bidDao;

  public Serializable saveUserBid(Bid bid) {
    // TODO Auto-generated method stub
    Bid oldBid = getBid(bid.getUser().getId(), bid.getProduct().getId());
    if (oldBid == null) {
      // 说明用户对该商品之前没有出过竞价。
      return bidDao.save(bid);
    } else {
      // 说明用户对该商品之前出过竞价。
      oldBid.setBindDate(bid.getBindDate());
      oldBid.setPrice(bid.getPrice());
      bidDao.update(oldBid);
      return oldBid.getId();
    }
  }

  public Bid getBid(int userId, int productId) {
    // TODO Auto-generated method stub
    String hql = "from " + Bid.class.getName() + " as b where b.user.id = ? and b.product.id = ?";
    List<Bid> bids = bidDao.find(hql, userId, productId);
    if (bids.size() != 1) {
      return null;
    }
    return new ArrayList<Bid>(bids).get(0);
  }
}
