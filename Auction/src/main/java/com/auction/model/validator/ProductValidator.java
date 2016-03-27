package com.auction.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.auction.model.Product;
import com.auction.util.FileUtil;
import com.auction.util.ImageUtil;

public class ProductValidator implements Validator {

  public boolean supports(Class<?> clazz) {
    // TODO Auto-generated method stub
    return Product.class.equals(clazz);
  }

  public void validate(Object obj, Errors errors) {
    // TODO Auto-generated method stub
    // 各个必要的域不能是空值
    ValidationUtils.rejectIfEmpty(errors, "name", "product.name.empty");
    ValidationUtils.rejectIfEmpty(errors, "describe", "product.describe.empty");

    Product product = (Product)obj;
    if (product.getCategory().getId() == -1) {
      errors.rejectValue("category.id", "product.no.category");
    }

    if (product.getBasicPrice() < 0) {
      errors.rejectValue("basicPrice", "product.base.price.error");
    }

    // 上传图片的检查
    if (product.getId() == null) {
      // 说明是新上传商品的情况，而不是更新商品信息的情况。
      if (FileUtil.isEmptyFile(product.getImgFile())) {
        errors.rejectValue("imgFile", "product.no.img.file");
        return;
      }
    } else if (FileUtil.meetSizeRestrict(product.getImgFile(), 1024 * 1024)) {
      errors.rejectValue("imgFile", "product.img.file.too.large");
      return;
    }

    // 检查图片的后缀名称
    if (FileUtil.getFileSuffix(product.getImgFile().getOriginalFilename()) == null) {
      errors.rejectValue("imgFile", "product.no.img.file");
      return;
    }
    if (! ImageUtil.checkImgType(product.getImgFile().getOriginalFilename())) {
      errors.rejectValue("imgFile", "product.img.file.suffix.error");
    }
  }
}
