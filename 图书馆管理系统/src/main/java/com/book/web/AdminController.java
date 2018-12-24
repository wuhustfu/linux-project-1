package com.book.web;

import com.book.domain.Admin;
import com.book.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminController {

    private AdminService adminService;
    @Autowired
    public void setAdminService(AdminService adminService){
        this.adminService=adminService;
    }


    @RequestMapping("/admin_fine.html")
    public ModelAndView toAdminFine(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("admin_fine");

       float fine=adminService.getAdminFine(20170001);

        modelAndView.addObject("fine",fine);
        return modelAndView;
    }


}

