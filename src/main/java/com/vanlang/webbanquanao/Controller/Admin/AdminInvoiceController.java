package com.vanlang.webbanquanao.Controller.Admin;

import com.vanlang.webbanquanao.Model.User;
import com.vanlang.webbanquanao.Service.InvoiceService;
import com.vanlang.webbanquanao.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class AdminInvoiceController
{
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/admin/invoice/list")
    public String ShowAdminInvoices(Model model, Principal principal)
    {
//        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "/admin/invoice/list";
    }

    @GetMapping("/admin/invoice/{id}")
    public String ShowAdminInvoiceDetails(@PathVariable("id") long id, Model model, Principal principal)
    {
            model.addAttribute("checkouts", invoiceService.getInvoiceById(id).getCheckout());
            model.addAttribute("invoice", invoiceService.getInvoiceById(id));
            return "/admin/invoice/invoice";
    }
}
