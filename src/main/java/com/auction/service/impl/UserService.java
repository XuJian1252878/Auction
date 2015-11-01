package com.auction.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.dao.IUserDao;
import com.auction.model.User;
import com.auction.service.IUserService;
import com.auction.service.common.BaseService;

@Service("userService")
@Transactional
public class UserService extends BaseService<User> implements IUserService {
  
  @Resource(name="userDao")
  private IUserDao userDao;
  
  public int getUserCount() {
    // TODO Auto-generated method stub
    return userDao.getUserCount();
  }

  public List<User> getAllUser() {
    // TODO Auto-generated method stub
    return userDao.findAll(User.class);
  }

  public boolean createUser(User user) {
    // TODO Auto-generated method stub
    if(userDao.save(user) != null) {
      return true;
    }
    return false;
  }

  public User findUserById(int id) {
    // TODO Auto-generated method stub
    return userDao.getUserById(new Integer(id));
  }

  public boolean delUserById(int id) {
    // TODO Auto-generated method stub
    return userDao.delete(User.class, id);
  }

  public boolean saveUser(User user) {
    // TODO Auto-generated method stub
    return userDao.update(user);
  }

}
