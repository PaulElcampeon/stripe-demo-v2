package com.paulo.stripe;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.BalanceTransaction;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api/v1/test")
@Slf4j
public class NoNameController {

    private final StripeClient stripeClient;

    private ConcurrentHashMap<String, PaymentLink> concurrentHashMap = new ConcurrentHashMap<>();

    public NoNameController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @GetMapping
    public ResponseEntity<String> generate() throws StripeException {
        Price price = stripeClient.prices().create(
                PriceCreateParams.builder()
                        .setCurrency("usd")
                        .setUnitAmount(2000L)
//                        .setRecurring(
//                                PriceCreateParams.Recurring.builder()
//                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
//                                        .build()
//                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                        )
                        .build()
        );

//        Price price = Price.create(params);



        PaymentLink paymentLink = stripeClient.paymentLinks().create(
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .build()
        );

//        PaymentLink paymentLink = PaymentLink.create(params);
//        String paymentLinkId = paymentLink.listLineItems().getData().get(0).getId();

        concurrentHashMap.put(paymentLink.getId(), paymentLink);
        PaymentLinkTwo paymentLinkTwo = new PaymentLinkTwo();
        paymentLinkTwo.link = paymentLink.getUrl();
        System.out.println(paymentLink.toString());
        concurrentHashMap.entrySet().forEach(stringStringEntry -> log.info("Key: {} \n Value: {}", stringStringEntry.getKey(), stringStringEntry.getValue().toString()));
        return ResponseEntity.ok(paymentLink.toJson());
    }

    @GetMapping("/transactions")
    public ResponseEntity transactions() throws StripeException {
        List<BalanceTransaction> balanceTransactions = stripeClient.balanceTransactions().list().getData();
        balanceTransactions.forEach((balanceTransaction) -> log.info("{}", balanceTransaction));
//        System.out.println(paymentLink.toString());
        return ResponseEntity.ok().build();
    }
}
