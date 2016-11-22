import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    private Transaction transaction;
    private Double expectedResult;
    private Double actualResult;

    @Before
    public void setUp() {
        transaction = new Transaction();
    }

    @Test
    public void WhenANewTransactionHasItsTotalUpdatedTheNewTotalIsTheValueThatWasPassed() {
        expectedResult = 0.25;

        transaction.updateTotal(0.25);
        actualResult = transaction.getTotal();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenANonEmptyTransactionHasItsTotalUpdatedTheNewTotalIsTheSumOfThePreviousTotalPlusTheNewlyPassedValue() {
        expectedResult = 1.00;

        transaction.updateTotal(0.95);

        transaction.updateTotal(0.05);
        actualResult = transaction.getTotal();

        assertEquals(expectedResult, actualResult);
    }
}
