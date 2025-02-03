package com.paulo.stripe.handler;

import com.paulo.stripe.utils.StripeEventUtils;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventHandler {

    private final CheckoutSessionHandler checkoutSessionHandler;
    private final StripeEventUtils stripeEventUtils;


    public EventHandler(CheckoutSessionHandler checkoutSessionHandler, StripeEventUtils stripeEventUtils) {
        this.checkoutSessionHandler = checkoutSessionHandler;
        this.stripeEventUtils = stripeEventUtils;
    }

    public void handle(String stripeEvent, String sigHeader) throws SignatureVerificationException {
        Event event = stripeEventUtils.constructEventFromRequest(stripeEvent, sigHeader);

        log.info("Received event of type: {}", event.getType());

        StripeObject stripeObject = stripeEventUtils.getStripeObjectFromEvent(event).orElseThrow(() -> new RuntimeException());

        switch (event.getType()) {
            case "checkout.session.completed" ->  checkoutSessionHandler.handleCompleted((Session) stripeObject);
            default -> log.info("Unhandled event type: {}", event.getType());
        }
    }
}
