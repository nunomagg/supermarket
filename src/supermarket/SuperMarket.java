package supermarket;

import discountRules.DiscountFactory;
import discountRules.SaleRule;

import java.util.*;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class SuperMarket {

    private long seq_id;
    private Map<Long, Product> productById;
    private DiscountFactory discountFactory;

    public SuperMarket() {
        productById = new HashMap<>();
        discountFactory = new DiscountFactory();
    }

    public Product addProduct(String name, float price) {
        Product product = new Product(seq_id, name, price);
        productById.put(seq_id++, product);
        return product;
    }

    public Product removeProduct(long id) {
        return productById.remove(id);
    }

    public Product getProduct(long id) {
        return productById.get(id);
    }

    public int numberOfExistingProducts(){return productById.size();}

    public void addDiscount(SaleRule discount) {
        discountFactory.addSalesRule(discount);
    }

    public void printProducts() {
        StringBuilder sb = new StringBuilder();

        sb.append("---- PRINTING PRODUCTS ----\n");
        for (Product product : productById.values()) {
            sb.append("id: ")
                    .append(product.getId())
                    .append(" | name ")
                    .append(product.getName())
                    .append(" | price: ")
                    .append(product.getPrice())
                    .append("\n");
        }
        System.out.println(sb.toString());
    }

    public void printCart(Collection<Item> items) {
        StringBuilder sb = new StringBuilder();
        float finalPrice = 0;

        sb.append("---- PRINTING PRODUCTS IN CART ----\n");

        for (Item item : items) {
            finalPrice +=  item.calculatePrice();
            sb.append(item.toString());
            sb.append("\n");
        }
        sb.append("--------------\n");
        sb.append("Final Price "+ finalPrice);
        sb.append("\n");
        System.out.println(sb.toString());
    }

    public void checkoutCart(ShoppingCart cart) {
        List<Item> checkoutItems = discountFactory.applyDiscounts(cart);
        printCart(checkoutItems);
    }
    public List<Item> applyDiscount(ShoppingCart cart) {
        List<Item> checkoutItems = discountFactory.applyDiscounts(cart);
        return checkoutItems;
    }

    public float getFinalPrice(List<Item> checkoutItems){
        float finalPrice = 0;
        if (checkoutItems!=null) {
            for (Item checkoutItem : checkoutItems) {
                finalPrice += checkoutItem.calculatePrice();
            }
        }
        return finalPrice;
    }

}
