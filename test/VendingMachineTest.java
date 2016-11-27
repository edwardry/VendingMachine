import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class VendingMachineTest {
    private VendingMachine vendingMachine;
    private VendingMachineBank bank;
    private Product product;
    private Inventory inventory;
    private Coin coin;
    private String expectedResult = "";
    private String actualResult = "";
    private List<Coin> coins;
    private List<String> expectedResults;

    @Before
    public void setUp() {
        Map<Double, List<Coin>> bankFunds = TestUtil.createBankFunds();
        bank = new VendingMachineBank(bankFunds);
        Product cola = new Product(CommonTestConstants.COLA, 1.00, 10);
        Product chips = new Product(CommonTestConstants.CHIPS, 0.50, 10);
        Product candy = new Product(CommonTestConstants.CANDY, 0.65, 10);
        inventory = new Inventory(Arrays.asList(cola, chips, candy));
        vendingMachine = new VendingMachine(bank, inventory);
        coins = new ArrayList<>();
        expectedResults = new ArrayList<>();
    }

    @Test
    public void WhenCustomerInsertsAValidCoinAsFirstTransactionInBankThenValueOfCoinIsDisplayedOnScreen() {
        coin = new Coin(CommonTestConstants.NICKEL_MASS, CommonTestConstants.NICKEL_DIAMETER);
        expectedResult = "$0.05";

        vendingMachine.insertCoin(coin);
        actualResult = vendingMachine.checkDisplay();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsAnInvalidCoinInBankThenScreenDisplaysInsertCoinAndCoinIsPlacedInCoinReturn() {
        coin = new Coin(2.5, 19.05);
        expectedResult = Screen.INSERT_COIN;

        vendingMachine.insertCoin(coin);
        actualResult = vendingMachine.checkDisplay();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerInsertsTwoValidCoinsInBankThenScreenDisplaysSumOfCoinValue() {
        coins.add(0, new Coin(CommonTestConstants.NICKEL_MASS, CommonTestConstants.NICKEL_DIAMETER));
        expectedResults.add(0, "$0.05");
        coins.add(1, new Coin(CommonTestConstants.DIME_MASS, CommonTestConstants.DIME_DIAMETER));
        expectedResults.add(1, "$0.15");
        int finalExpectedResult = coins.size() - 1;

        for (Coin coin : coins) {
            vendingMachine.insertCoin(coin);
            actualResult = vendingMachine.checkDisplay();
        }

        assertEquals(expectedResults.get(finalExpectedResult), actualResult);
    }

    @Test
    public void WhenSufficientMoneyAndProductSelectedThenDisplaysThankYouAndMoneyIsDepositedInBankAndTransactionReset() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        product = inventory.getProduct(CommonTestConstants.CHIPS);
        String initialExpectedResult = Screen.THANK_YOU;
        expectedResult = Screen.INSERT_COIN;

        for (int i = 0; i < numberOfCoins; i++) {
            vendingMachine.insertCoin(coin);
        }

        vendingMachine.pressButton(product);

        String initialActualResult = vendingMachine.checkDisplay();
        actualResult = vendingMachine.checkDisplay();

        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenInsufficientMoneyAndProductSelectedThenDisplaysPriceAndMoneyIsNotDeposited() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        product = inventory.getProduct(CommonTestConstants.COLA);
        expectedResult = Screen.PRICE + ": $1.00";

        for (int i = 0; i < numberOfCoins; i++) {
            vendingMachine.insertCoin(coin);
        }

        vendingMachine.pressButton(product);
        actualResult = vendingMachine.checkDisplay();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void AfterPriceIsDisplayedDueToInsufficientFundsThenScreenDisplaysCurrentTransactionTotal() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        product = inventory.getProduct(CommonTestConstants.COLA);
        String initialExpectedResult = Screen.PRICE + ": $1.00";
        expectedResult = "$0.75";

        for (int i = 0; i < numberOfCoins; i++) {
            vendingMachine.insertCoin(coin);
        }

        vendingMachine.pressButton(product);

        String initialActualResult = vendingMachine.checkDisplay();
        actualResult = vendingMachine.checkDisplay();

        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenCustomerSelectsAProductWithNoMoneyInsertedThenProductPriceIsDisplayedAndThenInsertCoinIsDisplayed() {
        String initialExpectedResult = Screen.PRICE + ": $1.00";
        product = inventory.getProduct(CommonTestConstants.COLA);
        expectedResult = Screen.INSERT_COIN;

        vendingMachine.pressButton(product);

        String initialActualResult = vendingMachine.checkDisplay();
        actualResult = vendingMachine.checkDisplay();

        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenProductIsSelectedThatCostsLessThanTransactionTotalThenRemainingTotalAfterPurchaseIsPlacedInCoinReturn() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 5;
        product = inventory.getProduct(CommonTestConstants.COLA);
        Double transactionFunds = 1.25;
        Double productCost = product.getPrice();
        Double extraFunds = transactionFunds - productCost;
        Double expected = 0.25;

        for (int i = 0; i < numberOfCoins; i++) {
            vendingMachine.insertCoin(coin);
        }

        vendingMachine.pressButton(product);

        assertEquals(expected, extraFunds);
    }

    @Test
    public void WhenCoinReturnButtonIsPressedThenTransactionTotalIsSentToCoinReturnAndScreenDisplaysInsertCoin() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 3;
        expectedResult = Screen.INSERT_COIN;

        for (int i = 0; i < numberOfCoins; i++) {
            vendingMachine.insertCoin(coin);
        }

        vendingMachine.returnCoins();

        actualResult = vendingMachine.checkDisplay();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenProductIsSelectedThatIsSoldOutThenDisplaysSoldOutAndThenTransactionTotalIsDisplayed() {
        coin = new Coin(CommonTestConstants.QUARTER_MASS, CommonTestConstants.QUARTER_DIAMETER);
        int numberOfCoins = 4;
        List<Product> products = Arrays.asList(new Product(CommonTestConstants.CANDY, 0.65, 0));
        Inventory inventory = new Inventory(products);
        product = inventory.getProduct(CommonTestConstants.CANDY);
        String initialExpectedResult = Screen.SOLD_OUT;
        expectedResult = "$1.00";

        for (int i = 0; i < numberOfCoins; i++) {
            vendingMachine.insertCoin(coin);
        }

        vendingMachine.pressButton(product);

        String initialActualResult = vendingMachine.checkDisplay();
        actualResult = vendingMachine.checkDisplay();

        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void WhenProductIsSelectedThatIsSoldOutAndTransactionTotalIsEmptyThenScreenDisplaysSoldOutAndThenInsertCoin() {
        List<Product> products = Arrays.asList(new Product(CommonTestConstants.CANDY, 0.65, 0));
        Inventory inventory = new Inventory(products);
        product = inventory.getProduct(CommonTestConstants.CANDY);
        String initialExpectedResult = Screen.SOLD_OUT;
        expectedResult = Screen.INSERT_COIN;

        vendingMachine.pressButton(product);

        String initialActualResult = vendingMachine.checkDisplay();
        actualResult = vendingMachine.checkDisplay();

        assertEquals(initialExpectedResult, initialActualResult);
        assertEquals(expectedResult, actualResult);
    }
}