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
    mv.addObject("categoryPageNo", pageNo);
    mv.setViewName("admin/category/list");
    return mv;
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String add(@RequestParam("pageNo") int pageNo, Model model) {
    model.addAttribute("category", new Category());
    model.addAttribute("categoryPageNo", pageNo);
    System.out.println("get_add" + pageNo);
    return "admin/category/add";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String add(@Valid @ModelAttribute("category") Category category, BindingResult result,
      @RequestParam("pageNo") int pageNo, Model model) {
    // 防止出现错误，跳回原界面（由于return的是"admin/category/add"，所以参数失效），再次提交，页码信息丢失的情况。
    model.addAttribute("categoryPageNo", pageNo);
    System.out.println(model.toString());
    if (result.hasErrors()) {
      System.out.print("post_add_error" + pageNo);
      return "admin/category/add";
    }
    if (categoryService.newCategory(category)) {
      return "redirect:/admin/category/list/" + pageNo;
    } else {
      result.rejectValue("name", "category.name.already.exist");
      return "admin/category/add";
    }
  }
}
