public class VendingMachine {
    private VendingMachineBank bank = new VendingMachineBank();
    private ButtonPanel buttons = new ButtonPanel();
    private Transaction transaction = new Transaction();
    private Screen screen = new Screen();
    private CoinReturn coinReturn = new CoinReturn();

    public void insertCoin(Double value) {
        if(!bank.isMoneyValid(value)) {
            screen.updateDisplay(Screen.INSERT_COIN);
            coinReturn.updateTotal(value);
        } else {
            transaction.updateTotal(value);
            screen.updateDisplay(transaction);
        }
    }

    public void pressButton(Product product) {
        Boolean transactionHasSufficientFundsForPurchase = buttons.press(product, transaction);
        Boolean productHasInventory = product.hasInventory();
        if(transactionHasSufficientFundsForPurchase && productHasInventory) {
            bank.depositMoney(transaction, product);
            coinReturn.updateTotal(transaction.getTotal());
            transaction.clear();
        }

        screen.updateDisplay(transaction.getStatus());
    }

    public String checkDisplay() {
        return screen.getDisplay();
    }
}
