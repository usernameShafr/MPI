package com.ifmo.hatchery.controller;

import com.ifmo.hatchery.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "infoMessage", required = false) String infoMessage,
                        @RequestParam(value = "errorMessage", required = false) String errorMessage) {

        model.addAttribute("TABLE_MAP", dashboardService.getTaskMap());
        if(errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
        }
        if(infoMessage != null){
            model.addAttribute("infoMessage", infoMessage);
        }
        return "/dashboard";
    }

    public static boolean hasFailures(Object status){
        if(status instanceof Long) {
            long failedCount = (Long) status;
            return failedCount > 0L;
        } else {
            return false;
        }
    }
}
