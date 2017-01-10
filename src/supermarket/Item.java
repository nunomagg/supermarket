package supermarket;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class Item {

    private Product product;
    private int quantity = 0;
    private float discount;

    private int usedUnitsInDiscount = 0;
    private int originalQuantity;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.originalQuantity = quantity;
        this.discount = 0;
    }

    public Item(Product product, int quantity, float discount) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
        this.originalQuantity = quantity;
    }

    public float calculatePrice() {
        return product.getPrice() * quantity - (product.getPrice() * quantity * (discount / 100));
    }

    public void addQuantity(int q) {
        quantity = quantity + q;
        originalQuantity = quantity;
    }

    public void removeQuantity(int q) {
        quantity = quantity - q;
        originalQuantity = quantity;
    }

    /**
     * This method should be used when the removal of a quantity was due to a discount on the item.
     * @param quantity
     * @param usedUnitsInDiscount
     */
    public void removeQuantityOnDiscount(int quantity, int usedUnitsInDiscount ){
        this.quantity = this.quantity - quantity;
        this.usedUnitsInDiscount += usedUnitsInDiscount;
    }

    /**
     * Set quantity of this item as a Already used quantity in a discount/sale
     * @param quantity
     */
    public void applyDiscountTo(int quantity){
        usedUnitsInDiscount += quantity;
    }

    /**
     * Checks if all units of this product were already used in another discount or
     * If it a product with discount
     *
     * @return true if one is valid ( hasDiscount() || usedUnitsInDiscount >= originalQuantity )
     */
    public boolean hasDiscountOrAllItemsUsedInDiscount() {
        return hasDiscount() || usedUnitsInDiscount >= originalQuantity;
    }

    public  boolean hasDiscount(){
        return discount != 0;
    }

    @Override
    public String toString() {
        return "product = " + product.getName() +
                " | quantity = " + quantity +
                " | price = " + calculatePrice();
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity(){
        return originalQuantity-usedUnitsInDiscount;
    }
}