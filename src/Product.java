public class Product {
    private String name;
    private Double price;
    private Integer count;

    public Product(String name, Double price, Integer count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean hasInventory() {
        return count > 0;
    }
}
