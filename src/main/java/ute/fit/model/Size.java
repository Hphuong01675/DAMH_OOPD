package ute.fit.model;

public enum Size {
	S(0.0),
	M(5000),
	L(8000);
	
	private final double extraPrice;

    Size(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
