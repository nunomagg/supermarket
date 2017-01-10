package discountRules;

import supermarket.Item;
import supermarket.Product;

import java.util.Map;

/**
 * Created by Nuno Maggiolly on 10-01-2017.
 */
public abstract class AbstractSaleRule implements SaleRule{

    /**
     * Removes from the item, to which the discount was applied.
     * If there are no more items at full price left. The product is removed from the Map.
     *
     * @param items
     * @param item
     * @param quantity
     * @param usedUnitsInDiscount
     */
    protected void removeItemQuantityOnDiscount(Map<Product, Item> items, Item item, int quantity, int usedUnitsInDiscount ){
        item.removeQuantityOnDiscount(quantity,usedUnitsInDiscount);
        if (item.getQuantity() <= 0){
            items.remove(item.getProduct());
        }
    }

}
