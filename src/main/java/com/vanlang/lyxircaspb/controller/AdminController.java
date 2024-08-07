package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    public final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint yêu cầu quyền ADMIN
    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> getAdminData() {
        // Trả về dữ liệu admin hoặc thông báo thành công
        return ResponseEntity.ok("Admin data");
    }
}
