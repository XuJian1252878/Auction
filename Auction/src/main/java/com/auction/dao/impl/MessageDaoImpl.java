package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.IMessageDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.Message;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements IMessageDao {

}
