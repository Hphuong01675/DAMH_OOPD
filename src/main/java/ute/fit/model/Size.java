package ute.fit.model;

public enum Size {
	S(0.0),
	M(5.0),
	L(8.0);
	
	private final double extraPrice;

    Size(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
