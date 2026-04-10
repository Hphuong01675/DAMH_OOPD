package ute.fit.model;

public abstract class ToppingDecorator implements Product {
    protected Product product;
    protected String toppingName;
    protected double toppingPrice;

    public ToppingDecorator(Product product, String name, double price) {
        this.product = product;
        this.toppingName = name;
        this.toppingPrice = price;
    }

    @Override
    public double getPrice() {
        return product.getPrice() + toppingPrice;
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " + " + toppingName;
    }

	public Product getProduct() {
		return product;
	}

	public String getToppingName() {
		return toppingName;
	}

	public double getToppingPrice() {
		return toppingPrice;
	}
}
