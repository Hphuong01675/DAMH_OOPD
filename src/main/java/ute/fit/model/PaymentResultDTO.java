package ute.fit.model;

public class PaymentResultDTO {
    private String transactionId;
    private String paymentStatus;
    private String orderState;
    private String paymentMethod;
    private String promoType;
    private double subtotal;
    private double discount;
    private double finalTotal;

    public PaymentResultDTO() {
    }

    public PaymentResultDTO(String transactionId, String paymentStatus, String orderState, String paymentMethod,
                            String promoType, double subtotal, double discount, double finalTotal) {
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.orderState = orderState;
        this.paymentMethod = paymentMethod;
        this.promoType = promoType;
        this.subtotal = subtotal;
        this.discount = discount;
        this.finalTotal = finalTotal;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }
}
