public class Screen {
    public static final String INSERT_COIN = "INSERT COIN";
    public static final String THANK_YOU = "THANK YOU";
    String display;

    public void updateDisplay(Transaction transaction) {

    }

    public void updateDisplay(String text) {
        this.display = text;
    }

    public String getDisplay() {
        return this.display;
    }
}