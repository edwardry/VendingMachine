import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScreenTest {
    private Screen screen;
    private Transaction transaction;
    private String expectedResult;
    private String actualResult;

    @Before
    public void setUp() {
        screen = new Screen();
        transaction = new Transaction();
        TestUtil.addCoins(5, CommonTestConstants.quarter, transaction);
    }

    @Test
    public void WhenScreenIsUpdatedWithTransactionInProgressThenSetScreenToCurrentTransactionTotal() {
        expectedResult = "$1.25";

        screen.updateDisplay(transaction);
        actualResult = screen.getDisplay();

        assertEquals(expectedResult, actualResult);
    }
}
