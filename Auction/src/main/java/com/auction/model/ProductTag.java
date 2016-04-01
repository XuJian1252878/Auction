package com.auction.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PRODUCTTAG")
@Proxy(lazy = true)
@DynamicInsert(value = true)
public class ProductTag {

  public ProductTag() {

  }

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "increment")
  @Column(name = "id", length = 32)
  private Integer id;

  @Column(name = "tag", nullable = false)
  private String tag;

  // mappedby 指明的变量名所在的一方即为关系的拥有方。
  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "product_tag", 
    joinColumns = { @JoinColumn(name = "producttag_id", referencedColumnName = "id") }, // owner side 管理这个关系（拥有这个关系）一方的id，name可以随意起名。
    inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") }) // 不拥有这个关系一方的id。name可以随意起名。
  @JsonBackReference
  private List<Product> products = new ArrayList<Product>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

}
