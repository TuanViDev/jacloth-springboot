package com.vanlang.webbanquanao.Controller.Webhook;

import com.vanlang.webbanquanao.Model.Invoice;
import com.vanlang.webbanquanao.Service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebhookPayosController
{
    private final InvoiceService invoiceService;

    public WebhookPayosController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/checkout/payos/webhook")
    public String webhookPayos(@RequestParam("cancel") Boolean cancel, @RequestParam("status") String status, @RequestParam("orderCode") long invoiceId)
    {
        //?code=00&id=082ba728fe5d426c97c438be42493b4e&cancel=false&status=PAID&orderCode=8

        if (cancel.equals(true))
        {
            return "redirect:/invoice?paidUnsuccessful";
        }
        else if (status.equals("PAID"))
        {
            Invoice invoice = invoiceService.getInvoiceById(invoiceId);
            invoice.setStatus("Đã thanh toán");
            invoiceService.saveInvoice(invoice);
            return "redirect:/invoice?paidSuccessful";
        }
        else
        {
            return "redirect:/invoice";
        }
    }

}
