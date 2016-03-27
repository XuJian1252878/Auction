package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.Bid;

public class BidValidator implements Validator {

  public boolean supports(Class<?> clazz) {
    // TODO Auto-generated method stub
    return Bid.class.equals(clazz);
  }

  public void validate(Object target, Errors errors) {
    // TODO Auto-generated method stub
    ValidationUtils.rejectIfEmpty(errors, "price", "bid.price.empty");
  }

}
