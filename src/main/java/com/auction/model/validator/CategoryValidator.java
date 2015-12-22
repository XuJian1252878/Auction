package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.Category;
import com.auction.util.FileUtil;
import com.auction.util.ImageUtil;

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
    
    // 类别图片的检查
    if (category.getId() == null) {
      if (FileUtil.isEmptyFile(category.getImgFile())) {
        errors.rejectValue("imgFile", "category.no.img.file");
        return;
      }
    } else if (FileUtil.meetSizeRestrict(category.getImgFile(), 1024 * 1024)) {
      errors.rejectValue("imgFile", "category.img.file.too.large");
      return;
    }
    // 检查图片的后缀名称
    if (FileUtil.getFileSuffix(category.getImgFile().getOriginalFilename()) == null) {
      errors.rejectValue("imgFile", "category.no.img.file");
      return;
    }
    if (! ImageUtil.checkImgType(category.getImgFile().getOriginalFilename())) {
      errors.rejectValue("img", "category.img.file.suffix.error");
    }
  }

}
