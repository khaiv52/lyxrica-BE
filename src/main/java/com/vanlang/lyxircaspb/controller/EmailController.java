package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.repository.UserRepository;
import com.vanlang.lyxircaspb.request.EmailRequest;
import com.vanlang.lyxircaspb.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class EmailController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;
    private UserRepository userRepository;

    public EmailController(JavaMailSender mailSender, UserService service, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userService = service;
        this.userRepository = userRepository;
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestBody EmailRequest req) {
        String email = req.getEmail();
        User user = userRepository.findByEmail(email);
        if(user != null) {
            // Create a reset password link
            String resetPasswordLink = "http://localhost:3000/reset-password?email=" + email;
            try {
                sendResetPasswordEmail(email, resetPasswordLink);
                return ResponseEntity.ok("Email sent");
            } catch (MessagingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
            }
        } else {
            return ResponseEntity.badRequest().body("Email does not exist");
        }
    }

    private void sendResetPasswordEmail(String email, String resetPasswordLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Reset Your Password");
        helper.setText(resetPasswordLink, true);

        mailSender.send(message);
    }

}
