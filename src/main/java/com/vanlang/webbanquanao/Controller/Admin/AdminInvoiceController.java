package com.vanlang.webbanquanao.Controller.Admin;

import com.vanlang.webbanquanao.Model.Invoice;
import com.vanlang.webbanquanao.Model.User;
import com.vanlang.webbanquanao.Service.InvoiceService;
import com.vanlang.webbanquanao.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AdminInvoiceController
{
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/admin/invoice/cancel")
    public String CancelInvoice(@RequestParam("id") long id)
    {
        Invoice invoice = invoiceService.getInvoiceById(id);
        invoice.setStatus("Đã hủy");
        invoiceService.saveInvoice(invoice);
        return "redirect:/admin/invoice/"+invoice.getId()+"?cancelSuccess";
    }

    @PostMapping("/admin/invoice/confirm")
    public String ConfirmInvoice(@RequestParam("id") long id)
    {
        Invoice invoice = invoiceService.getInvoiceById(id);
        invoice.setStatus("Đã xác nhận");
        invoiceService.saveInvoice(invoice);
        return "redirect:/admin/invoice/"+invoice.getId()+"?confirmSuccess";
    }

    @PostMapping("/admin/invoice/deliver")
    public String DeliverInvoice(@RequestParam("id") long id)
    {
        Invoice invoice = invoiceService.getInvoiceById(id);
        invoice.setStatus("Đang vận chuyển");
        invoiceService.saveInvoice(invoice);
        return "redirect:/admin/invoice/"+invoice.getId()+"?deliverSuccess";
    }

    @PostMapping("/admin/invoice/done")
    public String DoneInvoice(@RequestParam("id") long id)
    {
        Invoice invoice = invoiceService.getInvoiceById(id);
        invoice.setStatus("Đã hoàn tất");
        invoiceService.saveInvoice(invoice);
        return "redirect:/admin/invoice/"+invoice.getId()+"?done";
    }

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
