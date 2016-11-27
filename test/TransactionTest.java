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

        transaction.update(CommonTestConstants.quarter);
        actualResult = CoinUtil.getTotal(transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenANonEmptyTransactionHasItsTotalUpdatedTheNewTotalIsTheSumOfThePreviousTotalPlusTheNewlyPassedValue() {
        expectedResult = 1.00;

        TestUtil.addCoins(3, CommonTestConstants.quarter, transaction);
        TestUtil.addCoins(2, CommonTestConstants.dime, transaction);
        transaction.update(CommonTestConstants.nickel);
        actualResult = CoinUtil.getTotal(transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenANonEmptyTransactionIsClearedThenTheNewTotalIsZero() {
        expectedResult = 0.0;
        TestUtil.addCoins(3, CommonTestConstants.quarter, transaction);
        TestUtil.addCoins(2, CommonTestConstants.dime, transaction);
        transaction.clear();
        actualResult = CoinUtil.getTotal(transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenAnEmptyTransactionIsClearedThenTheTotalRemainsZero() {
        expectedResult = CoinUtil.getTotal(transaction);
        transaction.clear();
        actualResult = CoinUtil.getTotal(transaction);

        assertEquals(expectedResult, actualResult);
    }
}
