package com.auction.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.auction.model.Product;
import com.auction.service.IProductService;
import com.auction.service.common.BaseService;

@Service("productService")
@Transactional
public class ProductService extends BaseService<Product> implements IProductService {

}
