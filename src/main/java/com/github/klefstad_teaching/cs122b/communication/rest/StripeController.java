package com.github.klefstad_teaching.cs122b.communication.rest;

import com.github.klefstad_teaching.cs122b.communication.config.ActivityServiceConfig;
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
    public StripeController(ActivityServiceConfig config)
    {
        // We set up our Stripe by assigning the api key;
        Stripe.apiKey = config.getStripeApiKey();
    }

    @GetMapping("/stripe")
    public ResponseEntity<String> intent()
        throws StripeException
    {
        // Stripe takes amount in total cents
        // so $19.95 would be 1995
        Long amountInTotalCents = 1995L;

        String description = "A description of our sale";

        PaymentIntentCreateParams paymentIntentCreateParams =
            PaymentIntentCreateParams
                .builder()
                .setCurrency("USD")
                .setDescription(description)
                .setAmount(amountInTotalCents)
                // we use MetaData to keep track of the
                // user that should pay for the order
                .putMetadata("userId", "1")
                .setAutomaticPaymentMethods(
                    // This will tell stripe to generate the payment methods automatically
                    // Which is what we will be doing in this course
                    PaymentIntentCreateParams.AutomaticPaymentMethods
                        .builder()
                        .setEnabled(true)
                        .build()
                )
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);

        // This is the client secret that we pass to our front end to let the user
        // Complete the payment
        LOG.info(paymentIntent.getClientSecret());

        // When we want to get the paymentIntent later on in our service to
        // verify that its completed we retrieve it from stripe by using its id

        String paymentIntentId = paymentIntent.getId();

        PaymentIntent retrievedPaymentIntent = PaymentIntent.retrieve(paymentIntentId);

        LOG.info(retrievedPaymentIntent.getStatus());

        return ResponseEntity.ok().build();
    }
}
