package com.auction.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IBidDao;
import com.auction.dao.IProductDao;
import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.service.IBidService;
import com.auction.service.common.BaseService;
import com.auction.util.DateTimeUtil;

@Service("bidService")
@Transactional // 将service至于一个transaction中执行。
public class BidServiceImpl extends BaseService<Bid> implements IBidService {

  @Resource(name = "bidDao")
  IBidDao bidDao;
  @Resource(name = "productDao")
  IProductDao productDao;

  public Map<String, Object> saveUserBid(Bid bid) {
    // TODO Auto-generated method stub
    Map<String, Object> resMap = new HashMap<String, Object>();
    // 首先检查用户的出价是否低于商品的最低竞价。
    Product product = productDao.loadModel("from " + Product.class.getName() + " as p where p.id = ?", bid.getProduct().getId());
    if (product == null || product.getBasicPrice() > bid.getPrice()) {
      // 用户的出价信息低于商品的最低竞拍价。
      resMap.put("result", 1);
      return resMap;
    }
    Serializable serializable = null;
    Bid oldBid = getBid(bid.getUser().getId(), bid.getProduct().getId());
    if (oldBid == null) {
      // 说明用户对该商品之前没有出过竞价。
      serializable = bidDao.save(bid);
    } else {
      // 说明用户对该商品之前出过竞价。
      oldBid.setBidDate(bid.getBidDate());
      oldBid.setPrice(bid.getPrice());
      bidDao.update(oldBid);
      serializable = oldBid.getId();
    }
    if (serializable == null) {
      resMap.put("result", 2);  // 数据库操作失败。
    } else {
      resMap.put("result", 0);  // 数据库操作成功。
    }
    return resMap;
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

  public List<Bid> getAllBidsByProduct(int productId) {
    // TODO Auto-generated method stub
    String hql = "from " + Bid.class.getName() + " as b where b.product.id = ? order by b.price desc";
    return bidDao.find(hql, productId);
  }

  public List<Bid> getGoingOnBids(int userId) {
    // TODO Auto-generated method stub
    String curTimestampStr = DateTimeUtil.getCurrentTimeStamp("yyyy-MM-dd HH:mm:ss");
    String hql = "from " + Bid.class.getName() + " as b where b.user.id = ? and b.product.endDate >= '" + curTimestampStr
        + "' and b.isSuccess = false order by b.bidDate desc";
    return bidDao.find(hql, userId);
  }

  public List<Bid> getHistoryBids(int userId) {
    // TODO Auto-generated method stub
    String curTimestampStr = DateTimeUtil.getCurrentTimeStamp("yyyy-MM-dd HH:mm:ss");
    String hql = "from " + Bid.class.getName() + " as b where b.user.id = ? and ( b.product.endDate < '" + curTimestampStr
        + "' or b.isSuccess = true ) order by b.bidDate desc";
    return bidDao.find(hql, userId);
  }

  public List<Bid> getDealBids(int userId) {
    // TODO Auto-generated method stub
    String hql = "from " + Bid.class.getName() + " as b where b.user.id = ? and b.isSuccess = true order by b.dealDate desc";
    return bidDao.find(hql, userId);
  }

  public List<Bid> getFailedBids(int userId) {
    String hql = "from " + Bid.class.getName() + " as b where b.user.id = ? and b.isSuccess = false order by b.dealDate desc";
    return bidDao.find(hql, userId);
  }

  public boolean setBidDeal(Bid bid) {
    // TODO Auto-generated method stub
    bid.setIsSuccess(true);
    bid.setDealDate(new Date());
    bidDao.saveOrUpdate(bid);
    return true;
  }

  public boolean modifyBidPrice(int bidId, float price) {
    // TODO Auto-generated method stub
    Bid bid = bidDao.get(Bid.class, bidId);
    if (bid == null) {
      return false;
    }
    // 更新竞价的价格信息。
    bid.setPrice(price);
    bidDao.save(bid);
    return true;
  }

  public Bid getBidById(int bidId) {
    // TODO Auto-generated method stub
    Bid bid = bidDao.get(Bid.class, bidId);
    return bid;
  }

}
