package com.auction.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name="PRODUCT")
@Proxy(lazy=true)
public class Product {

  public Product() {
    
  }
  
  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy="increment")
  @Column(name="id", length=32)
  private Integer id;
  
  /* 
   * 多对一关联关系 
   * 延迟加载：fetch = FetchType.LAZY 
   * 引用外键：category_id 
   */ 
  @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
  @JoinColumn(name="category_id")//指明生成的外键的名字，随意命名。
  private Category category;

  @Column(name="name", length=100)
  private String name;
  
  @Column(name="pdescribe", length=250)
  private String describe;
  
  @Column(name="onSaleDate")
  @Temporal(TemporalType.TIMESTAMP) 
  private Date onSaleDate;
  
  @Column(name="endDate")
  @Temporal(TemporalType.TIMESTAMP) 
  private Date endDate;
  
  @Column(name="basicPrice")
  private float basicPrice;
  
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="product")
  private Set<Comment> comments = new HashSet<Comment>();
  
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="product")
  private Set<Bid> bids = new HashSet<Bid>();
}
