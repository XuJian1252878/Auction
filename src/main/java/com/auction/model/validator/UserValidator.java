package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.User;

public class UserValidator implements Validator {

  /**
   * 指明这个Validator能够支持validate的类，这个validator只适用于检验User类的 数据是否正确
   */
  public boolean supports(Class<?> clazz) {
    // TODO Auto-generated method stub
    return User.class.equals(clazz);
  }

  public void validate(Object obj, Errors errors) {
    // TODO Auto-generated method stub
    // 首先注册的各个域不能是空值
    ValidationUtils.rejectIfEmpty(errors, "userName", "register.user.name.empty");
    ValidationUtils.rejectIfEmpty(errors, "age", "register.user.age.empty");
    ValidationUtils.rejectIfEmpty(errors, "address", "register.user.address.empty");
    ValidationUtils.rejectIfEmpty(errors, "password", "register.user.password.empty");
    User user = (User) obj;
    if (user.getAge() != null && (user.getAge() <= 0 || user.getAge() >= 100)) {
      errors.rejectValue("age", "register.user.age.scope");
    }
    // 密码和确认密码需要相同
    if (!user.getPassword().equals(user.getConfirmPassword())) {
      errors.rejectValue("confirmPassword", "register.user.pwd.notsame");
    }
  }

}
