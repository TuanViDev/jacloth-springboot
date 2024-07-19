package com.vanlang.webbanquanao.Service;

import com.vanlang.webbanquanao.Repository.CategoryRepository;
import com.vanlang.webbanquanao.Repository.InvoiceRepository;
import com.vanlang.webbanquanao.Repository.ProductRepository;
import com.vanlang.webbanquanao.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;


    public int productCount()
    {
        return (int) productRepository.count();
    }

    public int categoryCount()
    {
        return (int) categoryRepository.count();
    }

    public int userCount()
    {
        return (int) userRepository.count();
    }



    public int countSoldOfAllProduct()
    {
        return productRepository.getSoldOfAllProducts();
    }

    public int invoiceCount()
    {
        return (int) invoiceRepository.count();
    }

    public int countTotalAmountOfAllInvoices()
    {
        return invoiceRepository.getAmountOfAllInvoices();
    }

    public int countUsersByRole(String role)
    {
        return userRepository.countUserByRole(role);
    }



}
