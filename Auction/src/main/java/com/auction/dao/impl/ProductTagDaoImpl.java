package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.IProductTagDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.ProductTag;

@Repository("productTagDao")
public class ProductTagDaoImpl extends BaseDaoImpl<ProductTag> implements IProductTagDao {

}
