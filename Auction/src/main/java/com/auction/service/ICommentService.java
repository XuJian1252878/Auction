package com.auction.service;

import java.io.Serializable;
import java.util.List;

import com.auction.model.Comment;

public interface ICommentService {

  /**
   * 将用户对商品的评论存储至数据库。
   * @param comment  用户对商品的评论实体。
   * @return  若评论信息存储成功，那么返回true；否则返回false。
   */
  public Serializable pubComment(Comment comment);

  /**
   * 获得某一个商品下的所有评论信息，评论信息按照评论发表的时间降序排列。
   * @param productId  被评论的商品的id信息。
   * @return  返回一个包含评论实体的list列表，若该商品下没有评论信息，那么将返回一个空的list。
   */
  public List<Comment> getProductComments(int productId);
}
