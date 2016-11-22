import java.util.List;

public class VendingMachineBank {

    public void depositMoney(Transaction transaction, Product product) {

    }

    public Boolean isMoneyValid(Coin value) {
        return true;
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
}