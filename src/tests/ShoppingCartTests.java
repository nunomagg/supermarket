package tests;

import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Nuno Maggiolly on 10-01-2017.
 */
public class ShoppingCartTests extends SuperMarketTest{


    /**
     * Test the addition of products to the shopping cart
     */
    @Test
    public void addItemsToShoppingCart(){
        final int WATERMELON_QUANTITY = 45;
        final int CHERRY_QUANTITY = 25;
        final int CLEMENTINE_QUANTITY = 9;
        final int PEAR_QUANTITY = 8;
        final int PINEAPPLE_QUANTITY = 21;
        final int APPLE_QUANTITY = 12;
        final int STRAWBERRY_QUANTITY = 5;
        final int CLEMENTINE_QUANTITY_2 = 7;
        
        final int NR_ITEMS = 7;
        final int EXPECTED_CLEMENTINE_QUANTITY = CLEMENTINE_QUANTITY + CLEMENTINE_QUANTITY_2;
        
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(WATERMELON, WATERMELON_QUANTITY);
        cart.addToCart(CHERRY, CHERRY_QUANTITY);
        cart.addToCart(CLEMENTINE, CLEMENTINE_QUANTITY);
        cart.addToCart(PEAR, PEAR_QUANTITY);
        cart.addToCart(PINEAPPLE, PINEAPPLE_QUANTITY);
        cart.addToCart(CLEMENTINE, CLEMENTINE_QUANTITY_2);
        cart.addToCart(APPLE, APPLE_QUANTITY);
        cart.addToCart(STRAWBERRY, STRAWBERRY_QUANTITY);

        Set<Map.Entry<Product, Item>> cartEntries = cart.getItemsInCart().entrySet();

        assertEquals(NR_ITEMS,cartEntries.size(),"There should only exist "+NR_ITEMS+" items in the cart");

        //Watermelon
        assertTrue(cart.getItemsInCart().get(WATERMELON)!=null, "there should exist a watermelon item"); 
        assertEquals(WATERMELON_QUANTITY,cart.getItemsInCart().get(WATERMELON).getQuantity(),
                "there should exist "+WATERMELON_QUANTITY+" of watermelon in the cart"); 
        
        //CHERRY
        assertTrue(cart.getItemsInCart().get(CHERRY)!=null, "there should exist a CHERRY item"); 
        assertEquals(CHERRY_QUANTITY,cart.getItemsInCart().get(CHERRY).getQuantity(),
                "there should exist "+CHERRY_QUANTITY+" of CHERRY in the cart"); 
        
        //PEAR
        assertTrue(cart.getItemsInCart().get(PEAR)!=null, "there should exist a PEAR item"); 
        assertEquals(PEAR_QUANTITY,cart.getItemsInCart().get(PEAR).getQuantity(),
                "there should exist "+PEAR_QUANTITY+" of PEAR in the cart"); 
        
        //PINEAPPLE
        assertTrue(cart.getItemsInCart().get(PINEAPPLE)!=null, "there should exist a PINEAPPLE item"); 
        assertEquals(PINEAPPLE_QUANTITY,cart.getItemsInCart().get(PINEAPPLE).getQuantity(),
                "there should exist "+PINEAPPLE_QUANTITY+" of PINEAPPLE in the cart"); 
        
        //APPLE
        assertTrue(cart.getItemsInCart().get(APPLE)!=null, "there should exist a APPLE item"); 
        assertEquals(APPLE_QUANTITY,cart.getItemsInCart().get(APPLE).getQuantity(),
                "there should exist "+APPLE_QUANTITY+" of APPLE in the cart"); 
        
        //Watermelon
        assertTrue(cart.getItemsInCart().get(WATERMELON)!=null, "there should exist a watermelon item"); 
        assertEquals(WATERMELON_QUANTITY,cart.getItemsInCart().get(WATERMELON).getQuantity(),
                "there should exist "+WATERMELON_QUANTITY+" of watermelon in the cart"); 
        
        //CLEMENTINE
        assertTrue(cart.getItemsInCart().get(CLEMENTINE)!=null, "there should exist a CLEMENTINE item"); 
        assertEquals(EXPECTED_CLEMENTINE_QUANTITY,cart.getItemsInCart().get(CLEMENTINE).getQuantity(),
                "there should exist "+EXPECTED_CLEMENTINE_QUANTITY+" of CLEMENTINE in the cart");

        market.printCart(cart.getItemsInCart().values());
    }

    /**
     * Adding and removing items from the cart
     */
    @Test
    public void addAndRemoveItemsFromShoppingCart(){
        final int WATERMELON_QUANTITY = 45;
        final int CHERRY_QUANTITY = 25;
        final int CLEMENTINE_QUANTITY = 9;
        final int PEAR_QUANTITY = 8;
        final int PINEAPPLE_QUANTITY = 21;
        final int APPLE_QUANTITY = 12;
        final int STRAWBERRY_QUANTITY = 5;
        final int CLEMENTINE_QUANTITY_2 = 7;

        final int REMOVE_PINEAPPLE_QUANTITY = 10;
        final int REMOVE_CHERRY_QUANTITY = 6;
        final int REMOVE_CLEMENTINE_QUANTITY = 11;

        final int EXPECTED_NR_ITEMS = 6;
        final int EXPECTED_CLEMENTINE_QUANTITY = CLEMENTINE_QUANTITY_2;
        final int EXPECTED_PINEAPPLE_QUANTITY = PINEAPPLE_QUANTITY - REMOVE_PINEAPPLE_QUANTITY;
        final int EXPECTED_CHERRY_QUANTITY = CHERRY_QUANTITY - REMOVE_CHERRY_QUANTITY;

        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(WATERMELON, WATERMELON_QUANTITY);
        cart.addToCart(CHERRY, CHERRY_QUANTITY);
        cart.addToCart(CLEMENTINE, CLEMENTINE_QUANTITY);
        cart.addToCart(PEAR, PEAR_QUANTITY);
        cart.removeFromCart(CLEMENTINE,REMOVE_CLEMENTINE_QUANTITY);
        cart.addToCart(PINEAPPLE, PINEAPPLE_QUANTITY);
        cart.addToCart(CLEMENTINE, CLEMENTINE_QUANTITY_2);
        cart.addToCart(APPLE, APPLE_QUANTITY);
        cart.removeFromCart(CHERRY,REMOVE_CHERRY_QUANTITY);
        cart.addToCart(STRAWBERRY, STRAWBERRY_QUANTITY);
        cart.removeFromCart(PINEAPPLE,REMOVE_PINEAPPLE_QUANTITY);
        cart.removeFromCart(PEAR, PEAR_QUANTITY);

        Set<Map.Entry<Product, Item>> cartEntries = cart.getItemsInCart().entrySet();

        assertEquals(EXPECTED_NR_ITEMS,cartEntries.size(),"There should only exist "+EXPECTED_NR_ITEMS+" items in the cart");

        //Watermelon
        assertTrue(cart.getItemsInCart().get(WATERMELON)!=null, "there should exist a watermelon item");
        assertEquals(WATERMELON_QUANTITY,cart.getItemsInCart().get(WATERMELON).getQuantity(),
                "there should exist "+WATERMELON_QUANTITY+" of watermelon in the cart");

        //CHERRY
        assertTrue(cart.getItemsInCart().get(CHERRY)!=null, "there should exist a CHERRY item");
        assertEquals(EXPECTED_CHERRY_QUANTITY,cart.getItemsInCart().get(CHERRY).getQuantity(),
                "there should exist "+EXPECTED_CHERRY_QUANTITY+" of CHERRY in the cart");

        //PEAR
        assertTrue(cart.getItemsInCart().get(PEAR)==null, "there shouldn't exist a PEAR item");

        //PINEAPPLE
        assertTrue(cart.getItemsInCart().get(PINEAPPLE)!=null, "there should exist a PINEAPPLE item");
        assertEquals(EXPECTED_PINEAPPLE_QUANTITY,cart.getItemsInCart().get(PINEAPPLE).getQuantity(),
                "there should exist "+EXPECTED_PINEAPPLE_QUANTITY+" of PINEAPPLE in the cart");

        //APPLE
        assertTrue(cart.getItemsInCart().get(APPLE)!=null, "there should exist a APPLE item");
        assertEquals(APPLE_QUANTITY,cart.getItemsInCart().get(APPLE).getQuantity(),
                "there should exist "+APPLE_QUANTITY+" of APPLE in the cart");

        //Watermelon
        assertTrue(cart.getItemsInCart().get(WATERMELON)!=null, "there should exist a watermelon item");
        assertEquals(WATERMELON_QUANTITY,cart.getItemsInCart().get(WATERMELON).getQuantity(),
                "there should exist "+WATERMELON_QUANTITY+" of watermelon in the cart");

        //CLEMENTINE
        assertTrue(cart.getItemsInCart().get(CLEMENTINE)!=null, "there should exist a CLEMENTINE item");
        assertEquals(EXPECTED_CLEMENTINE_QUANTITY,cart.getItemsInCart().get(CLEMENTINE).getQuantity(),
                "there should exist "+EXPECTED_CLEMENTINE_QUANTITY+" of CLEMENTINE in the cart");

        market.printCart(cart.getItemsInCart().values());
    }

}
