package com.paulo.stripe.handler;

import com.paulo.stripe.service.ApiKeyService;
import com.paulo.stripe.service.EmailService;
import com.paulo.stripe.service.PaymentLinkService;
import com.paulo.stripe.utils.StripeSessionUtils;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class CheckoutSessionHandler {

    private final StripeSessionUtils stripeSessionUtils;
    private final ApiKeyService apiKeyService;
    private final EmailService emailService;
    private final PaymentLinkService paymentLinkService;

    public CheckoutSessionHandler(StripeSessionUtils stripeSessionUtils, ApiKeyService apiKeyService, EmailService emailService, PaymentLinkService paymentLinkService) {
        this.stripeSessionUtils = stripeSessionUtils;
        this.apiKeyService = apiKeyService;
        this.emailService = emailService;
        this.paymentLinkService = paymentLinkService;
    }

    public void handleCompleted(Session session) {
        log.info("Received checkout session completed event {}", session);

        String customerEmail = stripeSessionUtils.getCustomerEmail(session).orElseThrow(() -> new RuntimeException("Customer email not found"));
        String apiKey = apiKeyService.generateApiKey(customerEmail);

        String paymentLinkId =  session.getPaymentLink();

        if (!Objects.isNull(paymentLinkId)) {
            paymentLinkService.removePaymentLink(paymentLinkId);
        }

        emailService.sendSimpleEmail(customerEmail, "API-KEY", "Your new api key: " + apiKey);
    }
}
