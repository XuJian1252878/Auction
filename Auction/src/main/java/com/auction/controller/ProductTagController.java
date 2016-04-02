package com.auction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auction.model.ProductTag;
import com.auction.model.validator.ProductTagValidator;
import com.auction.service.IProductTagService;

@Controller
@RequestMapping("/producttag")
public class ProductTagController {

  @Resource(name = "productTagService")
  IProductTagService productTagService;

  @InitBinder("producttag")
  public void initProductTagBinder(DataBinder binder) {
    binder.addValidators(new ProductTagValidator());
  }

  @RequestMapping(value = "/prefetch")
  @ResponseBody
  public Map<String, Object> prefetchTagInfos() {
    Map<String, Object> ptMap = new HashMap<String, Object>();
    // 先取出15个标签来进行标签的自动补全测试。
    List<ProductTag> ptList = productTagService.getTagsByPart(-1, -1);
    ptMap.put("result", ptList);
    return ptMap;
  }
}
