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
    private List<SaleRule> salesRules;

    public DiscountFactory() {
        salesRules = new ArrayList<>();
    }

    /**
     * Adds a SaleRule to the existing sales
     *
     * @param discount
     */
    public void addSalesRule(SaleRule discount){
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

        for (SaleRule discountRule : salesRules) {
            discountedProducts.addAll(discountRule.calculatePrice(checkoutItems));
        }

        //Add non discounted products to the final list of items
        discountedProducts.addAll(checkoutItems.values());

        //mergeEqualProductsWithSamePrice(discountedProducts)

        return discountedProducts;
    }

    /**
     *  This method is meant to keep the code for ordering the discount rules by its priority
     *
     *  TODO: missing implementation
     */
    private void orderDiscountRules(){
        // Missing order discount rules implementation
    }

    /**
     * Meant to merge all items that represent the same product with the same price
     * This method should be implemented if the checkout receipt can't have the same product with the same price
     * appearing more than once
     *
     * TODO: missing implementation
     */
    private List<Item> mergeEqualProductsWithSamePrice(List<Item> discountedProducts){
        //missing implementation
        return discountedProducts;
    }

}
