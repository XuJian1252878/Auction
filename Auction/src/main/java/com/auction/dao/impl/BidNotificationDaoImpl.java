package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.IBidNotificationDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.BidNotification;

@Repository("bidNotificationDao")
public class BidNotificationDaoImpl extends BaseDaoImpl<BidNotification> implements IBidNotificationDao {

}
