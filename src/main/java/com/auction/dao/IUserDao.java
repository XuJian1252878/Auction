package com.auction.dao;

import com.auction.dao.common.IBaseDao;
import com.auction.model.User;

public interface IUserDao extends IBaseDao<User> {
  public User getUserById(Integer id);
  public int getUserCount();
}
