import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoinUtil {
    public static String convertValueToString(Double total) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(total);
    }

    public static Double getTotal(Transaction transaction) {
       return calculateTotal(transaction.getCoins());
    }

    public static Double getTotal(VendingMachineBank bank) {
        return calculateTotal(bank.getFunds());
    }

    public static void store(Coin coin, Map<Double, List<Coin>> coins) {
        Double value = coin.getValue();
        List<Coin> coinsAtValue = new ArrayList<>();
        if(!coins.isEmpty() && coins.get(value) != null) {
            coinsAtValue = coins.get(value);
        }
        coinsAtValue.add(coin);
        coins.put(value, coinsAtValue);
    }

    private static Double calculateTotal(Map<Double, List<Coin>> coins) {
        Double totalFunds = 0.0;
        for(Double key : coins.keySet()) {
            int numberOfCoins = coins.get(key).size();
            Double coinValue = key;
            totalFunds += coinValue * numberOfCoins;
        }

        DecimalFormat formatter = new DecimalFormat("0.00");

        return Double.valueOf(formatter.format(totalFunds));
    }
}
