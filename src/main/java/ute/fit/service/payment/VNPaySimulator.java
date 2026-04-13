package ute.fit.service.payment;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VNPaySimulator implements IVNPayService {

    private final String vnpTmnCode = "DUMMY_TMN";
    private final String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    private final double amountToPay;
    private final String transactionId;

    public VNPaySimulator(double amountToPay, String transactionId) {
        this.amountToPay = amountToPay;
        this.transactionId = transactionId;
    }

    @Override
    public void processVNPayPayment() {
        try {
            long vnpAmount = (long) (this.amountToPay * 100);

            StringBuilder requestData = new StringBuilder();
            requestData.append("vnp_Amount=").append(vnpAmount);
            requestData.append("&vnp_Command=pay");
            requestData.append("&vnp_CreateDate=").append(System.currentTimeMillis());
            requestData.append("&vnp_CurrCode=VND");
            requestData.append("&vnp_IpAddr=127.0.0.1");
            requestData.append("&vnp_Locale=vn");
            requestData.append("&vnp_OrderInfo=")
                    .append(URLEncoder.encode("Thanh toan don hang " + transactionId, StandardCharsets.UTF_8));
            requestData.append("&vnp_OrderType=250000");
            requestData.append("&vnp_ReturnUrl=")
                    .append(URLEncoder.encode("http://localhost:8080/vnpay_return", StandardCharsets.UTF_8));
            requestData.append("&vnp_TmnCode=").append(vnpTmnCode);
            requestData.append("&vnp_TxnRef=").append(transactionId);
            requestData.append("&vnp_Version=2.1.0");

            String secureHash = Integer.toHexString(requestData.toString().hashCode());
            String paymentUrl = vnpUrl + "?" + requestData + "&vnp_SecureHash=" + secureHash;

            System.out.println("[VNPAY-SIM] Build request for tx=" + transactionId);
            System.out.println("[VNPAY-SIM] Amount(vnp format)=" + vnpAmount);
            System.out.println("[VNPAY-SIM] Redirect URL=" + paymentUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
