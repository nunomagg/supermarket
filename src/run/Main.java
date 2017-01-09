package run;

import discountRules.BuySetDontPayLowest;
import discountRules.BuyXForSpecialPrice;
import discountRules.BuyXGetOtherForFree;
import discountRules.BuyXGetYForFree;
import supermarket.Product;
import supermarket.ShoppingCart;
import supermarket.SuperMarket;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class Main {

	public static void main(String[] args){
		
		SuperMarket market = new SuperMarket();
		
		market.addProduct("BANANA", 2);
		market.addProduct("APPLE", 1);
		market.addProduct("PINEAPPLE", 4);
		market.addProduct("PEAR", 10);
		market.addProduct("WATERMELON", 5);
		market.addProduct("CHERRY", 15);
		market.printProducts();

		//REGRAS
		Set<Product> applicable = new HashSet<>();
		applicable.add(market.getProduct(1));
		applicable.add(market.getProduct(2));

		//Regra 1
		market.addDiscount(new BuyXGetOtherForFree(market.getProduct(0),3,1));
		market.addDiscount(new BuyXForSpecialPrice(market.getProduct(0),2,1,50));

		//Regra 2
		market.addDiscount(new BuyXForSpecialPrice(market.getProduct(4),3,1, 50));

		//- buy 3 (in a set of items) and the cheapest is free
		market.addDiscount(new BuySetDontPayLowest(applicable));

		//for each mouse get 2 HATS!
		market.addDiscount(new BuyXGetYForFree(market.getProduct(5),1,market.getProduct(3),2));

		ShoppingCart cart = new ShoppingCart();
		cart.addToCart(market.getProduct(0),3);
		cart.addToCart(market.getProduct(5),2);
		cart.addToCart(market.getProduct(1),2);
		cart.addToCart(market.getProduct(2),1);
		cart.addToCart(market.getProduct(0),5);
		cart.addToCart(market.getProduct(4),6);

		market.checkoutCart(cart);
	}
}
