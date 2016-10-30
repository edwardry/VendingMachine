import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.mock.Mock;
import org.unitils.mock.core.MockObject;

import static org.junit.Assert.assertEquals;

public class VendingMachineTest extends UnitilsJUnit4 {
    private VendingMachine vendingMachine;
    private Mock<VendingMachineBank> mockBank;
    private Double coin;
    private String expectedResult = "";
    private String actualResult = "";

    @Before
    public void setUp() {
        mockBank = new MockObject<>(VendingMachineBank.class, this);
        vendingMachine = new VendingMachine();
        vendingMachine.setBank(mockBank.getMock());
    }

    @Test
    public void WhenCustomerInsertsAValidCoinAsFirstTransactionInBankThenValueOfCoinIsDisplayedOnScreen() {
        coin = .05;
        expectedResult = "$0.05";

        mockBank.returns(expectedResult).depositMoney(coin);
        mockBank.returns(true).isMoneyValid(coin);

        actualResult = vendingMachine.insertCoin(coin);

        mockBank.assertInvoked().depositMoney(coin);
        mockBank.assertInvoked().isMoneyValid(coin);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsAnInvalidCoinInBankThenScreenDisplaysInsertCoinAndReturnsCoinToTray() {
        coin = .01;
        expectedResult = "INSERT COIN";
        mockBank.returns(expectedResult).depositMoney(coin);
        mockBank.returns(false).isMoneyValid(coin);

        actualResult = vendingMachine.insertCoin(coin);

        mockBank.assertInvoked().isMoneyValid(coin);
        assertEquals(expectedResult, actualResult);
    }
}
