package discountRules;

import supermarket.Item;
import supermarket.Product;
import supermarket.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class DiscountFactory {
    private List<SalesRule> salesRules;

    public DiscountFactory() {
        salesRules = new ArrayList<>();
    }

    /**
     * Adds a SalesRule to the existing sales
     *
     * @param discount
     */
    public void addSalesRule(SalesRule discount){
        if (discount!=null) {
            salesRules.add(discount);
            orderDiscountRules();
        }
    }

    /**
     * This method receives a cart and applies all discounts it can to the items in the cart
     *
     * @param cart
     * @return List<Item> - with all the items in cart after the discounts are applied
     */
    public List<Item> applyDiscounts(ShoppingCart cart) {
        Map<Product, Item> checkoutItems = cart.getItemsInCart();

        if (checkoutItems==null){
            return null;
        }

        List<Item> discountedProducts = new ArrayList<>();

        for (SalesRule discountRule : salesRules) {
            discountedProducts.addAll(discountRule.calculatePrice(checkoutItems));
        }

        //Add non discounted products to the final list of items
        discountedProducts.addAll(checkoutItems.values());

        return discountedProducts;
    }

    private void orderDiscountRules(){
        //TODO: Missing order discount rules implementation
    }

}
