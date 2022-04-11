# CS122B Activity 5 - Stripe

- [PaymentIntent](#paymentintent)
- [BigDecimal](#bigdecimal)

# PaymentIntent

When we want to process a user's order we have to first create a `PaymentIntent` with Stripe. We do this by first creating an instance of `PaymentIntentCreateParams`:

```java
// Stripe takes amount in total cents
// so $19.95 would be 1995
Long amountInTotalCents = 1995L; 
String description = "A description of our sale";
String userId = Long.toString(userId);

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
```

Once we create our `PaymentIntentCreateParams` its time to create a new `PaymentIntent` with Stripe, assuming we have our `Stripe::apiKey` set with our api key this should create a new `PaymentIntent` with Stripe:

```java
PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
```

When we call `PaymentIntent::create(PaymentIntentCreateParams)` a PaymentIntent will be created on Stripes side and will return the resulting `PaymentIntent` that contain two values we are concerned with.

```java
String paymentIntentId = paymentIntent.getId();
String clientSecret = paymentIntent.getClientSecret()
```

The first value `paymentIntentId` will allow us to be able to retrieve the `PaymentIntent` later (When we need to confirm is the payment has been completed). The second value `clientSecret` is needed by the frontend to give to Stripes frontend SDK to tell Stripe which intent this user should be completing.

# BigDecimal

When dealing with currency we want to ensure that we are working with the same scale and we are rounding in a consistent manner when applying discounts.

For this we will be using Java's `BigDecimal` class. This allows us the control we need and also provides us with the numbers string format.

### Important
When calling methods to calculate a `BigDecimal` a **NEW** `BigDecimal` is created and returned. The `BigDecimal` whos method was called **IS NOT MODIFIED**. This is because all instances of `BigDecimal` are **IMMUTABLE**.

```java
public void bigDecimal()
{
    BigDecimal first = BigDecimal.valueOf(1);
    BigDecimal second = BigDecimal.valueOf(2);

    first.add(second); // Because the return value is not saved this does nothing

    System.out.println(first); // prints 1 as it is Immutable

    BigDecimal sum = first.add(second); 

    System.out.println(sum); // prints 3 because the new instance of BigDecimal when calling .add() is saved to sum
}
```

### Common Pattern

When working with <code>BigDecimal</code> it is usually common to keep *overriding* the initial value with the new value.

```java
public void bigDecimal()
{
    BigDecimal total = BigDecimal.ZERO;

    total = total.add(BigDecimal.valueOf(1.5));  // Overwrites total with the new value
    total = total.add(BigDecimal.valueOf(2.5));  // Overwrites total with the new value
    total = total.add(BigDecimal.valueOf(0.01)); // Overwrites total with the new value

    System.out.println(total); // prints 4.01
}
```

### Scale and RoundingMode

One of the primary uses of <code>BigDecimal</code> is the ability to set the <code>scale</code> or in other terms, the amount of places after the decimal that we care about.

```java
public void bigDecimal()
{
    BigDecimal hundreds = BigDecimal.valueOf(1).setScale(2);

    System.out.println(hundreds); // prints 1.00

    BigDecimal thousands = BigDecimal.valueOf(1).setScale(3);

    System.out.println(thousands); // prints 1.000
}
```

This also helps when we need to round the value:

```java
public void bigDecimal()
{
    BigDecimal num = BigDecimal.valueOf(1.555);

    // Setting the scale to 1
    System.out.println(num.setScale(1, RoundingMode.DOWN));    // prints 1.5

    System.out.println(num.setScale(1, RoundingMode.UP));      // prints 1.6

    System.out.println(num.setScale(1, RoundingMode.HALF_UP)); // prints 1.6


    // Setting the scale to 2
    System.out.println(num.setScale(2, RoundingMode.DOWN));    // prints 1.55

    System.out.println(num.setScale(2, RoundingMode.UP));      // prints 1.56

    System.out.println(num.setScale(2, RoundingMode.HALF_UP)); // prints 1.56


    // THROWS ERROR Because there is a remainder left after dividing because
    // RoundingMode.UNNECESSARY is the same as saying: There should be no remainder
    System.out.println(num.setScale(1, RoundingMode.UNNECESSARY)); // Throws Error
}
```

We can also just do all the math we need then apply the <code>RoundingMode</code> when we set the <code>scale</code>

```java
public void bigDecimal()
    {
    BigDecimal discounted = BigDecimal.valueOf(19.99).multiply(BigDecimal.valueOf(0.85)); 

    discounted = discounted.setScale(2, RoundingMode.DOWN);
}
```
  
