package discountRules;

import supermarket.Product;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public abstract class AbstractSingleSaleRule implements SalesRule {

    protected Product applicableProduct;
    protected String salesText = "";

    @Override
    public boolean appliesToThisProduct(Product p) {
        return applicableProduct.equals(p);
    }
}

