package com.auction.dao;

import java.util.List;

import com.auction.dao.common.IBaseDao;
import com.auction.model.ProductTag;

public interface IProductTagDao extends IBaseDao<ProductTag> {

  /**
   * 将一串以某一字符做分隔符的tag字符串分割开来，返回一个tag的list列表。最好不要以需要转义的字符来作为
   * 分隔符，该函数中没有对需要转义的分隔符做处理。
   * @param tags  一个以分隔符分割的tag字符串
   * @param delimiter 指明tags这个字符串是以什么字符作为分割的。 
   * @return  返回一个包含tag的list列表，如果分割tags失败，那么将返回一个list列表，list列表中
   * 仅有tags这一个元素。
   */
  public List<String> splitTags(String tags, String delimiter);
}
