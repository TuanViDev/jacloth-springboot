package com.vanlang.webbanquanao.Controller.Guest;

import com.vanlang.webbanquanao.Model.Category;
import com.vanlang.webbanquanao.Model.Product;
import com.vanlang.webbanquanao.Service.CategoryService;
import com.vanlang.webbanquanao.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String showHomepage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        Map<String, List<Product>> productsByCategory = new HashMap<>();
        for (Category category : categories) {
            productsByCategory.put(category.getName(), productService.get5LastProductByCategoryId(category.getId()));
        }
        model.addAttribute("productsByCategory", productsByCategory);

        return "/guest/home";
    }

    @GetMapping("/about-us")
    public String showAboutUs(Model model)
    {
        return  "/guest/about-us";
    }



    ///////////// API ///////////////
    @GetMapping("/api/guest/GetProductByCategoryId/{id}")
    @ResponseBody
    public ResponseEntity<List<Product>> GetProductByCategoryId(@PathVariable long id)
    {
        return ResponseEntity.ok().body(productService.getProductsByCategoryId(id));

    }
}
