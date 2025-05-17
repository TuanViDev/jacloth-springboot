package com.vanlang.webbanquanao.Controller.Guest;

import com.vanlang.webbanquanao.Model.Product;
import com.vanlang.webbanquanao.Service.CategoryService;
import com.vanlang.webbanquanao.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductsViewController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String showAllProducts(Model model)
    {
        model.addAttribute("products", productService.getAllProductsDesc());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/guest/products";
    }

    @GetMapping("/products/best-sellers")
    public String showBestSellers(Model model)
    {
        model.addAttribute("products", productService.getAllBestSellers());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/guest/productsBestSellers";
    }

    @GetMapping("/products/{name}")
    public String showAllProductsByCategoryName(@PathVariable("name") String name, Model model)
    {
        model.addAttribute("categoryName",name);
        model.addAttribute("products", productService.getProductsByCategoryName(name));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/guest/productsByCategoryName";
    }

    @GetMapping("/products/search")
    public String showAllProductsBySearchQuery(@RequestParam("query") String query, Model model)
    {
        model.addAttribute("query",query);
        model.addAttribute("products", productService.getProductsByQuery(query));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/guest/productsBySearchQuery";
    }



}
