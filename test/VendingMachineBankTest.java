import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class VendingMachineBankTest {
    private VendingMachineBank bank;
    private Transaction transaction;
    private Product product;
    private Double expectedResult;
    private Double actualResult;

    @Before
    public void setUp() {
        transaction = new Transaction();
        product = new Product("Cola", 1.00, 1);
        bank = new VendingMachineBank(createInitialCoins());
    }

    private Map<Double, List<Coin>> createInitialCoins() {
        Map<Double, List<Coin>> coins = new HashMap<>();

        Coin nickel = CommonTestConstants.nickel;
        Coin dime = CommonTestConstants.dime;
        Coin quarter = CommonTestConstants.quarter;

        List<Coin> nickels = addCoins(3, nickel);
        List<Coin> dimes = addCoins(3, dime);
        List<Coin> quarters = addCoins(3, quarter);

        coins.put(CommonTestConstants.NICKEL_VALUE, nickels);
        coins.put(CommonTestConstants.DIME_VALUE, dimes);
        coins.put(CommonTestConstants.QUARTER_VALUE, quarters);

        return coins;
    }

    private List<Coin> addCoins(int number, Coin coin) {
        List<Coin> coins = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            coins.add(coin);
        }

        return coins;
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
    public void WhenDepositMoneyIsPassedATransactionWithTotalMoreThanProductThenRemainingMoneyIsSentToCoinReturn() {
    }
}
