package com.vanlang.webbanquanao.Controller.Admin;

import com.vanlang.webbanquanao.Service.StatisticService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminStatisticController
{
    private final StatisticService statisticService;

    public AdminStatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/admin")
    public String AdminShow()
    {
        return "redirect:/admin/statistic";
    }

    @GetMapping("/admin/statistic")
    public String AdminStatisticShow(Model model)
    {
        model.addAttribute("productCount",statisticService.productCount());
        model.addAttribute("userCount",statisticService.userCount());
        model.addAttribute("categoryCount",statisticService.categoryCount());
        model.addAttribute("productSold",statisticService.countSoldOfAllProduct());

        model.addAttribute("invoiceCount",statisticService.invoiceCount());
        model.addAttribute("totalAmount",statisticService.countTotalAmountOfAllInvoices());

        model.addAttribute("userCount",statisticService.countUsersByRole("USER"));
        model.addAttribute("adminCount",statisticService.countUsersByRole("ADMIN"));


        return "admin/statistic/view";
    }
}
