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
        actualResult = vendingMachine.insertCoin(coin);
        mockBank.assertInvoked().depositMoney(coin);
        assertEquals(expectedResult, actualResult);
    }
}
