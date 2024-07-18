package com.vanlang.webbanquanao.Service;

import com.vanlang.webbanquanao.Model.Category;
import com.vanlang.webbanquanao.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository repository;

    // CREATE
    public Category saveCategory(Category category)
    {
        return repository.save(category);
    }

    // READ
    public List<Category> getAllCategories()
    {
        return repository.findAll();
    }

    public Category getCategoryById(long id)
    {
        return repository.findById(Long.valueOf(id)).orElse(null);
    }

    // UPDATE
    public Category updateCategory(Category category)
    {
        Category catgoryEntity = repository.findById(category.getId()).orElse(null);
        catgoryEntity.setName(category.getName());
        return repository.save(catgoryEntity);
    }

    // DELETE
    public void deleteCategory(long id)
    {
        repository.deleteById(Long.valueOf(id));
    }




}
