import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ButtonPanelTest {
    private ButtonPanel buttons;
    private Transaction transaction;
    private Product product;
    private Boolean expectedResult;
    private Boolean actualResult;
    private Coin nickel;
    private Coin dime;
    private Coin quarter;

    @Before
    public void setUp() {
        buttons = new ButtonPanel();
        transaction = new Transaction();
        nickel = new Coin(CommonTestConstants.NICKEL_MASS, CommonTestConstants.NICKEL_DIAMETER, CommonTestConstants.NICKEL_VALUE);
        dime = new Coin(CommonTestConstants.DIME_MASS, CommonTestConstants.DIME_DIAMETER, CommonTestConstants.DIME_VALUE);
        quarter = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER, CommonTestConstants.QUARTER_VALUE);
    }

    @Test
    public void WhenTransactionHasJustEnoughCoinsToBuyProductThenPressingButtonReturnsTrue() {
        expectedResult = true;
        product = new Product("Cola", 1.00, 1);

        TestUtil.addCoins(4, quarter, transaction);

        actualResult = buttons.press(product, transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenTransactionHasMoreThanEnoughCoinsToBuyProductThenPressingButtonReturnsTrue() {
        expectedResult = true;
        product = new Product("Cola", 1.00, 1);

        TestUtil.addCoins(8, quarter, transaction);

        actualResult = buttons.press(product, transaction);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenTransactionDoesNotHaveEnoughCoinsToBuyProductThenPressingButtonReturnsFalse() {
        expectedResult = false;
        product = new Product("Cola", 1.00, 1);
        TestUtil.addCoins(3, quarter, transaction);
        TestUtil.addCoins(2, dime, transaction);

        actualResult = buttons.press(product, transaction);

        assertEquals(expectedResult, actualResult);
    }
}
