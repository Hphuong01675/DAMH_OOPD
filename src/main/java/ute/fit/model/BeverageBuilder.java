package ute.fit.model;

import ute.fit.entity.BeverageEntity;

public class BeverageBuilder {
	private String name;
	private double basePrice;
	private Size size = Size.S; // Mặc định
	private SugarLevel sugar = SugarLevel.S50;
	private IceLevel ice = IceLevel.Normal;

	// Truyền Entity vào để lấy thông tin gốc từ DB
	public BeverageBuilder(BeverageEntity entity) {
		this.name = entity.getName();
		this.basePrice = entity.getBasePrice();
	}

	public BeverageBuilder setSize(Size size) {
		this.size = size;
		return this;
	}

	public BeverageBuilder setSugar(SugarLevel sugar) {
		this.sugar = sugar;
		return this;
	}

	public BeverageBuilder setIce(IceLevel ice) {
		this.ice = ice;
		return this;
	}

	public String getName() {
		return name;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public Size getSize() {
		return size;
	}

	public SugarLevel getSugar() {
		return sugar;
	}

	public IceLevel getIce() {
		return ice;
	}

	public Product build() {
		return new Beverage(this);
	}
}
