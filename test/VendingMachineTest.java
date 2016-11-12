import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendingMachineTest {
    @InjectMocks private VendingMachine vendingMachine;
    @Mock private VendingMachineBank bank;
    private Double coin;
    private String expectedResult = "";
    private String actualResult = "";

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
        MockitoAnnotations.initMocks(this);
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
}
