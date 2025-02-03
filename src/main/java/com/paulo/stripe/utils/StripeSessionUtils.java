package com.paulo.stripe.utils;

import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class StripeSessionUtils {

    public Optional<String> getCustomerEmail(Session session) {
        return Optional.of(
                Objects.isNull(session.getCustomerEmail()) ?
                        session.getCustomerDetails().getEmail() : session.getCustomerEmail()
        );
    }
}
