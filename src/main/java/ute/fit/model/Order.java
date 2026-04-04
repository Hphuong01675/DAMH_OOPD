package ute.fit.model;

import java.util.ArrayList;
import java.util.List;
import ute.fit.entity.CustomerEntity;

/**
 * Lớp Order đóng vai trò là Context trong State Pattern.
 * Nó duy trì tham chiếu đến một đối tượng OrderState hiện tại để xác định hành vi.
 */
public class Order {
    private Long orderId;
    private List<OrderItem> items = new ArrayList<>();
    private CustomerEntity customer;
    
    // Cầu nối tới State Pattern (Trạng thái hiện tại)
    private OrderState currentState;

    // Cầu nối tới Payment (Enum trạng thái thanh toán)
    private StatusPayment paymentStatus;
    
    // Lý do hủy đơn (Dùng cho CancelledState)
    private String cancelReason;

    public Order() {
        // Mặc định khi tạo mới là trạng thái Chờ (Pending)
        this.currentState = new PendingState();
        this.paymentStatus = StatusPayment.PENDING;
    }

    // --- LOGIC NGHIỆP VỤ CHÍNH ---

    public void addItem(OrderItem item) {
        // Chỉ cho phép thêm món khi đơn hàng đang ở trạng thái chờ (Pending)
        if (currentState instanceof PendingState) {
            items.add(item);
        } else {
            System.out.println("Không thể thêm món vào đơn hàng đã xử lý hoặc đã hủy.");
        }
    }

    public double calculateTotal() {
        // Sử dụng Stream để tính tổng tiền từ danh sách OrderItem
        return items.stream().mapToDouble(OrderItem::getSubTotal).sum();
    }

    // --- CÁC HÀNH ĐỘNG CHUYỂN TRẠNG THÁI (STATE PATTERN) ---

    /**
     * Chuyển sang trạng thái tiếp theo (Xử lý đơn hàng)
     */
    public void proceed() {
        // Ủy thác (delegate) việc xử lý cho đối tượng State hiện tại
        currentState.handleRequest(this);
    }

    /**
     * Hủy đơn hàng với lý do cụ thể
     * @param reason Lý do hủy đơn
     */
    public void cancel(String reason) {
        this.cancelReason = reason;
        // Ủy thác việc hủy cho đối tượng State hiện tại
        currentState.cancel(this, reason);
    }

    // --- GETTERS & SETTERS ---

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public OrderState getCurrentState() {
        return currentState;
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public StatusPayment getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(StatusPayment paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}