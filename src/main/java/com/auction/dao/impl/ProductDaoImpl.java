package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.IProductDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.Product;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<Product> implements IProductDao {
  
}
