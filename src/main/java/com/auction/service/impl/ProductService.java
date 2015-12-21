package com.auction.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IProductDao;
import com.auction.model.Product;
import com.auction.service.IProductService;
import com.auction.service.common.BaseService;

@Service("productService")
@Transactional
public class ProductService extends BaseService<Product> implements IProductService {

  @Resource(name = "productDao")
  IProductDao productDao;

  public boolean createProduct(Product product) {
    // TODO Auto-generated method stub
    Serializable serializable = productDao.save(product);
    if (serializable == null) {
      return false;
    }
    return true;
  }

  public List<Product> loadProduct(int pageNo, int pageSize) {
    // TODO Auto-generated method stub
    String hql = "from " + Product.class.getName() + " as p order by p.endDate desc";
    List<Product> products = null;
    if (pageNo == -1 && pageSize == -1) {
      products = productDao.findAll(Product.class);
    } else {
      products = productDao.listPart(pageNo, pageSize, hql);
    }
    return products;
  }

}
