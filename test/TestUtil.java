public class TestUtil {

    public static void addCoins(int numberOfCoins, Coin coin, Transaction transaction) {
        for(int i=0;i<numberOfCoins;i++) {
            transaction.update(coin);
        }
    }
}
