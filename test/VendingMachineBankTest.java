import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendingMachineBankTest {
    private Coin coin;
    private Double expectedResult;
    private Double actualResult;

    @Test
    public void WhenDetermineCoinValueIsPassedACoinWithNickelWeightAndDiameterReturnsValueOfNickel() {
        coin = new Coin(CommonTestConstants.NICKEL_MASS, CommonTestConstants.NICKEL_DIAMETER);
        expectedResult = 0.05;

        actualResult = VendingMachineBank.determineValue(coin);

        assertEquals(expectedResult, actualResult);
    }
}
