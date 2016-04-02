package com.auction.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.springframework.stereotype.Repository;

import com.auction.dao.IProductTagDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.ProductTag;

@Repository("productTagDao")
public class ProductTagDaoImpl extends BaseDaoImpl<ProductTag> implements IProductTagDao {

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
}
