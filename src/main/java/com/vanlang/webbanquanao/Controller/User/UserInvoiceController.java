package com.vanlang.webbanquanao.Controller.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.payos.PayOS;
import com.lib.payos.type.ItemData;
import com.lib.payos.type.PaymentData;
import com.vanlang.webbanquanao.Model.Checkout;
import com.vanlang.webbanquanao.Model.Invoice;
import com.vanlang.webbanquanao.Model.User;
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
import java.util.List;


@Controller
public class UserInvoiceController
{
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;

    @Autowired
    private PayOS payOS;

    @GetMapping("/invoice")
    public String showInvoices(Model model, Principal principal)
    {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("invoices", invoiceService.getAllInvoicesByUserId(user.getId()));
        return "/user/invoice-list";
    }

    @GetMapping("/invoice/{id}")
    public String showInvoiceDetails( @PathVariable("id") long id,Model model, Principal principal)
    {
        User user = userService.findUserByUsername(principal.getName());
        if(user.getRole().equals("ADMIN") || user.getUsername().equals(invoiceService.getInvoiceById(id).getUser().getUsername()))
        {
        model.addAttribute("checkouts", invoiceService.getInvoiceById(id).getCheckout());
        model.addAttribute("invoice", invoiceService.getInvoiceById(id));
        return "/user/invoice";
        }
        else return "redirect:/invoice";
    }

    //////////////////// API ////////////////////////////
    @RequestMapping(method = RequestMethod.POST, value = "/create-payment-link", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void checkout(@RequestParam("invoiceId") long id, HttpServletResponse httpServletResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Invoice invoice = invoiceService.getInvoiceById(id);
//            final String productName = "Mì tôm hảo hảo ly";
//            final String description = "Thanh toan don hang";
            List<Checkout> checkoutItems = invoice.getCheckout();

            final String returnUrl = "http://localhost/checkout/payos/webhook";
            final String cancelUrl = "http://localhost/checkout/payos/webhook?paidUnsuccessful";
            // Gen order code

            List<ItemData> itemList = new ArrayList<>();

            for (Checkout checkoutItem : checkoutItems)
            {
                ItemData item = new ItemData(checkoutItem.getProduct_id().getName(), checkoutItem.getQuantity(), checkoutItem.getProduct_id().getPrice()*checkoutItem.getQuantity());
                itemList.add(item);
            }

            String description = "HOA DON "+invoice.getId()+" TAI JACLOTH";

            PaymentData paymentData = new PaymentData((int) invoice.getId(), invoice.getTotalAmount(), description,
                    itemList, cancelUrl, returnUrl);
            JsonNode data = payOS.createPaymentLink(paymentData);

            String checkoutUrl = data.get("checkoutUrl").asText();

            invoice.setCheckoutUrl(checkoutUrl);
            invoiceService.saveInvoice(invoice);

            httpServletResponse.setHeader("Location", checkoutUrl);


            httpServletResponse.setStatus(302);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
