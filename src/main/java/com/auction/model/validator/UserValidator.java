package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.User;
import com.auction.util.FileUtil;
import com.auction.util.ImageUtil;

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
    ValidationUtils.rejectIfEmpty(errors, "email", "register.user.email.empty");
    User user = (User) obj;
    if (user.getAge() != null && (user.getAge() <= 0 || user.getAge() >= 100)) {
      errors.rejectValue("age", "register.user.age.scope");
    }
    // 密码和确认密码需要相同
    if (user.getId() == null && user.getPassword() != null && !user.getPassword().equals(user.getConfirmPassword())) {
      errors.rejectValue("confirmPassword", "register.user.pwd.notsame");
    }
    if (user.getId() == null) {
      // 说明是用户注册时的情况，而不是用户更新个人信息时的情况。
      if (FileUtil.isEmptyFile(user.getAvatarFile())) {
        errors.rejectValue("avatarFile", "register.user.no.avatar.file");
        return; // 图片没上传，就没有检查图片后缀名称的必要。
      }
    } else if (FileUtil.meetSizeRestrict(user.getAvatarFile(), 1024 * 1024)) {
      errors.rejectValue("avatarFile", "register.user.avatar.file.toolarge");
      return;
    }
    // 检查图片文件的后缀名信息
    if (FileUtil.getFileSuffix(user.getAvatarFile().getOriginalFilename()) == null) {
      errors.rejectValue("avatarFile", "register.user.no.avatar.file");
      return; // 后缀名称都没有，那就没有检查后缀名称合法性的必要。
    }

    // 检查图片类型的合法性
    if (! ImageUtil.checkImgType(user.getAvatarFile().getOriginalFilename())) {
      errors.rejectValue("avatarFile", "register.user.avatar.file.suffix.error");
    }
  }
}
