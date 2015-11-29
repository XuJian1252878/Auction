package com.auction.controller.category;

import java.util.List;

import javax.annotation.Resource;
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
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Category;
import com.auction.model.validator.CategoryValidator;
import com.auction.service.ICategoryService;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController {

  @Resource(name = "categoryService")
  private ICategoryService categoryService;

  // 每页显示两个类别信息。
  private final int PAGESIZE = 2;

  @InitBinder
  public void initBinder(DataBinder binder) {
    // 有个疑问，我需要多个validator的时候怎么办？
    binder.setValidator(new CategoryValidator());
  }

  /**
   * 分页显示 category数据
   * 
   * @param pageNo
   * @return
   */
  @RequestMapping(value = "/list/{pageNo}", method = RequestMethod.GET)
  public ModelAndView list(@PathVariable int pageNo) {
    ModelAndView mv = new ModelAndView();
    // 首先获得category的记录总数
    int categoryCount = categoryService.count();
    if ((categoryCount / PAGESIZE) + 1 < pageNo) {
      // 传入的pageNo参数不正确，那么显示最后一页的数据
      pageNo = (categoryCount / PAGESIZE) + 1;
    }
    List<Category> categories = categoryService.loadPart(pageNo, PAGESIZE);
    mv.addObject("categories", categories);
    // 记录当前的页码信息
    mv.addObject("pageNo", pageNo);
    mv.setViewName("admin/category/list");
    return mv;
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
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

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String add(@Valid @ModelAttribute("category") Category category, BindingResult result,
      @RequestParam("pageNo") int pageNo, Model model) {
    // 首先如果用户添加的是一级标签，那么将这个一级标签的parentCategory设置为null。
    if (category.getParentCategory().getId() == -1) {
      System.out.println("添加类别为一级标签");
      category.setParentCategory(null);
    }
    System.out.println(model.toString());
    if (result.hasErrors()) {
      model.addAttribute("pageNo", pageNo);
      System.out.print("post_add_error" + pageNo);
      return "admin/category/add";
    }
    if (categoryService.newCategory(category)) {
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
  @RequestMapping(value = "/delete_{categoryId}", method = RequestMethod.GET)
  public String delete(@PathVariable int categoryId, @RequestParam int pageNo, Model model) {
    // 先省略判断categoryId是否合法的逻辑。
    categoryService.deleteCategory(categoryId);
    return "redirect:/admin/category/list/" + pageNo;
  }
  
  // 更新商品类别信息 进入页面
  // viewOrEdit 是0那么表示为查看，1表示为编辑
  @RequestMapping(value={"/edit/{viewOrEdit}_{categoryId}", "/view/{viewOrEdit}_{categoryId}"}, method = RequestMethod.GET)
  public String edit(@PathVariable("viewOrEdit") int viewOrEdit, @PathVariable("categoryId") int categoryId, @RequestParam int pageNo, Model model) {
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
  @RequestMapping(value="/edit", method=RequestMethod.POST)
  public String edit(@Valid @ModelAttribute("category") Category category, @RequestParam int pageNo, BindingResult result) {
    categoryService.updateCategory(category);
    return "redirect:/admin/category/list/" + pageNo;
  }
}
