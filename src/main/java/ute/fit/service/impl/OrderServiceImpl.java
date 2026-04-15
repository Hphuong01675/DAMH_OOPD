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
import ute.fit.service.ICustomerService;
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

	
	private final ICustomerService customerService = new CustomerServiceImpl();
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
		public void processOrder(Long orderId, Long baristaId) {
			updateState(orderId, "COMPLETE", null, baristaId);
		}
		@Override
		public void cancelOrder(Long orderId, String reason, Long baristaId) {
		    updateState(orderId, "CANCEL", reason, baristaId);
		}

		@Override
		public void updateState(Long orderId, String action, String reason, Long baristaId) {
		    // 1. Lấy dữ liệu thô từ Database
		    OrderEntity entity = orderDAO.findById(orderId);
		    if (entity == null) return;

		    // 2. Ghi nhận Barista tiếp nhận đơn (Thực hiện trước khi chuyển State)
		    if (baristaId != null) {
		        ute.fit.entity.BaristaEntity barista = new ute.fit.entity.BaristaEntity();
		        barista.setId(baristaId);
		        entity.setBarista(barista);
		        orderDAO.update(entity);
		    }

		    // 3. Chuẩn bị Context
		    Order order = new Order();
		    order.setOrderId(entity.getOrderID());
		    order.setPaymentStatus(entity.getStatusPayment());
		    
		    order.setState(ute.fit.model.state.OrderStateFactory.getState(entity.getStateName()));

		    // 4. Kích hoạt State Pattern
		    if ("COMPLETE".equalsIgnoreCase(action)) {
		        order.proceed(this.orderDAO); 
		    } 
		    else if ("CANCEL".equalsIgnoreCase(action)) {
		        order.cancel(reason, this.orderDAO);
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

		int[] hourlyCounts = new int[6];
		LocalDateTime now = LocalDateTime.now();
		int maxCount = 0;

		if (dates != null) {
			for (LocalDateTime date : dates) {
				// Tính khoảng cách giờ từ lúc đặt đơn đến hiện tại
				long hoursBetween = java.time.Duration.between(date, now).toHours();

				// Nếu đơn nằm trong phạm vi 6 tiếng vừa qua
				if (hoursBetween >= 0 && hoursBetween < 6) {
					int index = 5 - (int) hoursBetween;
					hourlyCounts[index]++;
					if (hourlyCounts[index] > maxCount) {
						maxCount = hourlyCounts[index];
					}
				}
			}
		}

		// Đóng gói thành danh sách Map để View
		List<Map<String, Object>> chartData = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			Map<String, Object> bar = new HashMap<>();
			bar.put("value", hourlyCounts[i]);

			// Tính phần trăm để set chiều cao của cột
			int percentage = (maxCount == 0) ? 0 : (int) Math.round((double) hourlyCounts[i] / maxCount * 100);

			if (hourlyCounts[i] == 0) {
				bar.put("percentage", 5); 
				bar.put("isMax", false);
			} else {
				bar.put("percentage", Math.max(20, percentage)); 
				bar.put("isMax", hourlyCounts[i] == maxCount);
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
	    
	    // Gán trạng thái từ State Pattern và Enum StatusPaymen
	    orderEntity.setStateName("PENDING"); 
	    orderEntity.setStatusPayment(StatusPayment.PENDING); 
	    orderEntity.setStaff(toStaffEntity(userDto, account));

	    if (order.getCustomerId() != null) {
	        CustomerEntity customer = customerDAO.findById(order.getCustomerId());
	        orderEntity.setCustomer(customer);
	    }

	    // 3. Xử lý danh sách Item
	    List<OrderItemEntity> itemEntities = new ArrayList<>();
	    for (OrderItem modelItem : order.getItems()) {
	        OrderItemEntity itemEntity = new OrderItemEntity();
	        itemEntity.setOrder(orderEntity);

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
	    orderDAO.save(orderEntity);
	    order.setOrderId(orderId);
	    return orderId;
	}

	@Override
	public void handlePostPayment(Long orderId, Long customerId, String promoCode, StatusPayment paymentStatus, String orderStateName) {
	    if (orderId == null) return;

	    OrderEntity orderEntity = orderDAO.findById(orderId);
	    if (orderEntity == null) return;

	    // Cập nhật trạng thái
	    orderEntity.setStatusPayment(paymentStatus != null ? paymentStatus : StatusPayment.PENDING);
	    if (orderStateName != null && !orderStateName.isBlank()) {
	        orderEntity.setStateName(orderStateName);
	    }

	    if (customerId != null) {
	        CustomerEntity customer = customerDAO.findById(customerId);
	        if (customer != null) {
	            orderEntity.setCustomer(customer);
	            
	            // Logic cộng điểm khi thanh toán thành công
	            boolean isSuccess = paymentStatus != null && 
	                ("SUCCESS".equalsIgnoreCase(paymentStatus.name()) || "PAID".equalsIgnoreCase(paymentStatus.name()));
	            
	            if (isSuccess) {
	                double finalAmount = orderEntity.getTotalAmount(); 
	                
	                int earnedPoints = (int) (finalAmount / 10000);
	                if (earnedPoints > 0) {
	                    customerService.addCustomerPoints(customerId, earnedPoints);
	                }
	            }
	        }
	    }
	    orderDAO.update(orderEntity);
	}
	
	private StaffEntity toStaffEntity(UserDTO user, AccountEntity account) {
	    StaffEntity staff = new StaffEntity();
	    staff.setId(user.getId());
	    staff.setName(user.getFullName());
	    staff.setPhoneNumber(user.getPhone());
	    staff.setAccount(account); 
	    return staff;
	}
	
	private Beverage unwrapProduct(Product product, List<ToppingEntity> collectedToppings, Map<String, ToppingEntity> toppingCache) {
		if (product instanceof ToppingDecorator decorator) {
	        String tName = decorator.getToppingName();
	        
	        
	        ToppingEntity tEntity = toppingCache.get(tName);
	        if (tEntity == null) {
	            tEntity = toppingDAO.findByName(tName);
	            if (tEntity != null) {
	                toppingCache.put(tName, tEntity); 
	            }
	        }

	        if (tEntity != null) {
	            collectedToppings.add(tEntity); 
	        }

	        return unwrapProduct(decorator.getProduct(), collectedToppings, toppingCache);
	    } 
	    
	    if (product instanceof Beverage beverage) {
	        return beverage;
	    }
	    return null;
	}
}
