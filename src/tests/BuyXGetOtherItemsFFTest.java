package tests;

import discountRules.BuyXGetYOtherItemsForFree;
import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;
import supermarket.SuperMarket;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Nuno Maggiolly on 09-01-2017.
 */
public class BuyXGetOtherItemsFFTest extends SuperMarketTest{


    /**
     * for each N (equals) items X, you get K items Y for free
     */
    @Test
    public void forEachNXItemsGetKYForFree(){

        final int N_UNITS = 3;
        final int K_UNITS = 2;
        final Product AVOCADO = APRICOT;
        final Product STRAWBERRY = PEAR;

        //Buy 3 Apricots get 2 PEARs
        market.addDiscount(new BuyXGetYOtherItemsForFree(AVOCADO, N_UNITS, STRAWBERRY, K_UNITS));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(AVOCADO, N_UNITS);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (2,itemsInCart.size(),"There should be 1 'item' Apricot and 1 Pear in the cart ");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            if (STRAWBERRY.equals(item.getProduct())){
                assertEquals(STRAWBERRY,item.getProduct(), "This product should be a "+STRAWBERRY.getName());
                assertEquals (K_UNITS,item.getQuantity(),
                        "There should be "+K_UNITS+" of "+STRAWBERRY.getName()+" for free in the cart ");
            }
            else if (AVOCADO.equals(item.getProduct())){
                assertEquals (N_UNITS,item.getQuantity(),"There should be "+N_UNITS+" of full priced "+AVOCADO.getName());
            }
        }

        float finalPrice = N_UNITS * AVOCADO.getPrice();

        //assertEquals(15*3, market.getFinalPrice(itemsInCart), "wrong final price");
        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        market.printCart(itemsInCart);
    }

    /**
     * This method tests if the BuyXGetYOtherItemsForFree is applied to the X item (in this case Avocado)
     * if the quantity in the cart is less than the one necessary for the discount (N_UNITS)
     */
    @Test
    public void BuyAvocadosGetStrawberriesNotApplied(){

        final int N_UNITS = 10;
        final int K_UNITS = 3;
        final int AVOCADO_QUANTITY = 5;
        final int PINEAPPLE_QUANTITY = 8;

        //Buy 3 AVOCADO get 2 STRAWBERRY
        market.addDiscount(new BuyXGetYOtherItemsForFree(AVOCADO, N_UNITS, STRAWBERRY, K_UNITS));

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(AVOCADO, AVOCADO_QUANTITY);
        cart.addToCart(PINEAPPLE, PINEAPPLE_QUANTITY);

        List<Item> itemsInCart = market.applyDiscount(cart);

        assertEquals (2,itemsInCart.size(),"There should be 2 items in the cart ");

        for (int i = 0; i < itemsInCart.size(); i++) {
            Item item = itemsInCart.get(i);

            assertFalse(STRAWBERRY.equals(item.getProduct()),"Strawberries shouldn't exist in the cart");

            if (AVOCADO.equals(item.getProduct())){
                assertEquals (AVOCADO_QUANTITY,item.getQuantity(),"There should be "+AVOCADO_QUANTITY+" of full priced "+AVOCADO.getName());
            }
            else if (PINEAPPLE.equals(item.getProduct())){
                assertEquals (PINEAPPLE_QUANTITY,item.getQuantity(),"There should be "+PINEAPPLE_QUANTITY+" of full priced "+PINEAPPLE.getName());
            }
        }

        float finalPrice = AVOCADO_QUANTITY * AVOCADO.getPrice() + PINEAPPLE.getPrice() * PINEAPPLE_QUANTITY;

        assertEquals(finalPrice, market.getFinalPrice(itemsInCart), "wrong final price");

        market.printCart(itemsInCart);
    }

    //Missing a more complex Test
}
