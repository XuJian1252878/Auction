package com.auction.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IProductTagDao;
import com.auction.model.Product;
import com.auction.model.ProductTag;
import com.auction.service.IProductTagService;
import com.auction.service.common.BaseService;

@Service("productTagService")
@Transactional
public class ProductTagServiceImpl extends BaseService<ProductTag> implements IProductTagService {

  @Resource(name = "productTagDao")
  IProductTagDao productTagDao;

  public List<String> splitTags(String tags, String delimiter) {
    // TODO Auto-generated method stub
    List<String> tagList = new ArrayList<String>();
    if (tags == null) {
      tagList.add(tags);
      return tagList;
    }
    try{
      tagList = Arrays.asList(tags.split(delimiter));
    } catch (PatternSyntaxException e) {
      tagList.add(tags);
      e.printStackTrace();
    }
    return tagList;
  }

  public boolean saveTag(String tag, int productId) {
    // TODO Auto-generated method stub
    ProductTag productTag = new ProductTag();
    productTag.setTag(tag);
    Product product = new Product();
    product.setId(productId);
    productTag.getProducts().add(product);
    Serializable serializable = productTagDao.save(productTag);
    if (serializable == null) {
      return false;
    }
    return true;
  }
}
