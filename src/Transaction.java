public class Transaction {
    private Double total;
    private String status;

    public void updateTotal(Double value) {
        this.total = value;
    }

    public String getStatus() {
        return status;
    }
}
