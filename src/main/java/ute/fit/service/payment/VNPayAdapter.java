package ute.fit.service.payment;

public class VNPayAdapter {
    private IVNPayService vnPay;

    public boolean executePayment(double amount) {
        // Khoi tao adaptee cu the voi tham so tu flow hien tai
        String transactionId = "TXN" + System.currentTimeMillis();
        this.vnPay = new VNPaySimulator(amount, transactionId);

        // Goi ham cua giao dien cu ma Adapter dong goi
        this.vnPay.processVNPayPayment();

        // Mo phong ket qua gateway thanh cong
        return true;
    }
}
