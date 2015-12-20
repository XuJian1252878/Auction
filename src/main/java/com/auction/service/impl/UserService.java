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

  @Resource(name = "userDao")
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
    if (userDao.save(user) != null) {
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

  /**
   * 根据注册邮箱找出对应的用户 邮箱对于每个用户来说是唯一的
   */
  public User getUserByEmail(String email) {
    return userDao.getUserByEmail(email);
  }

  /**
   * 根据用户名称找出对应的用户 用户名称对每个用户来说是唯一的
   */
  public User getUserByName(String userName) {
    return userDao.getUserByName(userName);
  }

  public boolean updateUser(User user) {
    // TODO Auto-generated method stub
    if (userDao.getUserById(user.getId()) == null) {
      return false;
    }
    // org.hibernate.NonUniqueObjectException: A different object with the same identifier value was already associated with the session
    // http://fatkun.com/2011/04/org-hibernate-nonuniqueobjectexception.html
    // http://www.stevideter.com/2008/12/07/saveorupdate-versus-merge-in-hibernate/
    // userDao.update(user);
    // 留下一个坑，外键元素会不会丢失，因为没有 cascade = "merge" 的声明
    userDao.merge(user);
    return true;
  }

  /**
   * 判断新用户是否符合注册条件，用户名和注册邮箱都唯一。
   */
  public boolean existsUser(User user) {
    // TODO Auto-generated method stub
    // 查看数据库中有无相通名称或者注册邮箱的用户，如果有那么返回原页面重新注册
    if (getUserByEmail(user.getEmail()) != null || getUserByName(user.getUserName()) != null) {
      // 该用户名称或者用户邮箱已经被注册
      return false;
    }
    return true;
  }
}
