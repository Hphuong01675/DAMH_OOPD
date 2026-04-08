package ute.fit.model;

public class Caramel extends ToppingDecorator {
	public Caramel(Product product, String name, double price) {
        super(product, name, price); 
    }

	@Override
	public String getDescription() {
		return product.getDescription() + " + Caramel";
	}

	@Override
	public double getPrice() {
		return product.getPrice() + toppingPrice;
	}
}
