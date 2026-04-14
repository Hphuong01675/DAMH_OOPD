package ute.fit.model;

import java.util.ArrayList;
import java.util.List;

import ute.fit.dao.IOrderDAO;
import ute.fit.model.state.CancelledState;
import ute.fit.model.state.OrderState;
import ute.fit.model.state.PendingState;

/**
 * Lớp Order đóng vai trò là Context trong State Pattern.
 * Nó duy trì tham chiếu đến một đối tượng OrderState hiện tại để xác định hành vi.
 */
public class Order {
    private Long orderId;
    private List<OrderItem> items = new ArrayList<>();
    private Long customerId;
    
    // Cầu nối tới State Pattern
    private OrderState currentState;

    // Cầu nối tới Payment
    private StatusPayment paymentStatus;
    
    // Lý do hủy đơn
    private String cancelReason;

    public Order() {
        // Mặc định khi tạo mới là trạng thái Chờ (Pending)
        this.currentState = new PendingState();
        this.paymentStatus = StatusPayment.PENDING;
    }
    
    
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

    
    /**
     * Xử lý bước tiếp theo của đơn hàng (Barista bấm hoàn thành).
     */
    public void proceed(IOrderDAO orderDAO) {
        if (currentState != null) {
            // Truyền orderDAO xuống cho State hiện tại tự xử lý
            currentState.handleRequest(this, orderDAO);
        }
    }

    /**
     * Hủy đơn hàng.
     */
    public void cancel(String reason, IOrderDAO orderDAO) {
        this.cancelReason = reason;
        if (currentState != null) {
            currentState.cancel(this, reason, orderDAO);
        } else {
            // Nếu chưa có state, chuyển thẳng sang CancelledState
            this.setState(new CancelledState());
        }
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
