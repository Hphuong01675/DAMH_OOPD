package ute.fit.model;

import java.time.LocalDateTime;

public class Payment {
    private String transactionID;
    private StatusPayment statusPayment;
    private LocalDateTime paymentDate;

    public Payment() {
    }

    public Payment(String transactionID, StatusPayment statusPayment, LocalDateTime paymentDate) {
        this.transactionID = transactionID;
        this.statusPayment = statusPayment;
        this.paymentDate = paymentDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public StatusPayment getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(StatusPayment statusPayment) {
        this.statusPayment = statusPayment;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
