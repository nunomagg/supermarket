package tests;

import discountRules.BuyXGetFreeUnits;
import org.junit.jupiter.api.Test;
import run.Utils;
import supermarket.Item;
import supermarket.ShoppingCart;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Nuno Maggiolly on 09-01-2017.
 */
public class BuyXGetFreeUnitsTest extends SuperMarketTest{

    /**
     * buy 3 (equals) items and pay for 2
     */
    @Test
    public void buyThreeEqualPayTwo(){

        //buy 3 get 1 for free
        market.addDiscount(new BuyXGetFreeUnits(BANANA,3,1));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(BANANA,4);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (2,itemsInCart.size(),"There should be 2 'items' banana in cart ");

        Item fullPricedItem = null;

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (BANANA.equals(item.getProduct()) && item.calculatePrice() == 0.0){
                assertEquals (1,item.getQuantity(),"There should be 1 banana for free in the cart ");
            }
            else if (BANANA.equals(item.getProduct()) && item.calculatePrice() > 0.0){
                fullPricedItem = item;
                assertEquals (3,item.getQuantity(),"There should be 2 full priced bananas");
            }
        }

        float finalPrice =  fullPricedItem.getQuantity() * fullPricedItem.getProduct().getPrice();

        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong full price");

        ShoppingCartTests.testProductsQuantities(itemsInCart);
        market.printCart(itemsInCart);
    }


    /**
     * This test will have the buy X get Free units rule applied to 2 different items
     *      - Bananas: Buy 5 get 2 for free
     *      - Apples: Buy 6 get 3 for free
     *
     *  Using the following quantities:
     *       banana = 4;
     *       strawberry = 7;
     *       apple = 13;
     *
     *  This will result in:
     *      - 4  Bananas Full price: Same quantity , since there aren't enough bananas to apply the rule
     *      - 7 Strawberries Full price: Same quantity , since no rule is being applied to this product
     *      - 7 Full Price Apples: (12 apples used in the discount, where 6 are full price) + 1 not used in discount
     *      - 6 Free Apples: ( 13 / 6 = (int) 2,16 = 2 ) * 3 for free
     */
    @Test
    public void MultipleFreeUnitDiscounts(){

        int BANANA_QUANTITY = 4;
        int STRAWBERRY_QUANTITY = 7;
        int APPLE_QUANTITY = 13;
        int APPLE_RULE_BUY_UNITS = 6;
        int APPLE_RULE_FREE_UNITS = 3;

        float EXPECTED_FREE_APPLES = APPLE_RULE_FREE_UNITS * Math.floorDiv(APPLE_QUANTITY, APPLE_RULE_BUY_UNITS);
        int EXPECTED_FULL_PRICE_APPLES = Utils.getIntOfRoundedValue(APPLE_QUANTITY * (APPLE_RULE_FREE_UNITS / EXPECTED_FREE_APPLES));

        //buy 3 get 1 for free
        market.addDiscount(new BuyXGetFreeUnits(BANANA,5,2));
        //buy 6 get 1 for free
        market.addDiscount(new BuyXGetFreeUnits(APPLE,APPLE_RULE_BUY_UNITS,APPLE_RULE_FREE_UNITS));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(BANANA, BANANA_QUANTITY);
        cart.addToCart(STRAWBERRY, STRAWBERRY_QUANTITY);
        cart.addToCart(APPLE, APPLE_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        // The 3 items + the free apple item
        assertEquals (4,itemsInCart.size(),"There should be 4 'items' ");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (BANANA.equals(item.getProduct())){
                assertEquals (BANANA_QUANTITY,item.getQuantity(),"There should only be "+BANANA_QUANTITY+" BANANAS in the cart ");
            }
            else if (STRAWBERRY.equals(item.getProduct())){
                assertEquals (STRAWBERRY_QUANTITY,item.getQuantity(),"There should only be "+STRAWBERRY_QUANTITY+" STRAWBERRY in the cart ");
            }
            else if (APPLE.equals(item.getProduct())){
                if (item.calculatePrice() > 0.0) {
                    assertEquals(EXPECTED_FULL_PRICE_APPLES, item.getQuantity(), "There should only be " + EXPECTED_FULL_PRICE_APPLES + " APPLE in the cart ");
                }else {
                    assertEquals(EXPECTED_FREE_APPLES, item.getQuantity(), "There should only be " + EXPECTED_FREE_APPLES + " free apples in the cart ");
                }
            }
        }

        float finalPrice =  BANANA.getPrice() *  BANANA_QUANTITY +
                            STRAWBERRY.getPrice() * STRAWBERRY_QUANTITY +
                            APPLE.getPrice() * EXPECTED_FULL_PRICE_APPLES;

        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong full price");

        ShoppingCartTests.testProductsQuantities(itemsInCart);
        market.printCart(itemsInCart);
    }


    /**
     * buy 3 (equals) items and pay for 2,  applied to STRAWBERRY products but the cart only contains Bananas
     *
     */
    @Test
    public void getFreeUnitsRuleNotApplied(){

        int BANANA_QUANTITY = 6;

        //buy 3 get 1 for free
        market.addDiscount(new BuyXGetFreeUnits(STRAWBERRY,3,1));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(BANANA,BANANA_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (1,itemsInCart.size(),"There should be 1 item banana in cart ");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (BANANA.equals(item.getProduct())){
                assertEquals (BANANA_QUANTITY,item.getQuantity(),"There should be "+BANANA_QUANTITY+"quantities of banana in the cart ");
            }
            assertTrue(BANANA.equals(item.getProduct()), "There should only be bananas in the cart");
        }

        float finalPrice =  BANANA_QUANTITY * BANANA.getPrice();

        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong full price");

        ShoppingCartTests.testProductsQuantities(itemsInCart);
        market.printCart(itemsInCart);
    }
}
