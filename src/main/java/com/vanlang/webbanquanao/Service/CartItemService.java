package com.vanlang.webbanquanao.Service;

import com.vanlang.webbanquanao.Model.CartItem;
import com.vanlang.webbanquanao.Model.Product;
import com.vanlang.webbanquanao.Repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
@SessionScope
public class CartItemService
{
    @Autowired
    private ProductRepository productRepository;

    private List<CartItem> cartItems = new ArrayList<CartItem>();

    public List<CartItem> GetCartItems()
    {
        return cartItems;
    }

    public int GetTotalAmount()
    {
        int total = 0;
        for (CartItem cartItem : cartItems)
        {
            total += cartItem.getProduct().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    public void AddCartItem(Long productId, int quantity)
    {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = new CartItem(product, quantity);
        cartItems.add(cartItem);
        System.out.println(cartItem.getId());
    }

    public void DeleteCartItem(Long id)
    {
        cartItems.removeIf(cartItem -> cartItem.getId() == id);
    }

    public void ClearCartItem()
    {
        cartItems.clear();
    }

}
