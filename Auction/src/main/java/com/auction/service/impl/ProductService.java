package com.auction.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IBidDao;
import com.auction.dao.IProductDao;
import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.service.IProductService;
import com.auction.service.common.BaseService;

@Service("productService")
@Transactional
public class ProductService extends BaseService<Product> implements IProductService {

  @Resource(name = "bidDao")
  IBidDao bidDao;

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

  public Product getProductById(int productId) {
    // TODO Auto-generated method stub
    Product product = productDao.get(Product.class, productId);
    return product;
  }

  public List<Product> getGoingOnProductsByUser(int userId) {
    // TODO Auto-generated method stub
    String phql = "from " + Product.class.getName()
        + " as p where p.user.id = ? and p.endDate > ? order by p.onSaleDate desc";
    List<Product> goingOnProducts = productDao.find(phql, userId, new Date());
    // 然后有可能交易最后期限还没有到，交易就已经成功，要去除这一类的商品。
    for (Product product : goingOnProducts) {
      int pid = product.getId();
      String bhql = "select count(*) from " + Bid.class.getName() + " as b where b.product.id = " + pid
          + " and b.isSuccess = true";
      int dealBidCount = bidDao.findCount(bhql);
      if (dealBidCount != 0) {
        goingOnProducts.remove(product);
      }
    }
    return goingOnProducts;
  }

  public List<Product> getHistoryProductsByUser(int userId) {
    // TODO Auto-generated method stub
    String hql = "from " + Product.class.getName()
        + " as p where p.user.id = ? and p.endDate < ? order by p.onSaleDate desc";
    List<Product> historyProducts = productDao.find(hql, userId, new Date());
    return historyProducts;
  }

}
