import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        products = new ArrayList<>();
    }

    public Inventory(List<Product> products) {
        this.products = products;
    }
}
