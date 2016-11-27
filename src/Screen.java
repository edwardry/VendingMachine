public class Screen {
    public static final String INSERT_COIN = "INSERT COIN";
    public static final String THANK_YOU = "THANK YOU";
    public static final String PRICE = "PRICE";
    public static final String SOLD_OUT = "SOLD OUT";
    public static final String EXACT_CHANGE = "EXACT CHANGE ONLY";
    private String display;
    private String defaultMessage = "INSERT COIN";

    public Screen() {
        display = defaultMessage;
    }

    public void updateDisplay(Transaction transaction) {
        display = CoinUtil.convertValueToString(transaction.getTotal());
    }

    public void updateDisplay(String text) {
        this.display = text;
    }

    public String getDisplay() {
        return display;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
