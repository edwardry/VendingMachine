import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {
    private Product product;
    private Boolean expectedResult;
    private Boolean actualResult;

    @Test
    public void WhenProductHasInventoryThenTrueIsReturned() {
        expectedResult = true;
        product = new Product("Cola", 1.00, 1);

        actualResult = product.hasInventory();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenProductDoesNotHaveInventoryThenFalseIsReturned() {
        expectedResult = false;
        product = new Product("Cola", 1.00, 0);

        actualResult = product.hasInventory();

        assertEquals(expectedResult, actualResult);
    }
}
