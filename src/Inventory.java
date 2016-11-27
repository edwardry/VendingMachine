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

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(String name) {
        for(Product product : products) {
            if(product.getName().equals(name)) {
                return product;
            }
        }

        return new Product();
    }
}
