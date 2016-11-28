public class CommonTestConstants {
    public static final Double NICKEL_MASS = 5.0;
    public static final Double NICKEL_DIAMETER = 21.21;
    public static final Double NICKEL_VALUE = 0.05;
    public static final Coin nickel = new Coin(NICKEL_MASS, NICKEL_DIAMETER, NICKEL_VALUE);
    public static final Double DIME_MASS = 2.268;
    public static final Double DIME_DIAMETER = 17.91;
    public static final Double DIME_VALUE = 0.10;
    public static final Coin dime = new Coin(DIME_MASS, DIME_DIAMETER, DIME_VALUE);
    public static final Double QUARTER_MASS = 5.670;
    public static final Double QUARTER_DIAMETER = 24.26;
    public static final Double QUARTER_VALUE = 0.25;
    public static final Coin quarter = new Coin(QUARTER_MASS, QUARTER_DIAMETER, QUARTER_VALUE);
    public static final String COLA = "Cola";
    public static final String CHIPS = "Chips";
    public static final String CANDY = "Candy";
    public static final Product cola = new Product(COLA, 1.00, 10);
    public static final Product chips = new Product(CHIPS, 0.50, 10);
    public static final Product candy = new Product(CANDY, 0.65, 10);
}
