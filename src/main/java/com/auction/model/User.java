package com.auction.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

public class User {
  
  protected User() {
    
  }
  
  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy="increment")
  @Column(name="id", length=32)
  private Integer id;
  
  @Column(name="userName", length=32)
  private String userName;
  
  @Column(name="age", length=32)
  private Integer age;
  
  @Column(name="addr", length=50)
  private String address;
  
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="user")
  private Set<Comment> comment = new HashSet<Comment>();
  
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="user")
  private Set<Bid> bids = new HashSet<Bid>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Set<Comment> getComment() {
    return comment;
  }

  public void setComment(Set<Comment> comment) {
    this.comment = comment;
  }

  public Set<Bid> getBids() {
    return bids;
  }

  public void setBids(Set<Bid> bids) {
    this.bids = bids;
  }
  
}
