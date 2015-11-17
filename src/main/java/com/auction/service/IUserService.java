package com.auction.service;

import java.util.List;

import com.auction.model.User;

public interface IUserService {

  public int getUserCount();
  
  public List<User> getAllUser();
  
  public boolean createUser(User user);
  
  public User findUserById(int id);
  
  public boolean delUserById(int id);
  
  public boolean saveUser(User user);
  
  public User getUserByEmail(String userName);
  
  public User getUserByName(String userName);
}
