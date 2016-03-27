package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.IUserDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

  public User getUserById(Integer id) {
    // TODO Auto-generated method stub
    return get(User.class, id);
  }

  public int getUserCount() {
    // TODO Auto-generated method stub
    return findCount(User.class);
  }

  public User getUserByEmail(String email) {
    // TODO Auto-generated method stub
    return loadModel("from " + User.class.getName() + " where email = ? ", email);
  }

  public User getUserByName(String userName) {
    // TODO Auto-generated method stub
    return loadModel("from " + User.class.getName() + " where userName = ?", userName);
  }

}
