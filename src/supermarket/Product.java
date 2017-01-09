package supermarket;

/**
 * Created by Nuno Maggiolly on 08-01-2017.
 */
public class Product {

	protected long id;
	protected String name;
	protected float price;

	public Product(long id, String name, float price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
