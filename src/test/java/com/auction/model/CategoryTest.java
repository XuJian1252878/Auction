package com.auction.model;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/configs/spring-servlet.xml",
    "classpath:/configs/spring-hibernate.xml",
    "classpath:/configs/spring-beans.xml"
})
public class CategoryTest {

  @Resource(name="sessionFactory")
  private SessionFactory sessionFactory;
  
  @BeforeClass  
  public static void beforeClass() {
  }  
  
  @AfterClass  
  public static void afterClass() {
  }
}
