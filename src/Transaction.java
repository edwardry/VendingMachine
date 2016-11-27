import java.util.*;

public class Transaction {
    private Map<Double, List<Coin>> coins;
    private String status;

    public Transaction() {
        coins = new HashMap<>();
    }

    public Map<Double, List<Coin>> getCoins() {
        return coins;
    }

    public void update(Coin coin) {
        CoinUtil.store(coin, coins);
    }

    public void remove(Double value) {
        List<Coin> coinsAtValue = coins.get(value);
        coinsAtValue.remove(0);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void clear() {
        coins.clear();
    }
}
