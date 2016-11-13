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
    private Double coin;
    private String expectedResult = "";
    private String actualResult = "";
    private List<Double> coins;
    private List<String> expectedResults;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
        MockitoAnnotations.initMocks(this);
        coins = new ArrayList<>();
        expectedResults = new ArrayList<>();
    }

    @Test
    public void WhenCustomerInsertsAValidCoinAsFirstTransactionInBankThenValueOfCoinIsDisplayedOnScreen() {
        coin = .05;
        expectedResult = "$0.05";

        when(bank.isMoneyValid(coin)).thenReturn(true);
        when(screen.getDisplay()).thenReturn(expectedResult);

        vendingMachine.insertCoin(coin);
        actualResult = vendingMachine.checkDisplay();

        verify(bank).isMoneyValid(coin);
        verify(transaction).updateTotal(coin);
        verify(screen).updateDisplay(transaction);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsAnInvalidCoinInBankThenScreenDisplaysInsertCoin() {
        coin = .01;
        expectedResult = Screen.INSERT_COIN;

        when(bank.isMoneyValid(coin)).thenReturn(false);
        when(screen.getDisplay()).thenReturn(expectedResult);

        vendingMachine.insertCoin(coin);
        actualResult = vendingMachine.checkDisplay();

        verify(bank).isMoneyValid(coin);
        verify(screen).updateDisplay(expectedResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsTwoValidCoinsInBankThenScreenDisplaysSumOfCoinValue() {
        coins.add(0, .05);
        expectedResults.add(0, "$0.05");
        coins.add(1, .10);
        expectedResults.add(1, "$0.15");
        int finalExpectedResult = coins.size() - 1;

        for(Double coin : coins) {
            int index = coins.indexOf(coin);
            when(screen.getDisplay()).thenReturn(expectedResults.get(index));
            when(bank.isMoneyValid(coin)).thenReturn(true);

            vendingMachine.insertCoin(coin);
            actualResult = vendingMachine.checkDisplay();

            verify(bank).isMoneyValid(coin);
            verify(transaction).updateTotal(coin);
        }

        verify(screen, times(2)).updateDisplay(transaction);
        assertEquals(expectedResults.get(finalExpectedResult), actualResult);
    }

    @Test
    public void WhenCustomerHasInsertedEnoughMoneyAndSelectsAProductThenScreenDisplaysThankYouAndMoneyIsDepositedInBank() {
        coin = .25;
        int numberOfCoins = 3;
        expectedResult = Screen.THANK_YOU;

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(true);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank).depositMoney(transaction);
        verify(screen).getDisplay();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenTheScreenIsCheckedAgainAfterPurchaseOfProductThenTheScreenDisplaysInsertCoin() {
        coin = .25;
        int numberOfCoins = 3;
        String initialExpectedResult = Screen.THANK_YOU;
        expectedResult = Screen.INSERT_COIN;

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(true);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(initialExpectedResult);
        String initialActualResult = vendingMachine.checkDisplay();
        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank).depositMoney(transaction);
        verify(screen, times(2)).getDisplay();
        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerHasNotInsertedEnoughMoneyAndSelectsAProductThenScreenDisplaysPriceAndMoneyIsNotDepositedInBank() {
        coin = .25;
        int numberOfCoins = 3;
        expectedResult = Screen.PRICE;

        for(int i=0;i<numberOfCoins;i++) {
            vendingMachine.insertCoin(coin);
        }

        when(buttons.press(product, transaction)).thenReturn(false);
        vendingMachine.pressButton(product);

        when(screen.getDisplay()).thenReturn(expectedResult);
        actualResult = vendingMachine.checkDisplay();

        verify(bank, never()).depositMoney(transaction);
        verify(screen).getDisplay();
        assertEquals(expectedResult, actualResult);
    }
}
