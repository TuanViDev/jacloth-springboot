package com.vanlang.webbanquanao.Service;

import com.vanlang.webbanquanao.Model.Product;
import com.vanlang.webbanquanao.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository repository;

    // CREATE
    public Product saveProduct(Product product)
    {
        return repository.save(product);
    }

    // READ

    public List<Product> getProductsByCategoryId(Long id)
    {
        return repository.findByCategoryId(id);
    }

    public List<Product> getProductsByCategoryName(String name)
    {
        return repository.findByCategoryName(name);
    }

    public List<Product> getProductsByQuery(String query)
    {
        return repository.findByQuery(query);
    }

    public List<Product> get5LastProductByCategoryId(Long id)
    {
        return repository.findLast5ProductByCategoryId(id);
    }
    public List<Product> getAllProducts()
    {
        return repository.findAll();
    }

    public List<Product> getAllBestSellers()
    {
        return repository.findAllBestSellers();
    }

    public List<Product> getAllProductsDesc()
    {
        return repository.findAllProductDesc();
    }

    public Product getProductById(long id)
    {
        return repository.findById(Long.valueOf(id)).orElse(null);
    }


    // UPDATE
    public Product updateProduct(Product product)
    {
        Product productEntity = repository.findById(product.getId()).orElse(null);

        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setDescription(product.getDescription());
        productEntity.setCategory_id(product.getCategory_id());
        productEntity.setPrice(product.getPrice());
        productEntity.setImage(product.getImage());
        return repository.save(productEntity);
    }

    // DELETE
    public void deleteProduct(long id)
    {
        repository.deleteById(Long.valueOf(id));
    }




}
