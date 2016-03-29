package com.auction.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IMessageDao;
import com.auction.model.Message;
import com.auction.service.IMessageService;
import com.auction.service.common.BaseService;

@Service("messageService")
@Transactional
public class MessageService extends BaseService<Message> implements IMessageService {
  
  @Resource(name = "messageDao")
  private IMessageDao messageDao;

}
