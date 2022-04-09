# CS122B Activity 5 - Stripe

- [BigDecimal](#bigdecimal)

# BigDecimal

When dealing with currency we want to ensure that we are working with the same scale and we are rounding in a consistent manner when applying discounts.

For this we will be using Java's <code>BigDecimal</code> class. This allows us the control we need and also provides us with the numbers string format.

### Important
When calling methods to calculate a <code>BigDecimal</code> a **NEW** <code>BigDecimal</code> is created and returned. The <code>BigDecimal</code> whos method was called **IS NOT MODIFIED**. This is because all instances of <code>BigDecimal</code> are **IMMUTABLE**.

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
  
