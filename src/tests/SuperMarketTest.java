package tests;

import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;
import supermarket.SuperMarket;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class SuperMarketTest {

    SuperMarket market = getSuperMarketWithProducts();

    final Product BANANA = market.getProduct(0);
    final Product APPLE = market.getProduct(1);
    final Product PINEAPPLE = market.getProduct(2);
    final Product PEAR = market.getProduct(3);
    final Product STRAWBERRY = market.getProduct(4);
    final Product APRICOT = market.getProduct(5);
    final Product AVOCADO = market.getProduct(6);
    final Product CHERRY = market.getProduct(7);
    final Product WATERMELON = market.getProduct(8);
    final Product CLEMENTINE = market.getProduct(9);

    @Test
    protected SuperMarket getSuperMarketWithProducts (){

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

        assertEquals(10,market.numberOfExistingProducts(), "should exist 10 products in the market");

        return market;
    }

    /**
     * There shouldn't be any product with 0 quantity
     */
    @Test
    public void testProductsQuantities(List<Item> items){

        if (items.size()>0){
            for (Item item : items) {
                assertTrue(item.getQuantity()>0,
                        "Item with product "+item.getProduct().getName()+ " at "+item.calculatePrice()+" should not exist with 0 quantity");
            }
        }

    }

    //comprar 3 mas em que 2 são de outro tipo de produto
    // 3 regras diferentes seguidas
    // para cada regra um exemplo em que a regra nao é aplicada ( 4)
    // um teste com adicionar e depois remover certos produtos ao supermarket


}
