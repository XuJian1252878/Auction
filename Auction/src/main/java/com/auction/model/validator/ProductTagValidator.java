package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.ProductTag;

public class ProductTagValidator implements Validator {

  public boolean supports(Class<?> clazz) {
    // TODO Auto-generated method stub
    return ProductTag.class.equals(clazz);
  }

  public void validate(Object target, Errors errors) {
    // TODO Auto-generated method stub
    ValidationUtils.rejectIfEmpty(errors, "tag", "product.tag.content.empty");
  }

}
