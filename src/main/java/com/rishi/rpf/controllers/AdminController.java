package com.rishi.rpf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> users() {
        return adminService.listAllUsers();
    }

    @GetMapping("/enable/{id}")
    @ResponseBody
    public String enable(@PathVariable("id") String id) {
        adminService.enableUser(id);
        return "OK";
    }

    @GetMapping("/disable/{id}")
    @ResponseBody
    public String disable(@PathVariable("id") String id) {
        adminService.disableUser(id);
        return "OK";
    }

    @GetMapping("/promote/{id}")
    @ResponseBody
    public String promote(@PathVariable("id") String id) {
        adminService.promoteToAdmin(id);
        return "OK";
    }

    @GetMapping("/demote/{id}")
    @ResponseBody
    public String demote(@PathVariable("id") String id) {
        adminService.demoteToUser(id);
        return "OK";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") String id) {
        adminService.deleteUser(id);
        return "OK";
    }

    @GetMapping("/stats")
    @ResponseBody
    public String stats() {
        return "users=" + adminService.totalUsers() + ", admins=" + adminService.totalAdmins();
    }
}


