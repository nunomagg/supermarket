package discountRules;

import supermarket.Item;
import supermarket.Product;

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

                // the cast to int is used to get only the integer value of the operation
                int quantityToRemove = (int) (item.getAvailableQuantity() * (((float)discountedUnits)/ ((float) buyUnits)));
                int nrDiscountedProducts = Math.floorDiv(item.getAvailableQuantity(), buyUnits);

                removeItemQuantityOnDiscount(items,item,quantityToRemove, nrDiscountedProducts * buyUnits);
                productsWithDiscount.add(new Item(item.getProduct(), nrDiscountedProducts * discountedUnits, specialPricePercentage));
            }
        }

        return productsWithDiscount;
    }

}

