package ute.fit.service.payment;

public class VNPayAdapter implements IVNPayGateway {
    private IVNPayService vnPay;

    @Override
    public boolean executePayment(double amount, String transactionId) {
        try {
            // Adapter chuyen doi from target API -> adaptee API
            this.vnPay = new VNPaySimulator(amount, transactionId);
            this.vnPay.processVNPayPayment();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
