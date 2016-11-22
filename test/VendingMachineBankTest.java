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

    @Test
    public void WhenDetermineCoinValueIsPassedACoinWithDimeWeightAndDiameterReturnsValueOfDime() {
        coin = new Coin(CommonTestConstants.DIME_MASS, CommonTestConstants.DIME_DIAMETER);
        expectedResult = 0.10;

        actualResult = VendingMachineBank.determineValue(coin);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDetermineCoinValueIsPassedACoinWithQuarterWeightAndDiameterReturnsValueOfQuarter() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        expectedResult = 0.25;

        actualResult = VendingMachineBank.determineValue(coin);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenDetermineCoinValueIsPassedAnInvalidCoinThenReturnsValueOfZero() {
        coin = new Coin(2.5, 19.05);
        expectedResult = 0.0;

        actualResult = VendingMachineBank.determineValue(coin);

        assertEquals(expectedResult, actualResult);
    }
}
