package com.paulo.stripe.cache;

import com.stripe.model.PaymentLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class PaymentLinkCache {
    private ConcurrentHashMap<String, PaymentLink> concurrentHashMap = new ConcurrentHashMap<>();

    public void addPaymentLink(PaymentLink paymentLink) {
        concurrentHashMap.put(paymentLink.getId(), paymentLink);
        log.info("Added Payment Link with ID: {} to cache", paymentLink.getId());
    }

    public void removePaymentLink(String paymentLinkId) {
        concurrentHashMap.remove(paymentLinkId);
        log.info("Removed Payment Link with ID: {} from cache", paymentLinkId);
    }
}
