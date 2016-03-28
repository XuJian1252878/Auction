package com.auction.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.dao.ICommentDao;
import com.auction.model.Comment;
import com.auction.service.ICommentService;
import com.auction.service.common.BaseService;

@Service("commentService")
@Transactional
public class CommentServiceImpl extends BaseService<Comment> implements ICommentService {

  @Resource(name = "commentDao")
  ICommentDao commentDao;
  
  public Serializable pubComment(Comment comment) {
    // TODO Auto-generated method stub
    return commentDao.save(comment);
  }

  public List<Comment> getProductComments(int productId) {
    // TODO Auto-generated method stub
    String hql = "from " + Comment.class.getName() + " as c where c.product.id = ? order by c.pubDate desc";
    List<Comment> comments = commentDao.find(hql, productId);
    return comments;
  }

}
