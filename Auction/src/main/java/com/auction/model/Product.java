package com.auction.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "PRODUCT")
@Proxy(lazy = true)
@DynamicInsert(value = true)
public class Product {

  public Product() {

  }

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "increment")
  @Column(name = "id", length = 32)
  private Integer id;

  /*
   * 多对一关联关系 延迟加载：fetch = FetchType.LAZY 引用外键：category_id
   */
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "category_id") // 指明生成的外键的名字，随意命名。
  @JsonManagedReference
  private Category category;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "pdescribe", length = 250)
  private String describe;

  @Column(name = "onSaleDate")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date onSaleDate;

  @Column(name = "endDate")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date endDate;

  @Column(name = "basicPrice")
  private float basicPrice;

  @Column(name = "imgPath")
  private String imgPath;

  @Column(name = "isDeal", nullable = false)
  private boolean isDeal = false;

  @Transient
  private MultipartFile imgFile;

  // 商品在html页面中的倒计时元素id信息。
  @Transient
  private String countdownId;

  // 商品在html页面中的倒计时提示元素的id信息。
  @Transient
  private String countdownAlertId;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
  @JsonBackReference
  private Set<Comment> comments = new HashSet<Comment>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
  @JsonManagedReference
  private Set<Bid> bids = new HashSet<Bid>();

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @JsonManagedReference
  private User user;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, mappedBy = "products")
  @JsonManagedReference
  private List<ProductTag> productTags = new ArrayList<ProductTag>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public Date getOnSaleDate() {
    return onSaleDate;
  }

  public void setOnSaleDate(Date onSaleDate) {
    this.onSaleDate = onSaleDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public float getBasicPrice() {
    return basicPrice;
  }

  public void setBasicPrice(float basicPrice) {
    this.basicPrice = basicPrice;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public boolean getIsDeal() {
    return isDeal;
  }

  public void setIsDeal(boolean isDeal) {
    this.isDeal = isDeal;
  }

  public MultipartFile getImgFile() {
    return imgFile;
  }

  public void setImgFile(MultipartFile imgFile) {
    this.imgFile = imgFile;
  }

  public String getCountdownId() {
    return "productexpireclock" + getId();
  }

  public String getCountdownAlertId() {
    return "productexpirealert" + getId();
  }

  public Set<Comment> getComments() {
    return comments;
  }

  public void setComments(Set<Comment> comments) {
    this.comments = comments;
  }

  public Set<Bid> getBids() {
    // 希望得到一个按照竞价价格降序排列的集合。
    TreeSet<Bid> bidsSet = new TreeSet<Bid>();
    for (Bid bid : bids) {
      bidsSet.add(bid);
    }
    return bidsSet;
  }

  public void setBids(Set<Bid> bids) {
    this.bids = bids;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<ProductTag> getProductTags() {
    return productTags;
  }

  public void setProductTags(List<ProductTag> productTags) {
    this.productTags = productTags;
  }

  /**
   * 获得当前商品的最大竞拍价信息。
   */
  public float getMaxBidPrice() {
    float maxBidPrice = 0;
    for (Bid bid : this.bids) {
      if (bid.getPrice() > maxBidPrice) {
        maxBidPrice = bid.getPrice();
      }
    }
    return maxBidPrice;
  }

  /**
   * 获得当前成交的商品信息。
   * @return
   */
  public float getDealBidPrice() {
    float dealPrice = -1;
    for (Bid bid : this.bids) {
      if (bid.getIsSuccess()) {
        dealPrice = bid.getPrice();
        break;
      }
    }
    return dealPrice;
  }

}
