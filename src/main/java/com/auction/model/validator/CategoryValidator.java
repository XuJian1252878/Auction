package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.Category;

public class CategoryValidator implements Validator {

  public boolean supports(Class<?> clazz) {
    // TODO Auto-generated method stub
    return Category.class.equals(clazz);
  }

  public void validate(Object obj, Errors errors) {
    // TODO Auto-generated method stub
    // name字段和cdesc字段不能为空
    ValidationUtils.rejectIfEmpty(errors, "name", "category.name.empty");
    ValidationUtils.rejectIfEmpty(errors, "cdesc", "category.cdesc.empty");
    Category category = (Category)obj;
    if (category.getCdesc() != null) {
      System.out.println("测试成功");
    }
  }

}
