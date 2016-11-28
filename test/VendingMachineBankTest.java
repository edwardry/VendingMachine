import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class VendingMachineBankTest {
    private VendingMachineBank bank;
    private Transaction transaction;
    private Product product;
    private Double expectedResult;
    private Double actualResult;
    private Inventory inventory;

    @Before
    public void setUp() {
        transaction = new Transaction();
        product = CommonTestConstants.cola;
        bank = new VendingMachineBank(TestUtil.createBankFunds(3, 3, 3));
        inventory = new Inventory(Arrays.asList(CommonTestConstants.cola, CommonTestConstants.chips, CommonTestConstants.candy));
    }

    @Test
    public void WhenDetermineCoinValueIsPassedACoinWithNickelWeightAndDiameterReturnsValueOfNickel() {
        expectedResult = 0.05;

        actualResult = VendingMachineBank.determineValue(CommonTestConstants.nickel);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDetermineCoinValueIsPassedACoinWithDimeWeightAndDiameterReturnsValueOfDime() {
        expectedResult = 0.10;

        actualResult = VendingMachineBank.determineValue(CommonTestConstants.dime);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDetermineCoinValueIsPassedACoinWithQuarterWeightAndDiameterReturnsValueOfQuarter() {
        expectedResult = 0.25;

        actualResult = VendingMachineBank.determineValue(CommonTestConstants.quarter);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDetermineCoinValueIsPassedAnInvalidCoinThenReturnsValueOfZero() {
        Coin coin = new Coin(2.5, 19.05);
        expectedResult = 0.0;

        actualResult = VendingMachineBank.determineValue(coin);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDepositMoneyIsPassedATransactionWithTotalSameAsProductThenAllMoneyInTheTransactionIsDeposited() {
        TestUtil.addCoins(4, CommonTestConstants.quarter, transaction);
        expectedResult = CoinUtil.getTotal(bank) + 1.00;

        bank.depositMoney(transaction, product);
        actualResult = CoinUtil.getTotal(bank);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDepositMoneyIsPassedATransactionWithTotalMoreThanProductThenRemainingMoneyIsStillInTransaction() {
        TestUtil.addCoins(8, CommonTestConstants.quarter, transaction);
        expectedResult = CoinUtil.getTotal(transaction) - 1.00;

        bank.depositMoney(transaction, product);
        actualResult = CoinUtil.getTotal(transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenAttemptingToDepositATransactionWithNoMoneyThenNothingIsDeposited() {
        expectedResult = CoinUtil.getTotal(transaction);

        bank.depositMoney(transaction, product);
        actualResult = CoinUtil.getTotal(transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDepositingMoneyFromATransactionWithABalanceButProductHasNoPriceThenNothingIsDeposited() {
        expectedResult = CoinUtil.getTotal(transaction);
        Product freeProduct = new Product("Chips", 0.00, 1);

        bank.depositMoney(transaction, freeProduct);
        actualResult = CoinUtil.getTotal(transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenBankHasSufficientChangeThenReturnTrue() {
        Boolean expectedResult = true;

        Boolean actualResult = bank.hasSufficientChange(inventory);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenBankDoesNotHaveSufficientChangeThenReturnFalse() {

    }
}
