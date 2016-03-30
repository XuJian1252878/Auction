package com.auction.service;

import java.util.List;
import java.util.Map;

import com.auction.model.User;

public interface IUserService {

  public int getUserCount();

  public List<User> getAllUser();

  /**
   * 通过用户名称或者用户注册邮箱检查用户是否存在，用于注册新用户的时候检查账户名和邮箱是否已经被注册。
   * @param user 注册新用户的用户信息实体。
   * @return  0 表示用户名称已经被注册；1 表示用户邮箱已经被注册；2表示用户名和邮箱都没有被占用，新用户可以注册。
   */
  public int existsUser(User user);
  
  public boolean createUser(User user);
  
  public boolean updateUser(User user);
  
  public User findUserById(int id);
  
  public boolean delUserById(int id);
  
  public boolean saveUser(User user);

  /**
   * 通过用户邮箱取得对应的用户实体。
   * @param userEmail  用户邮箱信息。
   * @return  返回邮箱对应的用户实体。
   */
  public User getUserByEmail(String userEmail);

  /**
   * 通过用户名称返回对应的用户实体。
   * @param userName  用户名称信息。
   * @return  返回用户名称对应的用户实体信息。
   */
  public User getUserByName(String userName);

  /**
   * 通过用户名称或者用户邮箱登陆用户账户
   * @param userNameOrEmail  用户名称，或者是用户邮箱信息。
   * @param userPwd  用户输入的登陆密码信息。
   * @return 返回一个包含登陆结果flag和用户实体的map。
   * map返回的格式有以下几种：
   * key = ConstantUtil.USER_LOGIN_SUCCESS_FLAG  value = 0 表示用户名或者是用户邮箱不存在；value = 1 表示用户密码输入错误； value = 2 表示用户登录成功。
   * key = ConstantUtil.USER_LOGIN_OBJECT_FLAG 如果result key对应的值为 0或者 1，那么user键的值为 null，result key对应的值为2，user键的值为 登陆成功的用户实体。
   */
  public Map<String, Object> userLogin(String userNameOrEmail, String userPwd);
}
