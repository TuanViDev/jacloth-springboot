package com.vanlang.webbanquanao.Service;

import com.vanlang.webbanquanao.Model.*;
import com.vanlang.webbanquanao.Repository.CheckoutItemRepository;
import com.vanlang.webbanquanao.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceService
{
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CheckoutItemRepository checkoutItemRepository;
    @Autowired
    private ProductService productService;


    public Invoice saveInvoice(Invoice invoice)
    {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoicesByUserId(long id)
    {
        return invoiceRepository.findInvoicesByUserId(id);
    }

    public Invoice getInvoiceById(long id)
    {
        return invoiceRepository.getById(id);
    }

    public List<Invoice> getAllInvoices()
    {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice (String fullName, String phone, String address, String paymentMethod, User checkoutUser, List<CartItem> cartItems)
    {
        Invoice invoice = new Invoice();
        invoice.setFullName(fullName);
        invoice.setPhone(phone);
        invoice.setAddress(address);
        invoice.setPaymentMethod(paymentMethod);
        invoice.setUser(checkoutUser);
        invoice.setCreatedAt(new Date());


        invoice = invoiceRepository.save(invoice);

        int amount = 0;

        for (CartItem cartItem : cartItems)
        {
            Checkout checkout = new Checkout();
            checkout.setInvoice_id(invoice);
            checkout.setProduct_id(cartItem.getProduct());
            checkout.setQuantity(cartItem.getQuantity());
            checkout.setUser_id(checkoutUser);
            checkout.setCurrent_price(cartItem.getProduct().getPrice());
            amount += cartItem.getProduct().getPrice()*cartItem.getQuantity();
            checkoutItemRepository.save(checkout);

            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity()-cartItem.getQuantity());
            product.setSold(product.getSold()+cartItem.getQuantity());
            productService.saveProduct(product);

        }

        invoice.setTotalAmount(amount);
        invoice = invoiceRepository.save(invoice);

        if (invoice.getPaymentMethod().equals("cash"))
        {
            invoice.setStatus("Xác nhận đặt hàng");
        }
        else
        {
            invoice.setStatus("Chưa thanh toán");
        }

        invoice = invoiceRepository.save(invoice);


        cartItems.clear();

        return invoice;
    }





}
