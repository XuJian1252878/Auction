package com.auction.dao.impl;

import org.springframework.stereotype.Repository;

import com.auction.dao.ICommentDao;
import com.auction.dao.common.BaseDaoImpl;
import com.auction.model.Comment;

@Repository("commentDao")
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements ICommentDao {

}
