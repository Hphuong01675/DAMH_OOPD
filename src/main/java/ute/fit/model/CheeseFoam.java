package ute.fit.model;

public class CheeseFoam extends ToppingDecorator {
	public CheeseFoam(Product product, String name, double price) {
        super(product, name, price); 
    }

	@Override
	public String getDescription() {
		return product.getDescription() + " + Cheese Foam";
	}

	@Override
	public double getPrice() {
		return product.getPrice() + toppingPrice;
	}
}
