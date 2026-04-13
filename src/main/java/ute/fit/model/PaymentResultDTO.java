package ute.fit.model;

public class PaymentResultDTO {
    private Long orderId;
    private String transactionId;
    private String paymentStatus;
    private String orderState;
    private String paymentMethod;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private Long staffId;
    private String staffName;
    private String promoType;
    private double subtotal;
    private double discount;
    private double finalTotal;

    public PaymentResultDTO() {
    }

    public PaymentResultDTO(Long orderId, String transactionId, String paymentStatus, String orderState, String paymentMethod,
                            Long customerId, String customerName, String customerPhone,
                            Long staffId, String staffName, String promoType,
                            double subtotal, double discount, double finalTotal) {
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.orderState = orderState;
        this.paymentMethod = paymentMethod;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.staffId = staffId;
        this.staffName = staffName;
        this.promoType = promoType;
        this.subtotal = subtotal;
        this.discount = discount;
        this.finalTotal = finalTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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
