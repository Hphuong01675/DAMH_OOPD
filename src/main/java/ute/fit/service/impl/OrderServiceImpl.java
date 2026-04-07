package ute.fit.service.impl;

import ute.fit.dao.IOrderDAO;
import ute.fit.dao.impl.OrderDAOImpl;
import ute.fit.service.IOrderService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import ute.fit.entity.OrderEntity;
import ute.fit.model.*;
import ute.fit.model.state.OrderStateFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements IOrderService {
	private final IOrderDAO orderDAO = new OrderDAOImpl();

	@Override
	public Map<String, Object> getStaffDailyStats(Long staffId) {
		LocalDate today = LocalDate.now();
		Map<String, Object> stats = new HashMap<>();

		// Thống kê hỗ trợ kết ca cho Staff
		stats.put("revenue", orderDAO.calculateDailyRevenueByStaff(staffId, today));
		stats.put("completedCount", orderDAO.countOrdersByStaffAndStatus(staffId, today, "Completed"));
		stats.put("cancelledCount", orderDAO.countOrdersByStaffAndStatus(staffId, today, "Cancelled"));

		return stats;
	}

	@Override
	public void processOrder(Long orderId) {
		// Gọi updateState với hành động mặc định là COMPLETE
		updateState(orderId, "COMPLETE", null);
	}

	@Override
	public void updateState(Long orderId, String action, String reason) {
		OrderEntity entity = orderDAO.findById(orderId);
		if (entity == null)
			return;

		try {
			Order order = new Order();

			// Dùng Reflection đọc trường "orderID" và "stateName"
			Field idField = OrderEntity.class.getDeclaredField("orderID");
			idField.setAccessible(true);
			order.setOrderId((Long) idField.get(entity));

			Field stateField = OrderEntity.class.getDeclaredField("stateName");
			stateField.setAccessible(true);
			String currentStateName = (String) stateField.get(entity);

			// Khởi tạo trạng thái từ Model Factory
			order.setState(OrderStateFactory.getState(currentStateName));

			// Thực thi logic State Pattern
			// ĐOẠN CẬP NHẬT (Rất sạch)
			if ("COMPLETE".equalsIgnoreCase(action)) {
				order.proceed();
			} else if ("CANCEL".equalsIgnoreCase(action)) {
				order.cancel(reason);
			}

			// Đồng bộ trạng thái xử lý
			stateField.set(entity, order.getCurrentState().getStateName());

			// Đồng bộ trạng thái thanh toán (Lấy từ Model sau khi đã proceed/cancel)
			Field paymentField = OrderEntity.class.getDeclaredField("statusPayment");
			paymentField.setAccessible(true);
			paymentField.set(entity, order.getPaymentStatus());

			orderDAO.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrderEntity> getPendingOrdersToday() {
		List<OrderEntity> allOrders = orderDAO.findAll();
		LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);

		return allOrders.stream().filter(o -> {
			try {
				// Dùng Reflection để lọc dữ liệu trực tiếp từ các trường private
				Field dateField = OrderEntity.class.getDeclaredField("orderDate");
				dateField.setAccessible(true);
				LocalDateTime orderDate = (LocalDateTime) dateField.get(o);

				Field stateField = OrderEntity.class.getDeclaredField("stateName");
				stateField.setAccessible(true);
				String state = (String) stateField.get(o);

				return orderDate != null && "PENDING".equalsIgnoreCase(state) && orderDate.isAfter(startOfDay);
			} catch (Exception e) {
				return false;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public List<Object[]> getPendingOrdersDataToday() {
		return orderDAO.findPendingOrdersDataToday();
	}

	@Override
	public Object[] getBaristaStatsToday(String username) {
		if (username == null || username.trim().isEmpty()) {
			return new Object[] { 0.0, 0L, 0L };
		}
		return orderDAO.getBaristaStatsToday(username);
	}

	@Override
	public List<Object[]> getOrdersByBaristaUsernameToday(String username) {
		if (username == null || username.trim().isEmpty()) {
			return null;
		}
		return orderDAO.getOrdersByBaristaUsernameToday(username);
	}

	@Override
	public List<Object[]> getTopDrinksByBaristaToday(String username) {
		if (username == null || username.trim().isEmpty())
			return null;
		return orderDAO.getTopDrinksByBaristaToday(username);
	}

	@Override
	public List<Map<String, Object>> getBaristaChartDataToday(String username) {
		if (username == null || username.trim().isEmpty())
			return new ArrayList<>();

		List<LocalDateTime> dates = orderDAO.getOrderDatesByBaristaToday(username);

		// Tạo 6 nhóm (Đại diện cho 6 cột trên JSP - 6 giờ làm việc gần nhất)
		int[] hourlyCounts = new int[6];
		LocalDateTime now = LocalDateTime.now();
		int maxCount = 0;

		if (dates != null) {
			for (LocalDateTime date : dates) {
				// Tính khoảng cách giờ từ lúc đặt đơn đến hiện tại
				long hoursBetween = java.time.Duration.between(date, now).toHours();

				// Nếu đơn nằm trong phạm vi 6 tiếng vừa qua
				if (hoursBetween >= 0 && hoursBetween < 6) {
					// hoursBetween = 0 (giờ hiện tại) -> Nằm ở cột cuối cùng (index 5)
					// hoursBetween = 5 (5 giờ trước) -> Nằm ở cột đầu tiên (index 0)
					int index = 5 - (int) hoursBetween;
					hourlyCounts[index]++;
					if (hourlyCounts[index] > maxCount) {
						maxCount = hourlyCounts[index];
					}
				}
			}
		}

		// Đóng gói thành danh sách Map để View (JSP) có thể vẽ dễ dàng
		List<Map<String, Object>> chartData = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			Map<String, Object> bar = new HashMap<>();
			bar.put("value", hourlyCounts[i]);

			// Tính phần trăm để set chiều cao của cột (Tránh chia cho 0)
			int percentage = (maxCount == 0) ? 0 : (int) Math.round((double) hourlyCounts[i] / maxCount * 100);

			if (hourlyCounts[i] == 0) {
				bar.put("percentage", 5); // Cho 5% để cột hiển thị 1 mấu nhỏ cho đẹp UI
				bar.put("isMax", false);
			} else {
				bar.put("percentage", Math.max(20, percentage)); // Set min 20% để hover chữ vào không bị lỗi
				bar.put("isMax", hourlyCounts[i] == maxCount); // Để đổi màu cột cao nhất
			}
			chartData.add(bar);
		}

		return chartData;
	}
}
