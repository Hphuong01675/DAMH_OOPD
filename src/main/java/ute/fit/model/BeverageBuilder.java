package ute.fit.model;

public class BeverageBuilder {
	private String name;
	private double basePrice;
	private Size size = Size.S; // Mặc định
	private SugarLevel sugar = SugarLevel.S50;
	private IceLevel ice = IceLevel.Normal;

	// Truyền Entity vào để lấy thông tin gốc từ DB
	public BeverageBuilder(BeverageDTO beverageDTO) {
		this.name = beverageDTO.getName();
		this.basePrice = beverageDTO.getBasePrice();
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
