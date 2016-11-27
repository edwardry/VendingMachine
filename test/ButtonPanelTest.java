import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ButtonPanelTest {
    private ButtonPanel buttons;
    private Transaction transaction;
    private Product product;
    private Boolean expectedResult;
    private Boolean actualResult;

    @Before
    public void setUp() {
        buttons = new ButtonPanel();
        transaction = new Transaction();
    }

    @Test
    public void WhenTransactionHasJustEnoughCoinsToBuyProductThenPressingButtonReturnsTrue() {
        expectedResult = true;
        product = new Product("Cola", 1.00, 1);
        transaction.updateTotal(1.00);

        actualResult = buttons.press(product, transaction);

        assertEquals(expectedResult, actualResult);
    }
}
