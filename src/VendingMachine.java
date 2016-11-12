public class VendingMachine {
    private VendingMachineBank bank = new VendingMachineBank();

    public String insertCoin(Double value) {
        if(!bank.isMoneyValid(value)) {
            return "INSERT COIN";
        }

        return bank.depositMoney(value);
    }
}
