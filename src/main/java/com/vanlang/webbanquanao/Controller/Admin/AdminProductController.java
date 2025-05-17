package com.vanlang.webbanquanao.Controller.Admin;

import com.vanlang.webbanquanao.Model.Category;
import com.vanlang.webbanquanao.Model.Product;
import com.vanlang.webbanquanao.Service.CategoryService;
import com.vanlang.webbanquanao.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/product")
    public String AdminProducts()
    {
        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/list")
    public String AdminProductsList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/add")
    public String AddProduct(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/product/add";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@RequestParam("image") MultipartFile image, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") int price, @RequestParam("quantity") int quantity, @RequestParam("categoryId") Category categoryId) {
        ResponseEntity<String> response = AddProductAPI(image, name, description, price, quantity, categoryId);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/product/add?success";
        } else {
            return "redirect:/admin/product/add?error";
        }
    }

    @GetMapping("/admin/product/update/{id}")
    public String UpdateProduct(Model model, @PathVariable long id) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String UpdateProduct(@RequestParam("id") long id, @RequestParam("image") MultipartFile image, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") int price, @RequestParam("quantity") int quantity, @RequestParam("categoryId") Category categoryId) {
        ResponseEntity<String> response = UpdateProductAPI(id, image, name, description, price, quantity, categoryId);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/product/update/" + id + "?success";
        } else {
            return "redirect:/admin/product/update/" + id + "?error";
        }
    }

    @PostMapping("/admin/product/setquantity")
    public String SetQuantity(@RequestParam("id") long id, @RequestParam("quantity") int quantity){
        ResponseEntity<String> response = SetQuantityAPI(id, quantity);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/product/list?setQuantitySuccessful";
        } else {
            return "redirect:/admin/product/list?setQuantityUnsuccessful";
        }
    }

    @PostMapping("/admin/product/addquantity")
    public String AddQuantity(@RequestParam("id") long id, @RequestParam("quantity") int quantity){
        ResponseEntity<String> response = AddQuantityAPI(id, quantity);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/product/list?setQuantitySuccessful";
        } else {
            return "redirect:/admin/product/list?setQuantityUnsuccessful";
        }
    }


    @PostMapping("/admin/product/delete/{id}")
    public String DeleteProduct(@PathVariable long id)
    {
            ResponseEntity<String> response = DeleteProductAPI(id);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/admin/product/list?deletedSuccess";
            } else {
                return "redirect:/admin/product/list?deletedUnsuccess";
            }

    }

    ////////////////////     API            ///////////////////

    @PostMapping("api/admin/AddProduct")
    public ResponseEntity<String> AddProductAPI(MultipartFile image, String name, String description, int price, int quantity, Category categorryId) {
        try {
            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setDescription(description);
            newProduct.setPrice(price);
            newProduct.setQuantity(quantity);
            newProduct.setCategory_id(categorryId);
            newProduct.setSold(0);

            byte[] bytes = image.getBytes();
            String base64String = Base64.getEncoder().encodeToString(bytes);
            newProduct.setImage(base64String);

            productService.saveProduct(newProduct);


            if (newProduct.getId() >= 0) {
                return ResponseEntity.ok().body("Product added successfully");
            } else {
                return ResponseEntity.badRequest().body("Product added unsuccessfully");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Image added unsuccessfully");
        }

    }

    @PutMapping("/api/admin/UpdateProduct")
    public ResponseEntity<String> UpdateProductAPI(long id, MultipartFile image, String name, String description, int price, int quantity, Category categorryId) {
        try {
            Product product = productService.getProductById(id);

//            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setCategory_id(categorryId);


            byte[] bytes = image.getBytes();
            String base64String = Base64.getEncoder().encodeToString(bytes);
            if (!base64String.isEmpty()) {
                product.setImage(base64String);
            }

            productService.saveProduct(product);

            if (product.getName().equals(name)) {
                return ResponseEntity.ok().body("Product updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Product updated unsuccessfully");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Product update unsuccessfully");
        }
    }

    @PutMapping("/api/admin/AddQuantity")
    public ResponseEntity<String> AddQuantityAPI(long id, int quantity)
    {
        Product product = productService.getProductById(id);
        product.setQuantity(product.getQuantity() + quantity);
        productService.saveProduct(product);
        return ResponseEntity.ok().body("Added "+quantity+" item successfully");
    }

    @PutMapping("/api/admin/SetQuantity")
    public ResponseEntity<String> SetQuantityAPI(long id, int quantity)
    {
        Product product = productService.getProductById(id);
        product.setQuantity(quantity);
        productService.saveProduct(product);

        if (product.getQuantity()==quantity) {
            return ResponseEntity.ok().body("Set " + quantity + " item successfully");
        }
        else
        {
            return ResponseEntity.badRequest().body("Set quantity unsuccessfully");
        }
    }

    @DeleteMapping("/api/admin/DeleteProduct")
    public ResponseEntity<String> DeleteProductAPI(long id) {
        productService.deleteProduct(id);

        if (productService.getProductById(id) == null) {
            return ResponseEntity.ok().body("Product deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Product deleted unsuccessfully");
        }
    }

}
