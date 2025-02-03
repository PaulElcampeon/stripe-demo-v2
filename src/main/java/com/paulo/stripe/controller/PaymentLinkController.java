package com.paulo.stripe.controller;

import com.paulo.stripe.domain.enums.CurrencyCode;
import com.paulo.stripe.service.PaymentLinkService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment-link")
@Slf4j
public class PaymentLinkController {

    private final PaymentLinkService paymentLinkService;

    public PaymentLinkController(PaymentLinkService paymentLinkService) {
        this.paymentLinkService = paymentLinkService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generate() throws StripeException {
        PaymentLink paymentLink = paymentLinkService.createPaymentLink(500L, CurrencyCode.GBP, "Api Key", 1L);

        return ResponseEntity.ok(paymentLink.toJson());
    }
}
