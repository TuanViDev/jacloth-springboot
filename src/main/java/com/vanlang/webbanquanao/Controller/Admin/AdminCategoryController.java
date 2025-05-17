package com.vanlang.webbanquanao.Controller.Admin;

import com.vanlang.webbanquanao.Model.Category;
import com.vanlang.webbanquanao.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminCategoryController
{
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public String AdminCategories(Model model)
    {
        return "redirect:/admin/category/list";
    }

    @GetMapping("/admin/category/list")
    public String AdminCategoriesList(Model model)
    {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/category/list";
    }

    @GetMapping("/admin/category/add")
    public String AddCategories()
    {
        return "/admin/category/add";
    }

    @PostMapping("/admin/category/add")
    public String AddCategories(@RequestParam("name") String name)
    {
        ResponseEntity<String> response = AddCategoryAPI(name);
        if (response.getStatusCode() == HttpStatus.OK)
        {
            return "redirect:/admin/category/add?success";
        }
        else{
            return "redirect:/admin/category/add?error";
        }
    }

    @GetMapping("/admin/category/update/{id}")
    public String UpdateCategory(Model model,@PathVariable long id)
    {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "/admin/category/update";
    }

    @PostMapping("/admin/category/update")
    public String UpdateCategory(@RequestParam("id") long id,@RequestParam("name") String name)
    {
        ResponseEntity<String> response = UpdateCategoryAPI(id,name);
        if (response.getStatusCode() == HttpStatus.OK)
        {
            return "redirect:/admin/category/update/"+id+"?success";
        }
        else{
            return "redirect:/admin/category/update/"+id+"?error";
        }
    }

    @PostMapping("/admin/category/delete/{id}")
    public String DeleteCategory(@PathVariable long id)
    {
        try {
            ResponseEntity<String> response = DeleteCategoryAPI(id);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/admin/category/list?deletedSuccess";
            } else {
                return "redirect:/admin/category/list?deletedUnsuccess";
            }
        }
        catch (Exception e)
        {
            return "redirect:/admin/category/list?unableToDelete";
        }
    }




    ////////////////////     API            ///////////////////

    @PostMapping("/api/admin/AddCategory")
    public ResponseEntity<String> AddCategoryAPI (String name)
    {
        Category category = new Category();
        category.setName(name);
        categoryService.saveCategory(category);

        if (category.getId() >= 0)
        {
            return ResponseEntity.ok().body("Category added successfully");
        }
        else
        {
            return ResponseEntity.badRequest().body("Category added unsuccessfully");
        }


    }

    @PutMapping("/api/admin/UpdateCategory")
    public ResponseEntity<String> UpdateCategoryAPI (long id,String name)
    {
        Category category = categoryService.getCategoryById(id);
        category.setName(name);
        categoryService.updateCategory(category);

        if (category.getName().equals(name) )
        {
            return ResponseEntity.ok().body("Category updated successfully");
        }
        else
        {
            return ResponseEntity.badRequest().body("Category updated unsuccessfully");
        }
    }

    @DeleteMapping("/api/admin/DeleteCategory")
    public ResponseEntity<String> DeleteCategoryAPI (long id)
    {
        categoryService.deleteCategory(id);

        if (categoryService.getCategoryById(id) == null)
        {
            return ResponseEntity.ok().body("Category deleted successfully");
        }
        else
        {
            return ResponseEntity.badRequest().body("Category deleted unsuccessfully");
        }
    }



}
