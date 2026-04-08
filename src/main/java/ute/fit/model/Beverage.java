package ute.fit.model;

public class Beverage implements Product {
	private String name;
    private double basePrice;
    private Size size;
    private SugarLevel sugar;
    private IceLevel ice;

    // Constructor để Builder gọi
    // package-private: chi class cung package moi duoc phep new
    Beverage(BeverageBuilder builder) {
        this.name = builder.getName();
        this.basePrice = builder.getBasePrice();
        this.size = builder.getSize();
        this.sugar = builder.getSugar();
        this.ice = builder.getIce();
    }

    @Override
    public double getPrice() {
        // Giá gốc từ DB + extraPrice của Size từ Enum (hoặc DB)
        return basePrice + size.getExtraPrice();
    }

    @Override
    public String getDescription() {
        return String.format("%s (Size: %s, Đường: %s, Đá: %s)", name, size, sugar, ice);
    }
}
