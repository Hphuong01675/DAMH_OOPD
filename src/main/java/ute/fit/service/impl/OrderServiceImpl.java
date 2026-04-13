package ute.fit.service.impl;

import ute.fit.dao.IAccountDAO;
import ute.fit.dao.IBeverageDAO;
import ute.fit.dao.ICustomerDAO;
import ute.fit.dao.IOrderDAO;
import ute.fit.dao.IToppingDAO;
import ute.fit.dao.impl.AccountDAOImpl;
import ute.fit.dao.impl.BeverageDAOImpl;
import ute.fit.dao.impl.CustomerDAOImpl;
import ute.fit.dao.impl.OrderDAOImpl;
import ute.fit.dao.impl.ToppingDAOImpl;
import ute.fit.service.IOrderService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ute.fit.entity.AccountEntity;
import ute.fit.entity.BeverageEntity;
import ute.fit.entity.CustomerEntity;
import ute.fit.entity.OrderEntity;
import ute.fit.entity.OrderItemEntity;
import ute.fit.entity.StaffEntity;
import ute.fit.entity.ToppingEntity;
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
	private final IAccountDAO accountDAO = new AccountDAOImpl();
	private final ICustomerDAO customerDAO = new CustomerDAOImpl();
	private final IToppingDAO toppingDAO = new ToppingDAOImpl();
	private final IBeverageDAO beverageDAO = new BeverageDAOImpl();

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

	// Trong file: OrderServiceImpl.java

		@Override
		public void processOrder(Long orderId, Long baristaId) {
			// Truyền baristaId xuống hàm updateState
			updateState(orderId, "COMPLETE", null, baristaId);
		}

		@Override
		public void updateState(Long orderId, String action, String reason, Long baristaId) {
		    OrderEntity entity = orderDAO.findById(orderId);
		    if (entity == null) return;

		    try {
		        // Logic State Pattern để xử lý StateName
		        Order order = new Order();
		        
		        // Lấy state hiện tại từ Entity gán vào Model
		        Field stateField = OrderEntity.class.getDeclaredField("stateName");
		        stateField.setAccessible(true);
		        String currentStateName = (String) stateField.get(entity);
		        order.setState(ute.fit.model.state.OrderStateFactory.getState(currentStateName));

		        if ("COMPLETE".equalsIgnoreCase(action)) {
		                   order.proceed(); 
		            
		            // CẬP NHẬT BARISTA: Dùng Setter trực tiếp của Lombok
		            if (baristaId != null) {
		                ute.fit.entity.BaristaEntity barista = new ute.fit.entity.BaristaEntity();
		                barista.setId(baristaId);
		                entity.setBarista(barista); 
		            }
		            
		            // Cập nhật StateName vào Entity
		            stateField.set(entity, order.getCurrentState().getStateName());
		            
		        } else if ("CANCEL".equalsIgnoreCase(action)) {
		            order.cancel(reason);
		            stateField.set(entity, order.getCurrentState().getStateName());
		            
		            // Chỉ khi Cancel mới có thể cập nhật trạng thái thanh toán nếu cần
		            Field paymentField = OrderEntity.class.getDeclaredField("statusPayment");
		            paymentField.setAccessible(true);
		            paymentField.set(entity, order.getPaymentStatus());
		        }

		        // Thực hiện update xuống Database
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

	// Trong OrderServiceImpl.java
	@Override
	public List<OrderEntity> getPendingAndPaidOrdersToday() {
	    return orderDAO.findPendingAndPaidOrdersToday();
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
	
	@Override
	public Long saveOrder(Order order, UserDTO userDto) {
	    // 1. Tìm Account từ DB
	    Roles roleEnum = Roles.valueOf(userDto.getRole());
	    AccountEntity account = accountDAO.findActiveAccountByUsernameAndRole(userDto.getUsername(), roleEnum);

	    if (account == null) throw new RuntimeException("Tài khoản không hợp lệ");
	    if (order == null) throw new RuntimeException("Order không hợp lệ");

	    // 2. Khởi tạo OrderEntity với trạng thái PENDING
	    OrderEntity orderEntity = new OrderEntity();
	    Long orderId = order.getOrderId() != null ? order.getOrderId() : System.currentTimeMillis();
	    orderEntity.setOrderID(orderId);
	    orderEntity.setOrderDate(java.time.LocalDateTime.now());
	    orderEntity.setTotalAmount(order.calculateTotal());
	    
	    // Gán trạng thái từ State Pattern và Enum StatusPayment
	    orderEntity.setStateName("PENDING"); 
	    orderEntity.setStatusPayment(StatusPayment.PENDING); 
	    orderEntity.setStaff(toStaffEntity(userDto, account));

	    if (order.getCustomerId() != null) {
	        CustomerEntity customer = customerDAO.findById(order.getCustomerId());
	        orderEntity.setCustomer(customer);
	    }

	    // 3. Xử lý danh sách Item (Giải mã Decorator)
	    List<OrderItemEntity> itemEntities = new ArrayList<>();
	    for (OrderItem modelItem : order.getItems()) {
	        OrderItemEntity itemEntity = new OrderItemEntity();
	        itemEntity.setOrder(orderEntity);
	        
	        // Giải mã Product (Decorator) để lấy Beverage và Toppings
	        List<ToppingEntity> collectedToppings = new ArrayList<>();
	        Map<String, ToppingEntity> toppingCache = new HashMap<>();
	        Beverage baseBeverage = unwrapProduct(modelItem.getProduct(), collectedToppings, toppingCache);
	        
	        if (baseBeverage != null) {
	            // Lấy BeverageEntity từ DB dựa trên tên
	            BeverageEntity bevEntity = beverageDAO.findByName(baseBeverage.getName());
	            itemEntity.setBeverage(bevEntity);
	            
	            // Lấy thông tin Size, Sugar, Ice từ đối tượng Beverage gốc
	            itemEntity.setSize(baseBeverage.getSize());
	            itemEntity.setSugar(baseBeverage.getSugar());
	            itemEntity.setIce(baseBeverage.getIce());
	        }
	        
	        itemEntity.setToppings(collectedToppings);
	        
	        // Lấy thông tin lựa chọn và giá
	        itemEntity.setQuantity(modelItem.getQuantity());
	        itemEntity.setUnitPrice(modelItem.getUnitPrice()); // Giá đã bao gồm topping

	        itemEntities.add(itemEntity);
	    }
	    
	    orderEntity.setItems(itemEntities);
	    orderDAO.save(orderEntity); // Lưu toàn bộ nhờ CascadeType.ALL
	    order.setOrderId(orderId);
	    return orderId;
	}

	@Override
	public void handlePostPayment(Long orderId, Long customerId, String promoCode, StatusPayment paymentStatus, String orderStateName) {
		if (orderId == null) {
			return;
		}

		OrderEntity orderEntity = orderDAO.findById(orderId);
		if (orderEntity == null) {
			return;
		}

		orderEntity.setStatusPayment(paymentStatus != null ? paymentStatus : StatusPayment.PENDING);
		if (orderStateName != null && !orderStateName.isBlank()) {
			orderEntity.setStateName(orderStateName);
		}

		if (customerId != null) {
			CustomerEntity customer = customerDAO.findById(customerId);
			if (customer != null) {
				orderEntity.setCustomer(customer);
			}
		}

		orderDAO.update(orderEntity);
	}
	
	private StaffEntity toStaffEntity(UserDTO user, AccountEntity account) {
	    StaffEntity staff = new StaffEntity();
	    staff.setId(user.getId());
	    staff.setName(user.getFullName());
	    staff.setPhoneNumber(user.getPhone());
	    staff.setAccount(account); // Gán AccountEntity thực sự từ DB
	    return staff;
	}
	
	private Beverage unwrapProduct(Product product, List<ToppingEntity> collectedToppings, Map<String, ToppingEntity> toppingCache) {
		if (product instanceof ToppingDecorator decorator) {
	        String tName = decorator.getToppingName();
	        
	        // Kiểm tra xem Topping này đã được load lên chưa
	        ToppingEntity tEntity = toppingCache.get(tName);
	        if (tEntity == null) {
	            tEntity = toppingDAO.findByName(tName);
	            if (tEntity != null) {
	                toppingCache.put(tName, tEntity); // Lưu vào cache tạm
	            }
	        }

	        if (tEntity != null) {
	            collectedToppings.add(tEntity); // Add vào list (có thể add trùng đối tượng tEntity này nhiều lần)
	        }

	        return unwrapProduct(decorator.getProduct(), collectedToppings, toppingCache);
	    } 
	    
	    if (product instanceof Beverage beverage) {
	        return beverage;
	    }
	    return null;
	}
}
