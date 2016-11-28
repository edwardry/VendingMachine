import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {

    public static void addCoins(int numberOfCoins, Coin coin, Transaction transaction) {
        for(int i=0;i<numberOfCoins;i++) {
            transaction.update(coin);
        }
    }

    public static Map<Double, List<Coin>> createBankFunds(int numberOfNickels, int numberOfDimes, int numberOfQuarters) {
        Map<Double, List<Coin>> coins = new HashMap<>();

        Coin nickel = CommonTestConstants.nickel;
        Coin dime = CommonTestConstants.dime;
        Coin quarter = CommonTestConstants.quarter;

        List<Coin> nickels = addCoins(numberOfNickels, nickel);
        List<Coin> dimes = addCoins(numberOfDimes, dime);
        List<Coin> quarters = addCoins(numberOfQuarters, quarter);

        coins.put(CommonTestConstants.NICKEL_VALUE, nickels);
        coins.put(CommonTestConstants.DIME_VALUE, dimes);
        coins.put(CommonTestConstants.QUARTER_VALUE, quarters);

        return coins;
    }

    private static List<Coin> addCoins(int number, Coin coin) {
        List<Coin> coins = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            coins.add(coin);
        }

        return coins;
    }
}
