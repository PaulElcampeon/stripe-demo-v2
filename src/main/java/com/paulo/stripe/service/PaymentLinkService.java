package com.paulo.stripe.service;

import com.paulo.stripe.cache.PaymentLinkCache;
import com.paulo.stripe.domain.enums.CurrencyCode;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentLinkService {

    private final StripeClient stripeClient;
    private final PaymentLinkCache paymentLinkCache;

    public PaymentLinkService(StripeClient stripeClient, PaymentLinkCache paymentLinkCache) {
        this.stripeClient = stripeClient;
        this.paymentLinkCache = paymentLinkCache;
    }

    public PaymentLink createPaymentLink(Long amountInPence, CurrencyCode currencyCode, String productName, Long productQuantity) throws StripeException {

        Price price = stripeClient.prices().create(
                PriceCreateParams.builder()
                        .setCurrency(currencyCode.getValue())
                        .setUnitAmount(amountInPence)
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName(productName).build()
                        )
                        .build()
        );



        PaymentLink paymentLink = stripeClient.paymentLinks().create(
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(productQuantity)
                                        .build()
                        )
                        .build()
        );

        paymentLinkCache.addPaymentLink(paymentLink);

        return paymentLink;
    }

    public void removePaymentLink(String paymentLinkId) {
        paymentLinkCache.removePaymentLink(paymentLinkId);
    }
}
