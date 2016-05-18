package com.auction.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "BID")
@Proxy(lazy = true)
public class Bid implements Comparable<Bid> {

  public Bid() {

  }

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "increment")
  @Column(name = "id", length = 32)
  private Integer id;

  // 关于各个cascade关系的使用，http://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  @JsonBackReference
  private Product product;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @Column(name = "price", length = 20)
  private float price;

  @Column(name = "bidDate")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date bidDate;

  @Column(name = "isSuccess")
  private boolean isSuccess; // 记录成交与否的字段。

  @Column(name = "dealDate", nullable = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dealDate; // 记录交易成功的时间，如果交易没有成功，那么这个字段为NULL。

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "bid")
  @JsonBackReference
  private BidNotification bidNotification;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public Date getBidDate() {
    return bidDate;
  }

  public void setBidDate(Date bidDate) {
    this.bidDate = bidDate;
  }

  // 注意函数名称不能是isSuccess，否则框架取不出这个变量的值，变量的存取名称必须是getXXX, setXXX。（XXX是变量名称，驼峰命名法。）
  public boolean getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(boolean isSuccess) {
    this.isSuccess = isSuccess;
  }

  public Date getDealDate() {
    return dealDate;
  }

  public void setDealDate(Date dealDate) {
    this.dealDate = dealDate;
  }

  public BidNotification getBidNotification() {
    return bidNotification;
  }

  public void setBidNotification(BidNotification bidNotification) {
    this.bidNotification = bidNotification;
  }

  public int compareTo(Bid o) {
    // TODO Auto-generated method stub
    // 实现降序排列。
    if (this.price > o.price) {
      return -1;
    } else if (this.price < o.price) {
      return 1;
    } else {
      return 0;
    }
  }

}
