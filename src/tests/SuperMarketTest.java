package tests;

import org.junit.jupiter.api.Test;
import supermarket.Product;
import supermarket.SuperMarket;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    //comprar 3 mas em que 2 são de outro tipo de produto
    // 3 regras diferentes seguidas
    // para cada regra um exemplo em que a regra nao é aplicada ( 4)
    // um teste com adicionar e depois remover certos produtos ao supermarket

}
