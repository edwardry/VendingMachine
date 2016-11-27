import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class VendingMachineTest {
    @InjectMocks private VendingMachine vendingMachine;
    @Mock private VendingMachineBank bank;
    @Mock private Screen screen;
    @Mock private Product product;
    @Mock private ButtonPanel buttons;
    @Mock private Transaction transaction;
    @Mock private CoinReturn coinReturn;
    @Mock private Inventory inventory;
    private Coin coin;
    private String expectedResult = "";
    private String actualResult = "";
    private List<Coin> coins;
    private List<String> expectedResults;
    private String name = "";
    private Double price = 0.0;

    @Before
    public void setUp() {
        bank = new VendingMachineBank();
        inventory = new Inventory();
        vendingMachine = new VendingMachine(bank, inventory);
        MockitoAnnotations.initMocks(this);
        coins = new ArrayList<>();
        expectedResults = new ArrayList<>();
    }

    @Test
    public void WhenCustomerInsertsAValidCoinAsFirstTransactionInBankThenValueOfCoinIsDisplayedOnScreen() {
        coin = new Coin(CommonTestConstants.NICKEL_MASS, CommonTestConstants.NICKEL_DIAMETER);
        expectedResult = "$0.05";

        when(screen.getDisplay()).thenReturn(expectedResult);

        vendingMachine.insertCoin(coin);
        actualResult = vendingMachine.checkDisplay();

        verify(transaction).updateTotal(any(Double.class));
        verify(screen).updateDisplay(transaction);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsAnInvalidCoinInBankThenScreenDisplaysInsertCoinAndCoinIsPlacedInCoinReturn() {
        coin = new Coin(2.5, 19.05);
        expectedResult = Screen.INSERT_COIN;

        when(screen.getDisplay()).thenReturn(expectedResult);
        when(screen.getDefaultMessage()).thenReturn(Screen.INSERT_COIN);

        vendingMachine.insertCoin(coin);
        actualResult = vendingMachine.checkDisplay();

        verify(screen, times(2)).updateDisplay(expectedResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsTwoValidCoinsInBankThenScreenDisplaysSumOfCoinValue() {
        coins.add(0, new Coin(CommonTestConstants.NICKEL_MASS, CommonTestConstants.NICKEL_DIAMETER));
        expectedResults.add(0, "$0.05");
        coins.add(1, new Coin(CommonTestConstants.DIME_MASS, CommonTestConstants.DIME_DIAMETER));
        expectedResults.add(1, "$0.15");
        int finalExpectedResult = coins.size() - 1;

        for(Coin coin : coins) {
            int index = coins.indexOf(coin);
            when(screen.getDisplay()).thenReturn(expectedResults.get(index));

            vendingMachine.insertCoin(coin);
            actualResult = vendingMachine.checkDisplay();
        }

        verify(transaction, times(2)).updateTotal(any(Double.class));
        verify(screen, times(2)).updateDisplay(transaction);
        assertEquals(expectedResults.get(finalExpectedResult), actualResult);
    }

    @Test
    public void WhenSufficientMoneyAndProductSelectedThenDisplaysThankYouAndMoneyIsDepositedInBankAndTransactionReset() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        String initialExpectedResult = Screen.THANK_YOU;
        expectedResult = Screen.INSERT_COIN;

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(true);
        when(product.hasInventory()).thenReturn(true);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(initialExpectedResult);
        String initialActualResult = vendingMachine.checkDisplay();
        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank).depositMoney(transaction, product);
        verify(transaction).clear();
        verify(screen, times(2)).getDisplay();
        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenInsufficientMoneyAndProductSelectedThenDisplaysPriceAndMoneyIsNotDeposited() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        expectedResult = Screen.PRICE + ": $1.00";

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(false);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank, never()).depositMoney(transaction, product);
        verify(transaction, never()).clear();
        verify(screen).getDisplay();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void AfterPriceIsDisplayedDueToInsufficientFundsThenScreenDisplaysCurrentTransactionTotal() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        String initialExpectedResult = Screen.PRICE + ": $1.00";
        expectedResult = "$0.75";

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(false);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(initialExpectedResult);
        String initialActualResult = vendingMachine.checkDisplay();
        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank, never()).depositMoney(transaction, product);
        verify(transaction, never()).clear();
        verify(screen, times(2)).getDisplay();
        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerSelectsAProductWithNoMoneyInsertedThenProductPriceIsDisplayedAndThenInsertCoinIsDisplayed() {
        String initialExpectedResult = Screen.PRICE + ": $1.00";
        expectedResult = Screen.INSERT_COIN;

        when(buttons.press(product, transaction)).thenReturn(false);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(initialExpectedResult);
        String initialActualResult = vendingMachine.checkDisplay();
        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank, never()).depositMoney(transaction, product);
        verify(transaction, never()).clear();
        verify(screen, times(2)).getDisplay();
        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenProductIsSelectedThatCostsLessThanTransactionTotalThenRemainingTotalAfterPurchaseIsPlacedInCoinReturn() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 5;
        Double transactionFunds = 1.25;
        Double productCost = 1.00;
        Double extraFunds = transactionFunds - productCost;
        Double expected = 0.25;

        when(transaction.getTotal()).thenReturn(extraFunds);

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(true);
        when(product.hasInventory()).thenReturn(true);
        vendingMachine.pressButton(product);

        verify(bank).depositMoney(transaction, product);
        verify(coinReturn).updateTotal(extraFunds);
        verify(transaction).clear();
        assertEquals(expected, extraFunds);
    }

    @Test
    public void WhenCoinReturnButtonIsPressedThenTransactionTotalIsSentToCoinReturnAndScreenDisplaysInsertCoin() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        expectedResult = Screen.INSERT_COIN;

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        vendingMachine.returnCoins();

        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(coinReturn).updateTotal(any(Double.class));
        verify(transaction).clear();
        verify(screen).getDisplay();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenProductIsSelectedThatIsSoldOutThenDisplaysSoldOutAndThenTransactionTotalIsDisplayed() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 4;
        String initialExpectedResult = Screen.SOLD_OUT;
        expectedResult = "$1.00";

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(true);
        when(product.hasInventory()).thenReturn(false);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(initialExpectedResult);
        String initialActualResult = vendingMachine.checkDisplay();
        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank, never()).depositMoney(transaction, product);
        verify(transaction, never()).clear();
        verify(screen, times(2)).getDisplay();
        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenProductIsSelectedThatIsSoldOutAndTransactionTotalIsEmptyThenScreenDisplaysSoldOutAndThenInsertCoin() {
        String initialExpectedResult = Screen.SOLD_OUT;
        expectedResult = Screen.INSERT_COIN;

        when(buttons.press(product, transaction)).thenReturn(false);
        when(product.hasInventory()).thenReturn(false);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(initialExpectedResult);
        String initialActualResult = vendingMachine.checkDisplay();
        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank, never()).depositMoney(transaction, product);
        verify(transaction, never()).clear();
        verify(screen, times(2)).getDisplay();
        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenVendingMachineIsNotAbleToMakeExactChangeForAnyProductThenExactChangeOnlyIsDisplayedInsteadOfInsertCoin() {
        expectedResult = Screen.EXACT_CHANGE;

        when(bank.hasSufficientChange(inventory)).thenReturn(false);
        VendingMachine newMachine = new VendingMachine(bank, inventory);

        when(screen.getDisplay()).thenReturn(Screen.EXACT_CHANGE);
        actualResult = vendingMachine.checkDisplay();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenVendingMachineIsNotAbleToMakeExactChangeThenCustomerDepositsCoinsAndNoLongerNeedsExactChange() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 4;
        expectedResult = Screen.EXACT_CHANGE;

        when(bank.hasSufficientChange(inventory)).thenReturn(false);
        when(screen.getDisplay()).thenReturn(Screen.EXACT_CHANGE);
        VendingMachine newMachine = new VendingMachine(bank, inventory);

        actualResult = vendingMachine.checkDisplay();
        assertEquals(expectedResult, actualResult);

        for(int i=0;i<numberOfCoins;i++) {
            newMachine.insertCoin(coin);
        }

        expectedResult = Screen.THANK_YOU;
        when(bank.hasSufficientChange(inventory)).thenReturn(true);
        when(screen.getDisplay()).thenReturn(Screen.THANK_YOU);

        newMachine.pressButton(product);
        actualResult = vendingMachine.checkDisplay();
        assertEquals(expectedResult, actualResult);

        expectedResult = Screen.INSERT_COIN;
        when(screen.getDisplay()).thenReturn(Screen.INSERT_COIN);

        actualResult = vendingMachine.checkDisplay();
        assertEquals(expectedResult, actualResult);
    }
}
