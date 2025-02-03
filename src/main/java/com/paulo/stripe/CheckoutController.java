package com.paulo.stripe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout.html";
    }

    @GetMapping("/success")
    public String success() {
        return "success.html";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "cancel.html";
    }
}
