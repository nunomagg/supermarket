package discountRules;

import supermarket.Product;
import supermarket.Item;

import java.util.List;
import java.util.Map;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public interface SaleRule {

    List<Item> calculatePrice(Map<Product, Item> items);
    boolean appliesToThisProduct(Product p);
}
