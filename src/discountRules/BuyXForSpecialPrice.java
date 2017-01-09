package discountRules;

import supermarket.Product;
import supermarket.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class BuyXForSpecialPrice extends AbstractSingleSaleRule {

    private static final boolean IS_CUMULATIVE = false;

    protected int buyUnits;
    protected int discountedUnits;
    protected float specialPricePercentage;

    public BuyXForSpecialPrice(Product applicableProduct, int buyUnits, int discountedUnits, float specialPricePercentage) {
        this.applicableProduct = applicableProduct;
        this.buyUnits = buyUnits;
        this.discountedUnits = discountedUnits;
        this.specialPricePercentage = specialPricePercentage;
    }

    @Override
    public List<Item> calculatePrice(Map<Product, Item> items) {

        if (items == null) {
            return null;
        }
        if (!items.containsKey(applicableProduct)) {
            return new ArrayList<>();
        }

        Item item = items.get(applicableProduct);
        List<Item> productsWithDiscount = new ArrayList<Item>();

        if (IS_CUMULATIVE || !item.hasDiscountOrAllItemsUsedInDiscount()) {
            if (item.getAvailableQuantity() >= buyUnits) {
                int quantityToRemove = Math.floorDiv(item.getAvailableQuantity(), buyUnits);
                item.removeQuantityOnDiscount(quantityToRemove, quantityToRemove * buyUnits);
                productsWithDiscount.add(new Item(item.getProduct(), quantityToRemove * discountedUnits, specialPricePercentage));
            }
        }

        return productsWithDiscount;
    }


}

