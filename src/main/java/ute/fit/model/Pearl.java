package ute.fit.model;

public class Pearl extends ToppingDecorator {
	public Pearl(Product product, String name, double price) {
        super(product, name, price); 
    }

	@Override
	public String getDescription() {
		return product.getDescription() + " + Pearl";
	}

	@Override
	public double getPrice() {
		return product.getPrice() + toppingPrice;
	}
}