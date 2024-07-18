package com.vanlang.webbanquanao.Controller.User;

import com.vanlang.webbanquanao.Model.Product;
import com.vanlang.webbanquanao.Service.CategoryService;
import com.vanlang.webbanquanao.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserProductController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/product/{id}")
    public String ShowProduct(@PathVariable long id, Model model)
    {
        model.addAttribute("product", productService.getProductById(id));
        return "/guest/product";
    }


    /////////////////////// API /////////////////////////////
    @GetMapping("/api/user/getAllProducts")
    public List<Product> getAllProductsAPI()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/api/user/getProduct/{id}")
    public Product getProductAPI(@PathVariable long id)
    {
        return productService.getProductById(id);
    }


}
