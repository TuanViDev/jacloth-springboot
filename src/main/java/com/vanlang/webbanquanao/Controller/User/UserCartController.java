package com.vanlang.webbanquanao.Controller.User;

import com.vanlang.webbanquanao.Model.CartItem;
import com.vanlang.webbanquanao.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserCartController
{
    @Autowired
    CartItemService cartItemService;

    @GetMapping("/cart")
    public String showCart(Model model)
    {
        model.addAttribute("cartItems", cartItemService.GetCartItems());
        model.addAttribute("total", cartItemService.GetTotalAmount());
        return "/user/cart";
    }

    @PostMapping("/cart")
    public String AddToCart(@RequestParam("id") long id, @RequestParam("quantity") int quantity)
    {
        cartItemService.AddCartItem(id,quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/delete")
    public String CartDelete(@RequestParam("id") long id)
    {
        cartItemService.DeleteCartItem(id);
        return "redirect:/cart";
    }

}
