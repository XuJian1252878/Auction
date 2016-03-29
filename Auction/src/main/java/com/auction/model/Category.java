package com.auction.model;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CATEGORY")
@Proxy(lazy = true)
public class Category {

  public Category() {

  }

  /**
   * CascadeType.REFRESH：级联刷新，当多个用户同时作操作一个实体，为了用户取到的数据是实时的，
   * 在用实体中的数据之前就可以调用一下refresh()方法！
   * CascadeType.REMOVE：级联删除，当调用remove()方法删除Order实体时会先级联删除OrderItem的相关数据！
   * CascadeType.MERGE：级联更新，当调用了Merge()方法，如果Order中的数据改变了会相应的更新OrderItem中的数据，
   * CascadeType.ALL：包含以上所有级联属性。
   */

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "increment")
  @Column(name = "id", length = 32)
  private Integer id;

  @Column(name = "name", length = 16)
  private String name;

  @Column(name = "cdesc", length = 256)
  private String cdesc;
  
  // 商品的类别信息，一级类别为1，二级类别为2，最多为2级类别。
  @Transient
  private int levelInfo;

  @Column(name = "imgPath")
  private String imgPath;

  @Transient
  private MultipartFile imgFile; 

  /*
   * 一对多关联关系 级联关系：cascade=CascadeType.ALL 延迟加载：fetch = FetchType.LAZY
   * 映射：mappedBy = "category" ,  mappped 后面跟的是    管理映射关系的    变量的名称
   * 从下面的parentCategpry就可以看出这一点。
   * parentCategory是Category类中的一个变量，mappedBy = "parentCategory" 表示由Category类中的parentCategory类负责级联关系。
   * 这里的category是Product类中的一个成员变量。指明Category类不负责级联关系，而是Product类中的category变量负责级联关系。
   */
  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "category")
  @JsonBackReference  // 解决json数据循环显示的问题。
  private Set<Product> products = new HashSet<Product>();

  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  private Category parentCategory;

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "parentCategory")
  private Set<Category> categories = new HashSet<Category>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCdesc() {
    return cdesc;
  }

  public void setCdesc(String cdesc) {
    this.cdesc = cdesc;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public MultipartFile getImgFile() {
    return imgFile;
  }

  public void setImgFile(MultipartFile imgFile) {
    this.imgFile = imgFile;
  }

  public Set<Product> getProducts() {
    return products;
  }

  public Category getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public int getLevelInfo() {
    return levelInfo;
  }

  public void setLevelInfo(int levelInfo) {
    this.levelInfo = levelInfo;
  }

}
