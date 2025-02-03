package com.paulo.stripe.utils;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StripeEventUtils {

    @Value("${stripe.webhook-signing-secret}")
    private String webhookSigningSecret;

    public Optional<StripeObject> getStripeObjectFromEvent(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

        return deserializer.getObject();
    }

    public Event constructEventFromRequest(String stripeEvent, String sigHeader) throws SignatureVerificationException {
        return Webhook.constructEvent(stripeEvent, sigHeader, webhookSigningSecret);
    }
}
