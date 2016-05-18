package com.auction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "BIDNOTIFICATION")
@Proxy(lazy = true)
@DynamicInsert(value = true)
public class BidNotification {

  public BidNotification() {

  }

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "increment")
  @Column(name = "id", length = 32)
  private Integer id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bid_id")
  @JsonManagedReference
  private Bid bid;

  @Column(name = "isRead", nullable = false)
  private boolean isRead = false;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Bid getBid() {
    return bid;
  }

  public void setBid(Bid bid) {
    this.bid = bid;
  }

  public boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(boolean isRead) {
    this.isRead = isRead;
  }

}
