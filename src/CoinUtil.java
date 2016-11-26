import java.text.NumberFormat;

public class CoinUtil {
    public static String convertValueToString(Double total) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(total);
    }
}
