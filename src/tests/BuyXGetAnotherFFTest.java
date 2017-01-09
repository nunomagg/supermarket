package tests;

import discountRules.BuyXGetAnotherForFree;
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
public class BuyXGetAnotherFFTest extends SuperMarketTest{

    /**
     * buy 3 (equals) items and pay for 2
     */
    @Test
    public void buyThreeEqualPayTwo(){

        //buy 3 get 1 for free
        market.addDiscount(new BuyXGetAnotherForFree(BANANA,3,1));

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
}
