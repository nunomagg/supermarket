package tests;

import discountRules.BuySetDontPayLowest;
import discountRules.BuyXForSpecialPrice;
import discountRules.BuyXGetOtherForFree;
import discountRules.BuyXGetYForFree;
import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;
import supermarket.SuperMarket;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class SuperMarketTest {

    private SuperMarket getSuperMarketWithProducts (){

        SuperMarket market = new SuperMarket();

        market.addProduct("Banana", 2); //0
        market.addProduct("Apple", 1); //1
        market.addProduct("Pineapple", 4); //2
        market.addProduct("Pear", 10); // 3
        market.addProduct("Strawberry", 5); //4
        market.addProduct("Apricot", 15); //5

        market.addProduct("Avocado", 6); //6
        market.addProduct("Cherry", 3); //7
        market.addProduct("Watermelon", 10); //8
        market.addProduct("Clementine", 8); //9

        return market;
    }

    /**
     * buy 3 (equals) items and pay for 2
     */
    @Test
    public void buyThreeEqualPayTwo(){

        SuperMarket market = getSuperMarketWithProducts();

        final Product BANANA = market.getProduct(0);

        //buy 3 get 1 for free
        market.addDiscount(new BuyXGetOtherForFree(BANANA,3,1));

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
                assertEquals (3,item.getQuantity(),"There should be 3 full priced bananas");
            }
        }

        float finalPrice =  fullPricedItem.getQuantity() * fullPricedItem.getProduct().getPrice();

        assertEquals(2*3 + 1*0, market.getFinalPrice(itemsInCart), "wrong full price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong full price");

        market.printCart(itemsInCart);
    }

    /**
     * buy 2 (equals) items for a special price
     * in this case the second is half price. (i.e) you pay 75% of the set
     */
    @Test
    public void buy2EqualForSpecialPrice(){
        SuperMarket market = getSuperMarketWithProducts();

        final float DISCOUNT_PERCENT = 50;
        final Product STRAWBERRY = market.getProduct(4);
        final int QUANTITY_IN_CART = 4;

        final int RESULT_FULL_PRICED_UNITS = 2;
        final int RESULT_HALF_PRICED_UNITS = 2;

        //Buy 2 get one for Half price
        market.addDiscount(new BuyXForSpecialPrice(STRAWBERRY,2,1, DISCOUNT_PERCENT));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(STRAWBERRY,QUANTITY_IN_CART);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (2,itemsInCart.size(),"There should be 2 'items' Strawberry in cart ");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            //lower value
            if (STRAWBERRY.equals(item.getProduct()) && item.calculatePrice() < item.getQuantity()*STRAWBERRY.getPrice()){
                assertEquals (RESULT_HALF_PRICED_UNITS,item.getQuantity(),"There should be "+RESULT_HALF_PRICED_UNITS+" Strawberries for half price in the cart ");
            }
            else if (STRAWBERRY.equals(item.getProduct()) && item.calculatePrice() == item.getQuantity()*STRAWBERRY.getPrice()){
                assertEquals (RESULT_FULL_PRICED_UNITS,item.getQuantity(),"There should be "+RESULT_FULL_PRICED_UNITS+" full priced strawberries");
            }
        }

        float finalPrice = RESULT_FULL_PRICED_UNITS * STRAWBERRY.getPrice() +
                        RESULT_HALF_PRICED_UNITS * STRAWBERRY.getPrice() * (DISCOUNT_PERCENT / 100);

        assertEquals(2*5 + 2*2.5, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        market.printCart(itemsInCart);
    }

    /**
     * buy 3 (in a set of items) and the cheapest is free
     */
    @Test
    public void buy3FromSetCheapestIsFree(){
        SuperMarket market = getSuperMarketWithProducts();

        final Product WATERMELON  = market.getProduct(8);
        final Product CHERRY  = market.getProduct(7);
        final Product PINEAPPLE  = market.getProduct(2);
        final int MIN_QUANTITY = 4; //cart must have an entry with the cheapest product with this quantity
        final int WATERMELON_QUANTITY = MIN_QUANTITY;
        final int CHERRY_QUANTITY = MIN_QUANTITY+1;
        final int PINEAPPLE_QUANTITY = MIN_QUANTITY+2;

        //Cheapest is cherry with price = 3
        Set<Product> applicable = new HashSet<>();
        applicable.add(CHERRY);
        applicable.add(WATERMELON);
        applicable.add(PINEAPPLE);

        market.addDiscount(new BuySetDontPayLowest(applicable));

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
     * for each N (equals) items X, you get K items Y for free
     */
    @Test
    public void forEachNXItemsGetKYForFree(){
        SuperMarket market = getSuperMarketWithProducts();

        final int N_UNITS = 3;
        final int K_UNITS = 2;
        final Product PRODUCT_X = market.getProduct(5); //Apricot
        final Product PRODUCT_Y = market.getProduct(3); //PEAR

        //Buy 3 Apricots get 2 PEARs
        market.addDiscount(new BuyXGetYForFree(PRODUCT_X, N_UNITS, PRODUCT_Y, K_UNITS));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(PRODUCT_X, N_UNITS);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (2,itemsInCart.size(),"There should be 1 'item' Apricot and 1 Pear in the cart ");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (PRODUCT_Y.equals(item.getProduct())){
                assertEquals(PRODUCT_Y,item.getProduct(), "This product should be a "+PRODUCT_Y.getName());
                assertEquals (K_UNITS,item.getQuantity(),
                        "There should be "+K_UNITS+" of "+PRODUCT_Y.getName()+" for free in the cart ");
            }
            else if (PRODUCT_X.equals(item.getProduct())){
                assertEquals (N_UNITS,item.getQuantity(),"There should be "+N_UNITS+" of full priced "+PRODUCT_X.getName());
            }
        }

        float finalPrice = N_UNITS * PRODUCT_X.getPrice();

        assertEquals(15*3, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        market.printCart(itemsInCart);
    }

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

        final Product BANANA = market.getProduct(0);
        final int BANANA_QUANTITY = 8;

        final int RESULT_FREE_BANANAS = 2;
        final int RESULT_HALF_PRICE_BANANAS = 1;
        final int RESULT_FULL_PRICE_BANANAS = 5;

        market.addDiscount(new BuyXGetOtherForFree(BANANA,3,1));
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

        market.printCart(itemsInCart);
    }
}
