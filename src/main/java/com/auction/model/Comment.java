package com.auction.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

public class Comment {

  public Comment() {
    
  }
  
  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy="increment")
  @Column(name="id", length=32)
  private Integer id;
  
  @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
  @JoinColumn(name="user_id")
  private User user;
  
  @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
  @JoinColumn(name="product_id")
  private Product product;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
  
}
