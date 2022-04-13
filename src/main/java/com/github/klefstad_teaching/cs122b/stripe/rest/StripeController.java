package com.github.klefstad_teaching.cs122b.stripe.rest;

import com.github.klefstad_teaching.cs122b.stripe.config.StripeServiceConfig;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripeController
{
    private static final Logger LOG = LoggerFactory.getLogger(StripeController.class);

    @Autowired
    public StripeController(StripeServiceConfig config)
    {
        // We set up our Stripe by assigning the api key;
        Stripe.apiKey = config.getApiKey();
    }

    @GetMapping("/stripe/intent")
    public ResponseEntity<String> intent()
        throws StripeException
    {
        // Stripe takes amount in total cents
        // so $19.95 would be 1995
        Long amountInTotalCents = 1995L;
        String description = "A description of our sale";
        String userId = Long.toString(1);

        PaymentIntentCreateParams paymentIntentCreateParams =
            PaymentIntentCreateParams
                .builder()
                .setCurrency("USD") // This will always be the same for our project
                .setDescription(description)
                .setAmount(amountInTotalCents)
                // We use MetaData to keep track of the user that should pay for the order
                .putMetadata("userId", userId)
                .setAutomaticPaymentMethods(
                    // This will tell stripe to generate the payment methods automatically
                    // This will always be the same for our project
                    PaymentIntentCreateParams.AutomaticPaymentMethods
                        .builder()
                        .setEnabled(true)
                        .build()
                )
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);

        // When we want to get the paymentIntent later on in our service to
        // verify that its completed we retrieve it from stripe by using its id
        String paymentIntentId = paymentIntent.getId();
        LOG.info("PaymentIntent ID: {}", paymentIntentId);

        // This is the client secret that we pass to our front end to let the user
        // Complete the payment
        LOG.info("Client Secret: {}", paymentIntent.getClientSecret());

        // When the user completes their order on the front end we want to call the backend
        // and send the paymentIntentId so that we can confirm that the order has
        // been completed.
        PaymentIntent retrievedPaymentIntent = PaymentIntent.retrieve(paymentIntentId);

        LOG.info("Current Status: {}", retrievedPaymentIntent.getStatus());

        return ResponseEntity.ok().build();
    }
}
