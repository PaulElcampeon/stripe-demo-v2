package com.paulo.stripe.controller;

import com.paulo.stripe.handler.EventHandler;
import com.stripe.exception.SignatureVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/webhook")
@Slf4j
public class WebhookController {

    private final EventHandler eventHandler;

    public WebhookController(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @PostMapping
    public ResponseEntity webhook(@RequestBody String stripeEvent, @RequestHeader("Stripe-Signature") String sigHeader) throws SignatureVerificationException {
        eventHandler.handle(stripeEvent, sigHeader);

        return ResponseEntity.ok().build();
    }
}
