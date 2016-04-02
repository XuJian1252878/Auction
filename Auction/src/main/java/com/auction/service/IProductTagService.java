package com.auction.service;

import java.io.Serializable;
import java.util.List;

import com.auction.model.Product;
import com.auction.model.ProductTag;

public interface IProductTagService {

  /**
   * 将一串以某一字符做分隔符的tag字符串分割开来，返回一个tag的list列表。最好不要以需要转义的字符来作为
   * 分隔符，该函数中没有对需要转义的分隔符做处理。
   * @param tags  一个以分隔符分割的tag字符串
   * @param delimiter 指明tags这个字符串是以什么字符作为分割的。 
   * @return  返回一个包含tag的list列表，如果分割tags失败，那么将返回一个list列表，list列表中
   * 仅有tags这一个元素。
   */
  public List<String> splitTags(String tags, String delimiter);

  /**
   * 向数据库中存储一个新的tag，当对应的商品下存在该tag时，save操作自动忽略。
   * @param tag  需要存储如数据库的tag内容（字符串）。
   * @param product  tag是属于哪一个商品下的。新建商品的实体信息。
   * @return 当tag信息存储成功的时候返回true，存储失败的时候返回false。
   */
  public Serializable saveTag(String tag, Product product);

  /**
   * 存储新建的producttag对象。当对应的商品下存在该tag时，save操作自动忽略。
   * @param tags  以分隔符分割的tag字符串。
   * @param productId  这些tag所属的商品的id信息。
   * @return  如储存操作都成功那么返回true，一旦有某一个数据库操作失败，就返回false。
   */
  public boolean saveTags(String tags, int productId);

  /**
   * 获得某一个商品的所有标签信息。
   * @param productId
   * @return  返回一个包含ProductTag对象的list列表，如果没有tag对象，那么返回一个空的list。
   */
  public List<ProductTag> getTagsByProduct(int productId);

  /**
   * 取出部分的商品标签信息（在所有的标签记录的范围内）。
   * @param pageNo  要取出的是第几部分的标签。
   * @param pageSize  每一部分中标签的个数。
   * @return  返回一个包含ProductTag实体的list列表，当pageNo和pageSize都为-1的时候
   * 函数返回当前的所有ProductTag记录。如果所选范围的标签不存在，那么返回一个空的list。
   */
  public List<ProductTag> getTagsByPart(int pageNo, int pageSize);
}
