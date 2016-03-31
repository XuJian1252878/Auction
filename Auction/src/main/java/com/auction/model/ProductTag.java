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

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "product_producttag", joinColumns = {
      @JoinColumn(name = "product_id", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "producttag_id", referencedColumnName = "id") })
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
