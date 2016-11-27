public class VendingMachine {
    private VendingMachineBank bank;
    private ButtonPanel buttons;
    private Transaction transaction;
    private Screen screen;
    private CoinReturn coinReturn;
    private Inventory inventory;

    public VendingMachine(VendingMachineBank bank, Inventory inventory) {
        this.bank = bank;
        this.inventory = inventory;
        buttons = new ButtonPanel();
        transaction = new Transaction();
        screen = new Screen();
        coinReturn = new CoinReturn();
        determineDefaultMessage(bank);
    }

    private void determineDefaultMessage(VendingMachineBank bank) {
        if(bank.hasSufficientChange(inventory)) {
            screen.setDefaultMessage(Screen.INSERT_COIN);
        } else {
            screen.setDefaultMessage(Screen.EXACT_CHANGE);
        }
    }

    public void insertCoin(Coin coin) {
        Double value = VendingMachineBank.determineValue(coin);
        if(value.equals(Coin.VALUELESS)) {
            screen.updateDisplay(screen.getDefaultMessage());
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
            returnCoins();
            determineDefaultMessage(bank);
        }

        screen.updateDisplay(transaction.getStatus());
    }

    public String checkDisplay() {
        return screen.getDisplay();
    }

    public void returnCoins() {
        coinReturn.updateTotal(transaction.getTotal());
        transaction.clear();
    }

    public void removeCoinsFromCoinReturn() {
        coinReturn.takeCoins();
    }
}
