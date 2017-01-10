package tests;

import discountRules.BuySetDontPayCheapest;
import discountRules.BuyXForSpecialPrice;
import discountRules.BuyXGetFreeUnits;
import discountRules.BuyXGetYOtherItemsForFree;
import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;
import supermarket.SuperMarket;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Nuno Maggiolly on 09-01-2017.
 */
public class MultipleDifferentRulesTest extends SuperMarketTest {

    /*
     *    Rule 1 - buy 3 (equals) items and pay for 2
     *    Rule 2 - buy 2 (equals) items for a special price
     *    Rule 3 - buy 3 (in a set of items) and the cheapest is free
     *    Rule 4 - for each N (equals) items X, you get K items Y for free
     *
     * */


    /**
     * This method tests the application of 2 rules against the same type of product
     * In this case:
     *      - Buy 3 Bananas get 2 For Free
     *      - Buy 2 Bananas and 1 is Half price
     *
     *  We will start with 8 Bananas. At the end we should have
     *      5 - Bananas Full Priced
     *      2 - Bananas For Free
     *      1 - Banana Half Price
     *
     */
    @Test
    public void BananasWithRule1And2(){
        SuperMarket market = getSuperMarketWithProducts();

        final int BANANA_QUANTITY = 8;
        final int RESULT_FREE_BANANAS = 2;
        final int RESULT_HALF_PRICE_BANANAS = 1;
        final int RESULT_FULL_PRICE_BANANAS = 5;

        market.addDiscount(new BuyXGetFreeUnits(BANANA,3,1));
        market.addDiscount(new BuyXForSpecialPrice(BANANA,2,1,50));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(BANANA, BANANA_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (3,itemsInCart.size(),"There should be 3 'item' Banana cart ");


        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (BANANA.equals(item.getProduct()) && item.calculatePrice() == 0.0){
                assertEquals(BANANA,item.getProduct(), "This product should be a "+BANANA.getName());
                assertEquals (RESULT_FREE_BANANAS,item.getQuantity(),
                        "There should be "+RESULT_FREE_BANANAS+" of "+BANANA.getName()+" for free in the cart ");
            }
            else if (BANANA.equals(item.getProduct()) && item.hasDiscount()){
                assertEquals (RESULT_HALF_PRICE_BANANAS,item.getQuantity(),"There should be "+RESULT_HALF_PRICE_BANANAS+" of half priced "+BANANA.getName());
            }
            else {
                assertEquals (RESULT_FULL_PRICE_BANANAS,item.getQuantity(),"There should be "+RESULT_FULL_PRICE_BANANAS+" of full priced "+BANANA.getName());
            }
        }

        float finalPrice = (RESULT_HALF_PRICE_BANANAS * BANANA.getPrice())/2 + RESULT_FULL_PRICE_BANANAS * BANANA.getPrice() ;

        assertEquals(11.0, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        ShoppingCartTests.testProductsQuantities(itemsInCart);
        market.printCart(itemsInCart);
    }

    /**
     * This method tests the application of 2 rules against the same type of product
     * In this case: (the inverse of the previous test)
     *      - Buy 2 Bananas and 1 is Half price
     *      - Buy 3 Bananas get 2 For Free
     *
     *  We will start with 8 Bananas. At the end we should have
     *      4 - Bananas at Full Price
     *      4 - Banana at Half Price
     *
     */
    @Test
    public void BananasWithRule2And1(){
        SuperMarket market = getSuperMarketWithProducts();

        final int BANANA_QUANTITY = 8;

        final int RESULT_FREE_BANANAS = 0;
        final int RESULT_HALF_PRICE_BANANAS = BANANA_QUANTITY/2;
        final int RESULT_FULL_PRICE_BANANAS = BANANA_QUANTITY/2;

        market.addDiscount(new BuyXForSpecialPrice(BANANA,2,1,50));
        market.addDiscount(new BuyXGetFreeUnits(BANANA,3,1));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(BANANA, BANANA_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (2,itemsInCart.size(),"There should be 3 'item' Banana cart ");


        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (BANANA.equals(item.getProduct()) && item.calculatePrice() == 0.0){
                assertEquals(BANANA,item.getProduct(), "This product should be a "+BANANA.getName());
                assertEquals (RESULT_FREE_BANANAS,item.getQuantity(),
                        "There should be "+RESULT_FREE_BANANAS+" of "+BANANA.getName()+" for free in the cart ");
            }
            else if (BANANA.equals(item.getProduct()) && item.hasDiscount()){
                assertEquals (RESULT_HALF_PRICE_BANANAS,item.getQuantity(),"There should be "+RESULT_HALF_PRICE_BANANAS+" of half priced "+BANANA.getName());
            }
            else {
                assertEquals (RESULT_FULL_PRICE_BANANAS,item.getQuantity(),"There should be "+RESULT_FULL_PRICE_BANANAS+" of full priced "+BANANA.getName());
            }
        }

        float finalPrice = (RESULT_HALF_PRICE_BANANAS * BANANA.getPrice())/2 + RESULT_FULL_PRICE_BANANAS * BANANA.getPrice() ;


        assertEquals(12.0, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        ShoppingCartTests.testProductsQuantities(itemsInCart);
        market.printCart(itemsInCart);
    }


    @Test
    public void TestAllRules(){

        int BANANA_QUANTITY = 3;
        int APRICOT_QUANTITY = 2;
        int APPLE_QUANTITY = 2;
        int PINEAPPLE_QUANTITY = 1;
        int BANANA_QUANTITY_2 = 5;
        int STRAWBERRY_QUANTITY = 7;
        int SUM_BANANA_QUANTITY = BANANA_QUANTITY + BANANA_QUANTITY_2;

        //Expected results
        int PEAR_FREE = 2;
        int BANANA_FREE = 2;
        int BANANA_HALF_PRICE = 1;
        int BANANA_FULL_PRICE = SUM_BANANA_QUANTITY - BANANA_FREE - BANANA_HALF_PRICE;
        int APRICOT_FULL_PRICE = 2;
        int APPLE_FULL_PRICE = APPLE_QUANTITY;
        int PINEAPPLE_FREE_PRICE = 1;
        int STRAWBERRY_FULL_PRICE = 5;
        int STRAWBERRY_HALF_PRICE = 2;


        //Set - cheapest is Apple
        Set<Product> first_set = new HashSet<>();
        first_set.add(APPLE);
        first_set.add(PINEAPPLE);
        first_set.add(BANANA);

        //Set - cheapest is pineapple
        Set<Product> sec_set = new HashSet<>();
        sec_set.add(STRAWBERRY);
        sec_set.add(PINEAPPLE);
        sec_set.add(APRICOT);

        //1 .Rule 1
        market.addDiscount(new BuyXGetFreeUnits(BANANA,3,1));
        //after this there should only be 2 available bananas to use in other discounts

        //2.
        market.addDiscount(new BuyXForSpecialPrice(BANANA,2,1,50));

        //3. Rule 2 - This rule wont be applied
        market.addDiscount(new BuyXForSpecialPrice(STRAWBERRY,3,1, 50));

        //4. Rule 3 - buy 3 (in a set of items) and the cheapest is free
        //- This rule wont be applied
        market.addDiscount(new BuySetDontPayCheapest(first_set));

        //5.
        market.addDiscount(new BuySetDontPayCheapest(sec_set));

        //6.
        market.addDiscount(new BuyXGetYOtherItemsForFree(APRICOT,1,PEAR,2));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(BANANA, BANANA_QUANTITY);
        cart.addToCart(APRICOT, APRICOT_QUANTITY);
        cart.addToCart(APPLE, APPLE_QUANTITY);
        cart.addToCart(PINEAPPLE, PINEAPPLE_QUANTITY);
        cart.addToCart(BANANA, BANANA_QUANTITY_2);
        cart.addToCart(STRAWBERRY, STRAWBERRY_QUANTITY);


        //market.checkoutCart(cart);

        List<Item> items = market.applyDiscount(cart);

        ShoppingCartTests.testProductsQuantities(items);

        market.printCart(items);
    }
}
