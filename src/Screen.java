public class Screen {
    public static final String INSERT_COIN = "INSERT COIN";
    public static final String THANK_YOU = "THANK YOU";
    public static final String PRICE = "PRICE";
    public static final String SOLD_OUT = "SOLD OUT";
    String display;

    public void updateDisplay(Transaction transaction) {

    }

    public void updateDisplay(String text) {
        this.display = text;
    }

    public String getDisplay() {
        return display;
    }
}
