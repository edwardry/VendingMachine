import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendingMachineTest {
    @InjectMocks
    private VendingMachine vendingMachine;
    @Mock
    private VendingMachineBank bank;
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

        when(bank.depositMoney(coin)).thenReturn(expectedResult);
        when(bank.isMoneyValid(coin)).thenReturn(true);

        actualResult = vendingMachine.insertCoin(coin);

        verify(bank).depositMoney(coin);
        verify(bank).isMoneyValid(coin);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsAnInvalidCoinInBankThenScreenDisplaysInsertCoinAndReturnsCoinToTray() {
        coin = .01;
        expectedResult = "INSERT COIN";
        when(bank.depositMoney(coin)).thenReturn(expectedResult);
        when(bank.isMoneyValid(coin)).thenReturn(false);

        actualResult = vendingMachine.insertCoin(coin);

        verify(bank).isMoneyValid(coin);
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
            when(bank.depositMoney(coin)).thenReturn(expectedResults.get(index));
            when(bank.isMoneyValid(coin)).thenReturn(true);

            actualResult = vendingMachine.insertCoin(coin);

            verify(bank).depositMoney(coin);
            verify(bank).isMoneyValid(coin);
        }

        assertEquals(expectedResults.get(finalExpectedResult), actualResult);
    }
}
