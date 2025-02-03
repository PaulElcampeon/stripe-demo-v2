package com.paulo.stripe;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.v2.Event;
import com.stripe.param.WebhookEndpointCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/webhook")
@Slf4j
public class WebhookController {

    private final StripeClient stripeClient;

    public WebhookController(StripeClient stripeClient) throws StripeException {
        this.stripeClient = stripeClient;

        this.init();
    }

    private void init() throws StripeException {
        stripeClient.webhookEndpoints().create(
                WebhookEndpointCreateParams.builder()
                        .addEnabledEvent(WebhookEndpointCreateParams.EnabledEvent.ALL)
                        .setUrl("https://example.com/api/v1/webhook")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity webhook(@RequestBody Event event) {

//        event = ApiResource.GSON.fromJson(payload, Event.class);

        log.info("{} {}", event.getType(), event);

        return ResponseEntity.ok().build();

    }
}
