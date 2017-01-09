package tests;

import discountRules.BuyXGetYOtherItemsForFree;
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
public class BuyXGetOtherItemsFFTest extends SuperMarketTest{


    /**
     * for each N (equals) items X, you get K items Y for free
     */
    @Test
    public void forEachNXItemsGetKYForFree(){

        final int N_UNITS = 3;
        final int K_UNITS = 2;
        final Product PRODUCT_X = market.getProduct(5); //Apricot
        final Product PRODUCT_Y = market.getProduct(3); //PEAR

        //Buy 3 Apricots get 2 PEARs
        market.addDiscount(new BuyXGetYOtherItemsForFree(PRODUCT_X, N_UNITS, PRODUCT_Y, K_UNITS));

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

}
