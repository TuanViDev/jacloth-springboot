package com.vanlang.webbanquanao.Repository;

import com.vanlang.webbanquanao.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long>
{
    @Query(value = "SELECT * FROM products WHERE category_id=?",nativeQuery = true)
    public List<Product> findByCategoryId(Long categoryId);

    @Query(value = "SELECT * FROM products WHERE category_id = ? ORDER BY id DESC LIMIT 5",nativeQuery = true)
    public List<Product> findLast5ProductByCategoryId(Long categoryId);

    @Query(value = "SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id WHERE c.name = ?", nativeQuery = true)
    public List<Product> findByCategoryName(String name);

    @Query(value = "SELECT * FROM products ORDER BY sold DESC LIMIT 10",nativeQuery = true)
    public List<Product> findTop10SoldProduct();

    @Query(value = "SELECT p.* FROM products p WHERE p.name LIKE %?1%", nativeQuery = true)
    public List<Product> findByQuery(String name);

    @Query(value = "SELECT SUM(sold) from products", nativeQuery = true)
    public int getSoldOfAllProducts();

    @Query(value = "SELECT * FROM products ORDER BY id DESC",nativeQuery = true)
    public List<Product> findAllProductDesc();

    @Query(value = "SELECT * FROM products WHERE sold > 0 ORDER BY sold DESC", nativeQuery = true)
    public List<Product> findAllBestSellers();


}
