package discountRules;

import supermarket.Product;
import supermarket.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class BuyXGetYOtherItemsForFree extends AbstractSingleSaleRule {
    private static final boolean IS_CUMULATIVE = false;
    private static final int DISCOUNT_PERCENTAGE = 100;

    protected int buyUnits;
    protected Product freeProduct;
    protected int freeUnits;

    public BuyXGetYOtherItemsForFree(Product product, int buyUnits, Product freeProduct, int freeUnits) {
        this.applicableProduct = product;
        this.buyUnits = buyUnits;
        this.freeProduct = freeProduct;
        this.freeUnits = freeUnits;
    }

    @Override
    public List<Item> calculatePrice(Map<Product, Item> items) {

        if (items==null){
            return null;
        }

        if (!items.containsKey(applicableProduct)){
            return new ArrayList<>();
        }

        Item item = items.get(applicableProduct);
        List<Item> productsWithDiscount = new ArrayList<>();

        if (IS_CUMULATIVE || !item.hasDiscountOrAllItemsUsedInDiscount()) {
            if (items.get(applicableProduct).getAvailableQuantity() >= buyUnits) {
                int quantityMultiplier = Math.floorDiv(item.getAvailableQuantity(), buyUnits);
                item.applyDiscountTo(buyUnits);
                productsWithDiscount.add(new Item(freeProduct, quantityMultiplier * freeUnits, DISCOUNT_PERCENTAGE));
            }
        }

        return productsWithDiscount;
    }

}
