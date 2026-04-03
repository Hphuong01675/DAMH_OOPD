package ute.fit.model;

public class Jelly extends ToppingDecorator {
	public Jelly(Product product, String name, double price) {
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
