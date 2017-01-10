package discountRules;

import supermarket.Product;
import supermarket.Item;

import java.util.*;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class BuySetDontPayCheapest extends AbstractMultipleSaleRule {

    private static final boolean IS_CUMULATIVE = false;

    public BuySetDontPayCheapest(Set<Product> applicableItems) {
        this.applicableProducts = applicableItems;
    }

    @Override
    public List<Item> calculatePrice(Map<Product, Item> items) {

        if (items == null) {
            return null;
        }
        List<Item> productsWithDiscount = new ArrayList<>();

        int minQuantityFound = Integer.MAX_VALUE;
        Product cheapestProduct = null;
        int foundItems = 0;

        //check if all applicable products are in the shopping cart
        for (Product product : applicableProducts) {
            if (items.containsKey(product)) {
                Item item = items.get(product);
                if (IS_CUMULATIVE || !item.hasDiscountOrAllItemsUsedInDiscount()) {
                    foundItems++;
                    minQuantityFound = item.getAvailableQuantity() < minQuantityFound ? item.getAvailableQuantity() : minQuantityFound;
                    //check if current product is the cheapest. if so, save reference
                    if (cheapestProduct == null || product.getPrice() < cheapestProduct.getPrice()) {
                        cheapestProduct = product;
                    }
                }else {
                    //has already been used in a discount or this discount is not cumulative
                    break;
                }
            } else {
                //applicable product has not been found
                break;
            }
        }

        /*if all items exist in the cart
                add all min quantities of the cheapestItem as a discount item
                and set min quantities of the other items as used in a discount
         */
        if (foundItems == applicableProducts.size()){
            Item cheapestItem = items.get(cheapestProduct);
            removeItemQuantityOnDiscount(items, cheapestItem ,minQuantityFound,minQuantityFound);

            for (Product prod : applicableProducts) {
                if (!prod.equals(cheapestProduct)){
                    items.get(prod).applyDiscountTo(minQuantityFound);
                }
            }

            productsWithDiscount.add(new Item(cheapestProduct,minQuantityFound,100));
        }

        return productsWithDiscount;
    }

}
