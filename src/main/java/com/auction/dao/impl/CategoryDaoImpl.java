package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.ICategoryDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.Category;

@Repository("categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements ICategoryDao {

}
