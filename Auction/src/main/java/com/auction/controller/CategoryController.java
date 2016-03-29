package com.auction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Category;
import com.auction.model.Product;
import com.auction.model.validator.CategoryValidator;
import com.auction.service.ICategoryService;
import com.auction.util.ConstantUtil;
import com.auction.util.ImageUtil;

@Controller
public class CategoryController {

  @Resource(name = "categoryService")
  private ICategoryService categoryService;

  // 每页显示两个类别信息。
  private final int CATEGORY_COUNT_PER_PAGE = 2;

  @InitBinder("category")
  public void initCategoryBinder(DataBinder binder) {
    // 有个疑问，我需要多个validator的时候怎么办？
    // 解决方案：
    // http://stackoverflow.com/questions/14533488/addiing-multiple-validators-using-initbinder
    binder.setValidator(new CategoryValidator());
  }

  /**
   * 分页显示 category数据
   * 
   * @param pageNo
   * @return
   */
  @RequestMapping(value = "/admin/category/list/{pageNo:\\d+}", method = RequestMethod.GET)
  public ModelAndView list(@PathVariable int pageNo) {
    ModelAndView mv = new ModelAndView();
    // 首先获得category的记录总数
    int categoryCount = categoryService.count();
    if ((categoryCount / CATEGORY_COUNT_PER_PAGE) + 1 < pageNo) {
      // 传入的pageNo参数不正确，那么显示最后一页的数据
      pageNo = (categoryCount / CATEGORY_COUNT_PER_PAGE) + 1;
    }
    List<Category> categories = categoryService.loadCategory(pageNo, CATEGORY_COUNT_PER_PAGE);
    mv.addObject("categories", categories);
    // 记录当前的页码信息
    mv.addObject("pageNo", pageNo);
    mv.setViewName("admin/category/list");
    return mv;
  }

  @RequestMapping(value = "/admin/category/add", method = RequestMethod.GET)
  public String add(@RequestParam("pageNo") int pageNo, Model model) {
    model.addAttribute("category", new Category());
    model.addAttribute("pageNo", pageNo);
    // 首先要获得当前所有的类别列表
    List<Category> parentCategoryList = categoryService.getParentCategories();
    // 添加一级类别信息
    model.addAttribute("parentCategoryList", parentCategoryList);
    System.out.println("get_add" + pageNo);
    return "admin/category/add";
  }

  @RequestMapping(value = "/admin/category/add", method = RequestMethod.POST)
  public String add(@Valid @ModelAttribute("category") Category category, BindingResult result,
      @RequestParam("pageNo") int pageNo, Model model, HttpServletRequest request) {
    // 首先如果用户添加的是一级标签，那么将这个一级标签的parentCategory设置为null。
    if (category.getParentCategory().getId() == -1) {
      System.out.println("添加类别为一级标签");
      category.setParentCategory(null);
    }
    // System.out.println(model.toString());
    if (result.hasErrors()) {
      model.addAttribute("pageNo", pageNo);
      // System.out.print("post_add_error" + pageNo);
      return "admin/category/add";
    }
    String imgFilePath = ImageUtil.genImgFileName(request, "category", category.getImgFile().getOriginalFilename());
    category.setImgPath(imgFilePath);
    if (categoryService.newCategory(category)) {
      ImageUtil.saveImgFile(request, category.getImgFile(), result, imgFilePath);
      if (result.hasErrors()) {
        model.addAttribute("pageNo", pageNo);
        return "admin/category/add";
      }
      // 而redirect后的是url，是会经过controller处理的。
      return "redirect:/admin/category/list/" + pageNo;
    } else {
      // 防止出现错误，跳回原界面（由于return的是"admin/category/add"，所以参数失效），再次提交，页码信息丢失的情况。
      model.addAttribute("pageNo", pageNo);
      result.rejectValue("name", "category.name.already.exist");
      // 仅仅指明跳转到哪一个页面的jsp路径，而不经过controller的处理。所以原来的信息得以保存。
      return "admin/category/add";
    }
  }

  // 删除商品类别
  @RequestMapping(value = "/admin/category/delete_{categoryId}", method = RequestMethod.GET)
  public String delete(@PathVariable int categoryId, @RequestParam int pageNo, Model model) {
    // 先省略判断categoryId是否合法的逻辑。
    categoryService.deleteCategory(categoryId);
    return "redirect:/admin/category/list/" + pageNo;
  }

  // 更新商品类别信息 进入页面
  // viewOrEdit 是0那么表示为查看，1表示为编辑
  @RequestMapping(value = { "/admin/category/edit/{viewOrEdit}_{categoryId}",
      "/view/{viewOrEdit}_{categoryId}" }, method = RequestMethod.GET)
  public String edit(@PathVariable("viewOrEdit") int viewOrEdit, @PathVariable("categoryId") int categoryId,
      @RequestParam int pageNo, Model model) {
    Category category = categoryService.getCategory(categoryId);
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("viewOrEdit", viewOrEdit);
    if (category == null) {
      return "admin/category/edit";
    }
    // 设置页面相关的内容信息
    model.addAttribute("category", category);
    return "admin/category/edit";
  }

  // 更新商品类别信息 条件商品类别信息
  @RequestMapping(value = "/admin/category/edit", method = RequestMethod.POST)
  public String edit(@Valid @ModelAttribute("category") Category category, @RequestParam int pageNo,
      BindingResult result) {
    categoryService.updateCategory(category);
    return "redirect:/admin/category/list/" + pageNo;
  }

  @RequestMapping(value = "/category/list/{categoryId}_{pageNo}", method = RequestMethod.GET)
  public ModelAndView listProducts(@PathVariable("categoryId") int categoryId, @PathVariable("pageNo") int pageNo) {
    ModelAndView mv = new ModelAndView();
    Category category = categoryService.getCategory(categoryId);
    int productCount = categoryService.getProductCount(categoryId, false);
    int pageCount = (int)Math.ceil(productCount / (double)ConstantUtil.PRODUCT_COUNT_PER_PAGE);
    mv.addObject("category", category);  // 当前的商品类别信息。
    mv.addObject("pageNo", pageNo);  // 当前显示的页码。
    mv.addObject("productCount", productCount); // 竞价商品的总数。
    mv.addObject("pageCount", pageCount);  // 商品总共占的页数。
    mv.addObject("maxWaterfallParts", ConstantUtil.PRODUCT_WATERFALL_PARTS_PER_PAGE);  // 每个页面中，瀑布流最多加载的次数。
    mv.setViewName("product/list");
    return mv;
  }

  /**
   * 返回前端瀑布流所需要的json数据。
   * 
   * @param categoryId
   * @param pageNo
   * @param waterfallIndex
   * @return
   */
  // /category/list/{categoryId:\\d+}_{pageNo:\\d+}_{waterfallIndex:\\d+} //
  // {pageNo:\\d+}_{waterfallIndex:\\d+}识别不出，会混在一起，如pageNo=1_1，为什么？
  @RequestMapping(value = "/category/list/{categoryId:\\d+}_{pageNo:\\d+}/{waterfallIndex:\\d+}")
  @ResponseBody // 返回的是json数据。
  public Map<String, Object> getProductsByWaterFallPart(@PathVariable("categoryId") int categoryId,
      @PathVariable("pageNo") int pageNo, @PathVariable("waterfallIndex") int waterfallIndex) {
    // 获得当前应该改加载哪一部分瀑布流的数据。
    int waterfallCurPart = (pageNo - 1) * ConstantUtil.PRODUCT_WATERFALL_PARTS_PER_PAGE + waterfallIndex;
    // 加载该类别下的商品，不包含已经完成竞价的商品。
    List<Product> products = categoryService.loadProducts(categoryId, waterfallCurPart,
        ConstantUtil.PRODUCT_COUNT_PER_WATERFALL_PART, false);
    Map<String, Object> resMap = new HashMap<String, Object>();
    resMap.put("total", products.size());
    resMap.put("result", products);
    return resMap;
  }
}
