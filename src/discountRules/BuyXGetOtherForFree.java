package discountRules;

import supermarket.Product;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class BuyXGetOtherForFree extends BuyXForSpecialPrice {

    public BuyXGetOtherForFree(Product product, int buyUnits, int freeUnits) {
        super(product, buyUnits, freeUnits, 100);
    }

}
