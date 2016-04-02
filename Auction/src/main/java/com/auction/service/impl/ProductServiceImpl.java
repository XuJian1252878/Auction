package com.auction.service.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.dao.IBidDao;
import com.auction.dao.IProductDao;
import com.auction.dao.IProductTagDao;
import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.ProductTag;
import com.auction.service.IProductService;
import com.auction.service.common.BaseService;
import com.auction.util.WebConstantUtil;

@Service("productService")
@Transactional
public class ProductServiceImpl extends BaseService<Product> implements IProductService {

  @Resource(name = "bidDao")
  IBidDao bidDao;

  @Resource(name = "productDao")
  IProductDao productDao;

  @Resource(name = "productTagDao")
  IProductTagDao productTagDao;

  public Serializable createProduct(Product product) {
    // TODO Auto-generated method stub
    return productDao.save(product);
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
    String phql = "from " + Product.class.getName() + " as p where p.user.id = ? and p.endDate > ?";
    List<Product> goingOnProducts = productDao.find(phql, userId, new Date());
    // 然后有可能交易最后期限还没有到，交易就已经成功，要去除这一类的商品。
    for (Iterator<Product> iterator = goingOnProducts.iterator(); iterator.hasNext();) {
      Product product = iterator.next();
      int pid = product.getId();
      String bhql = "select count(*) from " + Bid.class.getName() + " as b where b.product.id = " + pid
          + " and b.isSuccess = true";
      int dealBidCount = bidDao.findCount(bhql);
      if (dealBidCount != 0) {
        iterator.remove();
      }
    }
    // 再按商品收到竞拍的数量对商品进行降序排序。
    Collections.sort(goingOnProducts, new Comparator<Product>() {
      public int compare(Product o1, Product o2) {
        // TODO Auto-generated method stub
        int bidCount1 = o1.getBids().size();
        int bidCount2 = o2.getBids().size();
        if (bidCount1 > bidCount2) {
          return -1;
        } else if (bidCount1 < bidCount2) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    return goingOnProducts;
  }

  public Map<Product, Bid> getHistoryProductsByUser(int userId) {
    // TODO Auto-generated method stub
    Map<Product, Bid> pbMap = new LinkedHashMap<Product, Bid>();
    String phql = "from " + Product.class.getName()
        + " as p where p.user.id = ? and (p.endDate < ? or p.isDeal = true) order by p.onSaleDate desc";
    // 获得用户上传的已经超过竞价期限的物品。
    List<Product> historyProducts = productDao.find(phql, userId, new Date());
    // 查询这些商品是否已经成交、或者是流拍。
    for (Product product : historyProducts) {
      // 检查该商品是否拍卖成功。
      String bhql = "from " + Bid.class.getName()
          + " as b where b.isSuccess = true and b.product.user.id = ? and b.product.id = ?";
      List<Bid> bids = bidDao.find(bhql, userId, product.getId());
      if (bids.size() == 1) {
        pbMap.put(product, bids.get(0));
      } else {
        pbMap.put(product, null);
      }
    }
    return pbMap;
  }

  public boolean updateProduct(Product product) {
    // TODO Auto-generated method stub
    productDao.update(product);
    return true;
  }

  public List<Product> getProductByTags(String tags, int pageNo, int pageSize) {
    // TODO Auto-generated method stub
    List<String> tagList = productTagDao.splitTags(tags, WebConstantUtil.PRODUCT_TAG_DELIMETER);
    String hql = "select p1 from " + Product.class.getName() + " as p1 where p1.id in ( select p2.id from "
        + ProductTag.class.getName() + " as pt inner join pt.products as p2 where pt.tag = '' ";
    // String hql = "select pt.products from " + ProductTag.class.getName() + "
    // as pt where pt.tag = '' ";
    // 开始构建根据tag查询商品的sql语句。
    for (String tag : tagList) {
      hql += ("or pt.tag like '%" + tag + "%' ");
    }
    hql += (")");
    List<Product> products = productDao.listPart(pageNo, pageSize, hql);
    return products;
  }

  public int getProductCountByTags(String tags) {
    // TODO Auto-generated method stub
    List<String> tagList = productTagDao.splitTags(tags, WebConstantUtil.PRODUCT_TAG_DELIMETER);
    // hql语句参考链接：http://stackoverflow.com/questions/13350858/count-the-number-of-rows-in-many-to-many-relationships-in-hibernate
    String hql = "select count(*) from " + Product.class.getName() + " as p1 where p1.id in ( select p2.id from "
        + ProductTag.class.getName() + " as pt inner join pt.products as p2 where pt.tag = '' ";
    // 开始构建查询商品总数的sql语句。
    for (String tag : tagList) {
      hql += ("or pt.tag like '%" + tag + "%' ");
    }
    hql += ")";
    int productCount = productDao.findCount(hql);
    return productCount;
  }

}
