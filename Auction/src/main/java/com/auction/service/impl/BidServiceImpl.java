package com.auction.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IBidDao;
import com.auction.model.Bid;
import com.auction.service.IBidService;
import com.auction.service.common.BaseService;

@Service("bidService")
@Transactional  // 将service至于一个transaction中执行。
public class BidServiceImpl extends BaseService<Bid> implements IBidService {

  @Resource(name = "bidDao")
  IBidDao bidDao;

  public Serializable createBid(Bid bid) {
    // TODO Auto-generated method stub
    return bidDao.save(bid);
  }
}
