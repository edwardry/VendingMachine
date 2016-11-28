import java.util.*;

public class ValidCoins {
    private static Coin nickel = new Coin(5.0, 21.21, .05);
    private static Coin dime = new Coin(2.268, 17.91, .10);
    private static Coin quarter = new Coin(5.670, 24.26, .25);
    public static final List<Coin> coins = new ArrayList<>(Arrays.asList(nickel, dime, quarter));
}
