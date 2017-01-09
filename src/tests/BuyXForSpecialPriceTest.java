package tests;

import discountRules.BuyXForSpecialPrice;
import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;
import supermarket.SuperMarket;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by MariaMagg on 09-01-2017.
 */
public class BuyXForSpecialPriceTest extends SuperMarketTest {

    /**
     * buy 2 (equals) items for a special price
     * in this case the second is half price. (i.e) you pay 75% of the set
     */
    @Test
    public void buy2EqualForSpecialPrice(){

        final float DISCOUNT_PERCENT = 50;
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
}
