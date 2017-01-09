package tests;

import discountRules.BuyXForSpecialPrice;
import discountRules.BuyXGetAnotherForFree;
import discountRules.BuyXGetYOtherItemsForFree;
import org.junit.jupiter.api.Test;
import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;
import supermarket.SuperMarket;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        return market;
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

        final int BANANA_QUANTITY = 8;
        final int RESULT_FREE_BANANAS = 2;
        final int RESULT_HALF_PRICE_BANANAS = 1;
        final int RESULT_FULL_PRICE_BANANAS = 5;

        market.addDiscount(new BuyXGetAnotherForFree(BANANA,3,1));
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
        market.addDiscount(new BuyXGetAnotherForFree(BANANA,3,1));

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

        market.printCart(itemsInCart);
    }


    //comprar 3 mas em que 2 são de outro tipo de produto
    // 3 regras seguidas
    // um em que tem a regra aplicada a um produto que nao existe no carrinho
    // para cada regra um exemplo em que a regra nao é aplicada ( 4)
    // um carrinho com varios produtos adicionados por ordem aleatoria e repetida e com várias regras
    // um teste para o adicionar de produtos ao supermarket
    // um teste para o adicionar de produtos ao carrinho
    // um teste com adicionar e depois remover certos produtos ao supermarket
    // um teste com adicionar e depois remover certos produtos ao carrinho

}
