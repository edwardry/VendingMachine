public class CoinReturn {
    private Double total = 0.0;

    public void updateTotal(Double value) {
        total += value;
    }

    public void takeCoins() {
        total = 0.0;
    }
}
