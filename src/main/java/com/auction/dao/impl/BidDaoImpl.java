package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.IBidDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.Bid;

@Repository("bidDao")  // 表示这个是数据库存储层。
public class BidDaoImpl extends BaseDaoImpl<Bid> implements IBidDao {

}
