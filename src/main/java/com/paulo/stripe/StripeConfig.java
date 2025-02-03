package com.paulo.stripe;

import com.stripe.StripeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Bean
    public StripeClient stripeClient() {
        return new StripeClient("sk_test_51NakfLDmTzP0jvvkjaMikawOIV6UBrawmxkU8HTtrYsvmz8riK65tL84BmEsXP8D3Ehu00MkeLKEIg2WeXKe1dkp00pKf9WnHq");
    }

}
