package com.vanlang.webbanquanao.Controller.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.payos.PayOS;
import com.lib.payos.type.ItemData;
import com.lib.payos.type.PaymentData;
import com.vanlang.webbanquanao.Model.CartItem;
import com.vanlang.webbanquanao.Model.Checkout;
import com.vanlang.webbanquanao.Model.Invoice;
import com.vanlang.webbanquanao.Model.User;
import com.vanlang.webbanquanao.Service.CartItemService;
import com.vanlang.webbanquanao.Service.CheckoutItemService;
import com.vanlang.webbanquanao.Service.InvoiceService;
import com.vanlang.webbanquanao.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserCheckoutController
{
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CheckoutItemService checkoutItemService;

    @Autowired
    private UserService userService;
    @Autowired
    private InvoiceService invoiceService;



    @GetMapping("/checkout")
    public String showCheckout(Model model)
    {
        model.addAttribute("checkoutItems", cartItemService.GetCartItems());
        model.addAttribute("total", cartItemService.GetTotalAmount());
        return "/user/checkout";
    }

    @PostMapping("/checkout")
    public String CheckOut(@RequestParam("fullName") String fullName, @RequestParam("phone") String phone, @RequestParam("address") String address, @RequestParam("paymentMethod") String paymentMethod, Principal principal, Model model)
    {
        List<CartItem> cartItems = cartItemService.GetCartItems();
        User checkoutUser = userService.findUserByUsername(principal.getName());

        Invoice invoice = invoiceService.createInvoice(fullName,phone,address,paymentMethod,checkoutUser, cartItems);

        if (paymentMethod.equals("payos"))
        {
            model.addAttribute("invoice", invoice);
            return "/user/merchant/payos";
        }
        else
        {
            return "redirect:/checkout";
        }

    }

    @GetMapping("/recheckout")
    public String ReCheckout(@RequestParam("id") long invoiceId, Principal principal, Model model)
    {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        if (invoice.getUser().getUsername().equals(principal.getName())) {
            model.addAttribute("invoice", invoice);
            return "redirect:"+invoice.getCheckoutUrl();
        }
        else
        {
            return "redirect:/invoice";
        }
    }

    @GetMapping("/checkout/payos")
    public String showPayOS(Model model)
    {
        model.addAttribute("checkoutItems", cartItemService.GetCartItems());
        model.addAttribute("total", cartItemService.GetTotalAmount());
        return "/user/checkout";
    }

    /////////////////////   API   ////////////////////////




}
