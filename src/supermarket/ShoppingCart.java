package supermarket;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class ShoppingCart {

    private Map<Product, Item> itemsInCart = new HashMap<>();

    public void addToCart(Product p) {
        addToCart(p, 1);
    }

    public void removeFromCart(Product p) {
        removeFromCart(p, 1);
    }

    public void addToCart(Product p, int quantity) {
        if (itemsInCart.containsKey(p)) {
            itemsInCart.get(p).addQuantity(quantity);
        } else {
            itemsInCart.put(p, new Item(p, quantity));
        }
    }

    public void removeFromCart(Product p, int quantity) {
        if (itemsInCart.containsKey(p)) {
            if (itemsInCart.get(p).getQuantity() == quantity) {
                itemsInCart.remove(p);
            } else {
                itemsInCart.get(p).removeQuantity(quantity);
            }
        }
    }

    public Map<Product, Item> getItemsInCart() {
        return itemsInCart;
    }
}

