package com.vanlang.webbanquanao.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem
{
    private static long nextId = 1;

    private long id;
    private Product product;
    private int quantity;



    public CartItem(Product product, int quantity)
    {
        this.id = nextId++;
        this.product = product;
        this.quantity = quantity;
    }
}
