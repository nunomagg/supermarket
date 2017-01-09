package discountRules;

import supermarket.Product;

import java.util.Set;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public abstract class AbstractMultipleSaleRule implements SalesRule {

    protected Set<Product> applicableProducts;
    protected String salesText = "";

    @Override
    public boolean appliesToThisProduct(Product p) {
        return applicableProducts.contains(p);
    }
}
