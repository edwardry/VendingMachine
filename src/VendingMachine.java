public class VendingMachine {
    private VendingMachineBank bank = new VendingMachineBank();

    public String insertCoin(Double value) {
        return bank.depositMoney(value);
    }

    public void setBank(VendingMachineBank bank) {
        this.bank = bank;
    }
}
