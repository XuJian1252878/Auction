package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.Comment;

public class CommentValidator implements Validator {

  public boolean supports(Class<?> clazz) {
    // TODO Auto-generated method stub
    return Comment.class.equals(clazz);
  }

  public void validate(Object target, Errors errors) {
    // TODO Auto-generated method stub
    ValidationUtils.rejectIfEmpty(errors, "commentText", "comment.content.empty");
    Comment comment = (Comment)target;
    // 用户对商品的评论文本不得小于10个字。
    if (comment.getCommentText().length() < 10) {
      errors.rejectValue("commentText", "comment.content.length.not.enough");
    }
  }

}
