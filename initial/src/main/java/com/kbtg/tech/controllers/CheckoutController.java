package com.kbtg.tech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

	@GetMapping("/Checkout")
    public String checkout() {
        return "checkout";
    }

    @PostMapping("/Confirm")
    public String confirm() {
        return "confirm";
    }

}
