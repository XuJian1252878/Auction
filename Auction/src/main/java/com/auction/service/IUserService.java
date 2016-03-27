package com.auction.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.auction.model.User;

public interface IUserService {

  public int getUserCount();
  
  public List<User> getAllUser();
  
  /**
   * 检查注册用户是否存在。
   * @param user  注册的用户实体对象。
   * @param result  BindingResult，该对象用来收集注册过程中的具体错误信息。
   * @return  如果用户名，或者用户邮箱已经存在，那么返回false，注册失败；否则注册成功。
   */
  public boolean existsUser(User user, BindingResult result);
  
  public boolean createUser(User user);
  
  public boolean updateUser(User user);
  
  public User findUserById(int id);
  
  public boolean delUserById(int id);
  
  public boolean saveUser(User user);
  
  public User getUserByEmail(String userName);
  
  public User getUserByName(String userName);
}
