package ute.fit.model;

import java.util.ArrayList;
import java.util.List;

import ute.fit.entity.CustomerEntity;

public class Order {
	private Long orderId;
	private List<OrderItem> items = new ArrayList<>();
	private CustomerEntity customer;

	// Cầu nối tới State Pattern
	private OrderState currentState;

	// Cầu nối tới Payment (Enum trạng thái thanh toán)
	private StatusPayment paymentStatus;

	public Order() {
		// Trạng thái mặc định khi mới tạo
		this.currentState = new PendingState();
		this.paymentStatus = StatusPayment.PENDING;
	}

	// Logic nghiệp vụ chính
	public void addItem(OrderItem item) {
		// Chỉ cho phép thêm món khi đơn hàng đang ở trạng thái chờ (Pending)
		items.add(item);
	}

	public double calculateTotal() {
		return items.stream().mapToDouble(OrderItem::getSubTotal).sum();
	}

	// --- CÁC HÀNH ĐỘNG CHUYỂN TRẠNG THÁI (STATE PATTERN) ---

	public void setState(OrderState state) {
		this.currentState = state;
	}

	/*
	 * public void proceed() { // Ủy thác việc chuyển trạng thái cho đối tượng State
	 * hiện tại currentState.next(this); }
	 * 
	 * public void cancel() { currentState.cancel(this); }
	 */

	// --- GETTERS & SETTERS ---
	public List<OrderItem> getItems() {
		return items;
	}

	public OrderState getCurrentState() {
		return currentState;
	}

	public StatusPayment getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(StatusPayment paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
