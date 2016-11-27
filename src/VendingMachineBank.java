import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachineBank {
    private Map<Double, List<Coin>> funds;

    public VendingMachineBank() {
        funds = new HashMap<>();
    }

    public VendingMachineBank(Map<Double, List<Coin>> coins) {
        funds = coins;
    }

    public void depositMoney(Transaction transaction, Product product) {
        Double valueToDeposit = product.getPrice();
        Double amountDeposited = 0.0;
        Map<Double, List<Coin>> coins = transaction.getCoins();

        for(Double coinValue : coins.keySet()) {
            if(!coins.get(coinValue).isEmpty()) {
                amountDeposited = calculateHowManyToDeposit(coinValue, transaction, amountDeposited, valueToDeposit);
            }
        }
    }

    private Double calculateHowManyToDeposit(Double value, Transaction transaction, Double amountDeposited, Double valueToDeposit) {
        int numberOfCoinsRemoved = 0;
        for(Coin coin : transaction.getCoins().get(value)) {
            if(value + amountDeposited <= valueToDeposit) {
                amountDeposited += value;
                addFunds(coin);
                numberOfCoinsRemoved++;
            }
        }

        removeCoinsFromTransaction(value, numberOfCoinsRemoved, transaction);

        return amountDeposited;
    }

    private void addFunds(Coin coin) {
        CoinUtil.store(coin, funds);
    }


    private void removeCoinsFromTransaction(Double value, int numberOfCoinsRemoved, Transaction transaction) {
        for(int i=0;i < numberOfCoinsRemoved;i++) {
            transaction.remove(value);
        }
    }

    public boolean hasSufficientChange(Inventory inventory) {
        return true;
    }

    public static Double determineValue(Coin coin) {
        Double coinMass = coin.getMass();
        Double coinDiameter = coin.getDiameter();

        for(Coin validCoin : ValidCoins.coins) {
            Double validCoinMass = validCoin.getMass();
            Double validCoinDiameter = validCoin.getDiameter();

            if(coinMass.equals(validCoinMass) && coinDiameter.equals(validCoinDiameter)) {
                return validCoin.getValue();
            }
        }

        return Coin.VALUELESS;
    }

    public Map<Double, List<Coin>> getFunds() {
        return funds;
    }
}