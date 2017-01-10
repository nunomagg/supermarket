package tests;

import discountRules.BuySetDontPayCheapest;
import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Nuno Maggiolly on 09-01-2017.
 */
public class BuySetDontPayCheapestTest extends SuperMarketTest
{
    /**
     * buy 3 (in a set of items) and the cheapest is free
     */
    @Test
    public void buy3FromSetCheapestIsFree(){

        // will be used WATERMELON, CHERRY and PINEAPPLE
        final int MIN_QUANTITY = 4; //cart must have an entry with the cheapest product with this quantity
        final int WATERMELON_QUANTITY = MIN_QUANTITY;
        final int CHERRY_QUANTITY = MIN_QUANTITY+1;
        final int PINEAPPLE_QUANTITY = MIN_QUANTITY+2;

        //Cheapest is cherry with price = 3
        Set<Product> applicable = new HashSet<>();
        applicable.add(CHERRY);
        applicable.add(WATERMELON);
        applicable.add(PINEAPPLE);

        market.addDiscount(new BuySetDontPayCheapest(applicable));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(WATERMELON, WATERMELON_QUANTITY);
        cart.addToCart(CHERRY, CHERRY_QUANTITY);
        cart.addToCart(PINEAPPLE, PINEAPPLE_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (4,itemsInCart.size(),"There should be 4 'items' cart ");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (CHERRY.equals(item.getProduct()) && item.calculatePrice() == 0.0){
                assertEquals(CHERRY,item.getProduct(), "This product should be a "+CHERRY.getName());
                assertEquals (MIN_QUANTITY,item.getQuantity(),
                        "There should be "+MIN_QUANTITY+" of "+CHERRY.getName()+" for free in the cart ");
            }
            else if (WATERMELON.equals(item.getProduct()) ){
                assertEquals (WATERMELON_QUANTITY,item.getQuantity(),
                        "There should be "+WATERMELON_QUANTITY+"of full priced watermelons");
            }
            else if (CHERRY.equals(item.getProduct()) ){
                assertEquals (CHERRY_QUANTITY-MIN_QUANTITY,item.getQuantity(),
                        "There should be "+(CHERRY_QUANTITY-MIN_QUANTITY)+"of full priced cherries");
            }
            else if (PINEAPPLE.equals(item.getProduct()) ){
                assertEquals (PINEAPPLE_QUANTITY,item.getQuantity(),
                        "There should be "+PINEAPPLE_QUANTITY+" of full priced pineapples");
            }
        }

        float finalPrice = (CHERRY_QUANTITY - MIN_QUANTITY) * CHERRY.getPrice() +
                PINEAPPLE_QUANTITY * PINEAPPLE.getPrice() +
                WATERMELON_QUANTITY * WATERMELON.getPrice();

        assertEquals(67.0, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        market.printCart(itemsInCart);
    }


    /**
     * In this test, 3 Items will be added to the discount (CHERRY, WATERMELON and PINEAPPLE)
     * But from those only CHERRY, WATERMELON will be in the cart
     *
     * Since one item of the rule is missing in the cart, the rule will not be applied
     *
     * The end result of the cart must contain, Cherry, Clementine and Watermelon with their initial quantities at full price
     */
    @Test
    public void buy3ItemsWhereOneIsNotFromSet(){

        final int MIN_QUANTITY = 4; //cart must have an entry with the cheapest product with this quantity
        final int WATERMELON_QUANTITY = MIN_QUANTITY;
        final int CHERRY_QUANTITY = MIN_QUANTITY+1;
        final int CLEMENTINE_QUANTITY = MIN_QUANTITY+5;

        //Cheapest is cherry with price = 3
        Set<Product> applicable = new HashSet<>();
        applicable.add(CHERRY);
        applicable.add(WATERMELON);
        applicable.add(PINEAPPLE);

        market.addDiscount(new BuySetDontPayCheapest(applicable));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(WATERMELON, WATERMELON_QUANTITY);
        cart.addToCart(CHERRY, CHERRY_QUANTITY);
        cart.addToCart(CLEMENTINE, CLEMENTINE_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (3,itemsInCart.size(),"There should be 3 items cart (WATERMELON, CHERRY and CLEMENTINE)");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (item.calculatePrice() == 0.0){
                assertEquals (MIN_QUANTITY,item.getQuantity(),
                        "There shouldn't be a free item in the cart ");
            }
            else if (WATERMELON.equals(item.getProduct()) ){
                assertEquals (WATERMELON_QUANTITY,item.getQuantity(),
                        "There should be "+WATERMELON_QUANTITY+"of full priced watermelons");
            }
            else if (CHERRY.equals(item.getProduct()) ){
                assertEquals (CHERRY_QUANTITY,item.getQuantity(),
                        "There should be "+CHERRY_QUANTITY+"of full priced cherries");
            }
            else if (CLEMENTINE.equals(item.getProduct()) ){
                assertEquals (CLEMENTINE_QUANTITY,item.getQuantity(),
                        "There should be "+CLEMENTINE_QUANTITY+" of full priced pineapples");
            }
        }

        float finalPrice = CHERRY_QUANTITY * CHERRY.getPrice() +
                CLEMENTINE_QUANTITY * CLEMENTINE.getPrice() +
                WATERMELON_QUANTITY * WATERMELON.getPrice();

        assertEquals(127.0, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        market.printCart(itemsInCart);
    }


    /**
     * In this test the rule will be applied to 5 different sets.
     *
     * Initial quantities of each product are as follows:
     *              9 Strawberries, 30 Pineapples, 5 Clementines.
     *              30 Apples, 10 Watermelons, 15 Cherry,
     *              8 Pears
     *
     * After first Rule:
     *      Used for discount: 10 Apples, 10 Pineapples and 10 watermelons
     *      Free Item: 10 Apples
     *      Left for use in other discounts:
     *              20 Apples, 20 Pineapples, 0 Watermelons. All other items stay the same
     *
     * After second Rule:
     *      No items are used for discount, since there are no more watermelons available.
     *      Left for use items: remain the same
     *
     * After third Rule:
     *      Used for discount: 5 Clementines, 5 Strawberries and 5 Pineapples
     *      Free Item: 5 Pineapples
     *      Left for use in other discounts:
     *              4 Strawberries, 15 Pineapples, 0 Clementines.
     *              20 Apples, 0 Watermelons, 15 Cherry,
     *              8 Pears
     *
     * After forth Rule:
     *      Used for discount: 15 Apples, 15 Cherry and 15 Pineapples
     *      Free Item: 15 Apples
     *      Left for use in other discounts:
     *              5 Apples, 0 Pineapples, 0 Cherry
     *              4 Strawberries, 0 Clementines,  0 Watermelons
     *              8 Pears
     *
     * After fifth Rule:
     *      Used for discount: 4 Strawberries, 4 Pears and 4 Apples
     *      Free Item: 4 Apples
     *      Left for use in other discounts:
     *              1 Apples, 0 Pineapples, 0 Cherry
     *              0 Strawberries, 0 Clementines,  0 Watermelons
     *              4 Pears
     *
     *
     * The final price must contain:
     *      29 Apples for free
     *      5 Pineapples for free
     *      All other items are in the cart as full priced
     *
     */
    @Test
    public void multipleSetsInMultipleRules(){

        final int WATERMELON_QUANTITY = 10;
        final int CHERRY_QUANTITY = 15;
        final int CLEMENTINE_QUANTITY = 5;
        final int PEAR_QUANTITY = 8;
        final int PINEAPPLE_QUANTITY = 30;
        final int APPLE_QUANTITY = 30;
        final int STRAWBERRY_QUANTITY = 9;

        //Cheapest is apple with price = 1
        Set<Product> first_set = new HashSet<>();
        first_set.add(APPLE);
        first_set.add(WATERMELON);
        first_set.add(PINEAPPLE);

        //cheapest is cherry with price = 3
        Set<Product> sec_set = new HashSet<>();
        sec_set.add(CHERRY);
        sec_set.add(WATERMELON);
        sec_set.add(STRAWBERRY);

        //cheapest is pineapple with price = 4
        Set<Product> third_set = new HashSet<>();
        third_set.add(CLEMENTINE);
        third_set.add(STRAWBERRY);
        third_set.add(PINEAPPLE);

        //cheapest is apple with price = 1
        Set<Product> forth_set = new HashSet<>();
        forth_set.add(CHERRY);
        forth_set.add(APPLE);
        forth_set.add(PINEAPPLE);

        //cheapest is apple with price = 1
        Set<Product> fifth_set = new HashSet<>();
        fifth_set.add(PEAR);
        fifth_set.add(APPLE);
        fifth_set.add(STRAWBERRY);

        market.addDiscount(new BuySetDontPayCheapest(first_set));
        market.addDiscount(new BuySetDontPayCheapest(sec_set));
        market.addDiscount(new BuySetDontPayCheapest(third_set));
        market.addDiscount(new BuySetDontPayCheapest(forth_set));
        market.addDiscount(new BuySetDontPayCheapest(fifth_set));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(WATERMELON, WATERMELON_QUANTITY);
        cart.addToCart(CHERRY, CHERRY_QUANTITY);
        cart.addToCart(CLEMENTINE, CLEMENTINE_QUANTITY);
        cart.addToCart(PEAR, PEAR_QUANTITY);
        cart.addToCart(PINEAPPLE, PINEAPPLE_QUANTITY);
        cart.addToCart(APPLE, APPLE_QUANTITY);
        cart.addToCart(STRAWBERRY, STRAWBERRY_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        int numFreeItemsAtTheEnd = 4;
        int numInitialItemsInCart = 7;

        int EXPECTED_NUM_FREE_APPLES = 29;
        int EXPECTED_NUM_FREE_PINEAPPLES = 5;

        int EXPECTED_NUM_FULL_PRICED_APPLES = APPLE_QUANTITY - EXPECTED_NUM_FREE_APPLES;
        int EXPECTED_NUM_FULL_PRICED_PINEAPPLES = PINEAPPLE_QUANTITY - EXPECTED_NUM_FREE_PINEAPPLES;

        assertEquals(numInitialItemsInCart + numFreeItemsAtTheEnd, itemsInCart.size(),
                "There should be "+numInitialItemsInCart + numFreeItemsAtTheEnd+" items cart");

        int freeAppleQuantityCounter = 0;
        int freePineappleQuantityCounter = 0;

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (item.calculatePrice() == 0.0){
                if (item.getProduct().equals(APPLE)){
                    freeAppleQuantityCounter+=item.getQuantity();
                }
                else if (item.getProduct().equals(PINEAPPLE)){
                    freePineappleQuantityCounter+=item.getQuantity();
                }
                else {
                    //todo: change this to use other assert
                    assertEquals(false, true,
                            "There shouldn't other a free item in the cart ");
                }
            }
            else if (WATERMELON.equals(item.getProduct()) ){
                assertEquals (WATERMELON_QUANTITY,item.getQuantity(),
                        "There should be "+WATERMELON_QUANTITY+" of full priced watermelons");
            }
            else if (CHERRY.equals(item.getProduct()) ){
                assertEquals (CHERRY_QUANTITY, item.getQuantity(),
                        "There should be "+CHERRY_QUANTITY+" of full priced cherries");
            }
            else if (CLEMENTINE.equals(item.getProduct()) ){
                assertEquals (CLEMENTINE_QUANTITY,item.getQuantity(),
                        "There should be "+CLEMENTINE_QUANTITY+" of full priced clementine");
            }
            else if (PEAR.equals(item.getProduct()) ){
                assertEquals (PEAR_QUANTITY,item.getQuantity(),
                        "There should be "+PEAR_QUANTITY+" of full priced pear");
            }
            else if (PINEAPPLE.equals(item.getProduct()) ){
                assertEquals (EXPECTED_NUM_FULL_PRICED_PINEAPPLES,item.getQuantity(),
                        "There should be "+EXPECTED_NUM_FULL_PRICED_PINEAPPLES+" of full priced pineapple");
            }
            else if (APPLE.equals(item.getProduct()) ){
                assertEquals (EXPECTED_NUM_FULL_PRICED_APPLES,item.getQuantity(),
                        "There should be "+EXPECTED_NUM_FULL_PRICED_APPLES+" of full priced apple");
            }
            else if (STRAWBERRY.equals(item.getProduct()) ){
                assertEquals (STRAWBERRY_QUANTITY,item.getQuantity(),
                        "There should be "+STRAWBERRY_QUANTITY+" of full priced strawberry");
            }
        }

        assertEquals (EXPECTED_NUM_FREE_APPLES,freeAppleQuantityCounter, "There should be "+EXPECTED_NUM_FREE_APPLES+" free apples in the cart");
        assertEquals (EXPECTED_NUM_FREE_PINEAPPLES,freePineappleQuantityCounter, "There should be "+EXPECTED_NUM_FREE_PINEAPPLES+" free apples in the cart");

        float finalPrice = WATERMELON_QUANTITY * WATERMELON.getPrice() +
                CHERRY_QUANTITY * CHERRY.getPrice() +
                CLEMENTINE_QUANTITY * CLEMENTINE.getPrice() +
                PEAR_QUANTITY * PEAR.getPrice() +
                STRAWBERRY_QUANTITY * STRAWBERRY.getPrice() +
                EXPECTED_NUM_FULL_PRICED_PINEAPPLES * PINEAPPLE.getPrice() +
                EXPECTED_NUM_FULL_PRICED_APPLES * APPLE.getPrice();

        assertEquals(411.0, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        market.printCart(itemsInCart);
    }
}
