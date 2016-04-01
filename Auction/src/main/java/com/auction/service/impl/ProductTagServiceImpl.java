package com.auction.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IProductDao;
import com.auction.dao.IProductTagDao;
import com.auction.model.Product;
import com.auction.model.ProductTag;
import com.auction.service.IProductTagService;
import com.auction.service.common.BaseService;
import com.auction.util.WebConstantUtil;

@Service("productTagService")
@Transactional
public class ProductTagServiceImpl extends BaseService<ProductTag> implements IProductTagService {

  @Resource(name = "productDao")
  IProductDao productDao;
  
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

  public Serializable saveTag(String tag, Product product) {
    // TODO Auto-generated method stub
    ProductTag productTag = new ProductTag();
    productTag.setTag(tag);
    productTag.getProducts().add(product);
    return productTagDao.save(productTag);
  }

  public boolean saveTags(String tags, int productId) {
    // TODO Auto-generated method stub
    // 取出对应的product对象。
    Product product = productDao.get(Product.class, productId);
    if (product == null) {
      return false;
    }
    List<String> tagList = splitTags(tags, WebConstantUtil.PRODUCT_TAG_DELIMETER);
    for (String tag: tagList) {
      ProductTag productTag = new ProductTag();
      productTag.setTag(tag);
      // 在productTag与product的关系中，productTag一方是owner size，所以要保证owner的一方管理关系。所以一定要加上接下来的一句。
      // http://stackoverflow.com/questions/19280121/spring-and-or-hibernate-saving-many-to-many-relations-from-one-side-after-form
      productTag.getProducts().add(product);
      Serializable serializable = productTagDao.save(productTag);
      if (serializable == null) {
        return false;
      }
    }
    return true;
  }

  public List<ProductTag> getTagsByProduct(int productId) {
    // TODO Auto-generated method stub
    String hql = "select t from " + Product.class.getName() + " p join p.productTags t where p.id = ?";
    List<ProductTag> pTags = productTagDao.find(hql, productId);
    return pTags;
  }
}
