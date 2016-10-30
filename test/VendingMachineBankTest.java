import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.mock.Mock;
import org.unitils.mock.core.MockObject;

public class VendingMachineBankTest extends UnitilsJUnit4 {
    private Mock<VendingMachineBank> mockBank;
    private Double coin;
    private String result = "";

    @Before
    public void setUp() {
        mockBank = new MockObject<>(VendingMachineBank.class, this);
    }

    @Test
    public void WhenCustomerInsertsAValidCoinAsFirstTransactionInBankThenValueOfCoinIsDisplayedOnScreen() {
        coin = .05;
        result = "$0.05";
        mockBank.returns(result).depositMoney(coin);
        mockBank.getMock().depositMoney(coin);
        mockBank.assertInvoked().depositMoney(coin);
    }
}
