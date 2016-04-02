package com.auction.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MyJsonObjectMapper extends ObjectMapper {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public MyJsonObjectMapper() {
    super();
    this.enable(SerializationFeature.INDENT_OUTPUT);
  }

}
