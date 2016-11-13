public class VendingMachine {
    private VendingMachineBank bank = new VendingMachineBank();
    private ButtonPanel buttons = new ButtonPanel();
    private Transaction transaction = new Transaction();
    private Screen screen = new Screen();

    public void insertCoin(Double value) {
        if(!bank.isMoneyValid(value)) {
            screen.updateDisplay(Screen.INSERT_COIN);
        } else {
            transaction.updateTotal(value);
            screen.updateDisplay(transaction);
        }
    }

    public void pressButton(Product product) {
        if(buttons.press(product, transaction)) {
            bank.depositMoney(transaction);
            transaction.clear();
        }

        screen.updateDisplay(transaction.getStatus());
    }

    public String checkDisplay() {
        return screen.getDisplay();
    }
}
