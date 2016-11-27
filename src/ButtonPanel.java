public class ButtonPanel {

    public boolean press(Product product, Transaction transaction) {
        Double productValue = product.getPrice();
        Double transactionTotal = transaction.getTotal();

        if(transactionTotal >= productValue) {
            transaction.setStatus(Screen.THANK_YOU);
            return true;
        } else {
            transaction.setStatus(Screen.PRICE);
            return false;
        }
    }
}
