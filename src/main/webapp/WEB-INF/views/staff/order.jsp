<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chip3Chip - Staff Order Builder</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        :root {
            --primary-color: #a74880;
            --secondary-color: #f5e6d3;
            --dark-text: #2d2d2d;
            --light-text: #666;
            --green-accent: #4caf50;
            --warning-color: #ff9800;
            --success-color: #66bb6a;
            --border-color: #e0e0e0;
            --shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            --shadow-md: 0 8px 16px rgba(0, 0, 0, 0.15);
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            color: var(--dark-text);
            line-height: 1.6;
        }

        .container {
            display: flex;
            min-height: 100vh;
            flex-direction: column;
        }

        .main-wrapper {
            display: flex;
            flex: 1;
        }

        /* HEADER STYLES */
        .header {
            background: linear-gradient(135deg, var(--primary-color) 0%, #8b3f70 100%);
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: var(--shadow);
            position: sticky;
            top: 0;
            z-index: 100;
        }

        .header-left {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .logo {
            font-size: 1.8rem;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .header-title {
            font-size: 1.2rem;
            font-weight: 500;
        }

        .header-center {
            flex: 1;
            display: flex;
            justify-content: center;
            margin: 0 2rem;
        }

        .search-box {
            background: rgba(255, 255, 255, 0.2);
            border: 1px solid rgba(255, 255, 255, 0.3);
            padding: 0.7rem 1.2rem;
            border-radius: 25px;
            color: white;
            width: 300px;
            transition: all 0.3s ease;
        }

        .search-box::placeholder {
            color: rgba(255, 255, 255, 0.7);
        }

        .search-box:focus {
            outline: none;
            background: rgba(255, 255, 255, 0.3);
            border-color: white;
        }

        .header-right {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .header-icon {
            cursor: pointer;
            font-size: 1.3rem;
            transition: transform 0.3s ease;
        }

        .header-icon:hover {
            transform: scale(1.1);
        }

        .clock {
            font-size: 0.9rem;
            font-weight: 500;
        }

        .profile-pic {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: var(--secondary-color);
            border: 2px solid white;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            font-weight: bold;
            color: var(--primary-color);
        }

        /* SIDEBAR STYLES */
        .sidebar {
            width: 200px;
            background: white;
            padding: 2rem 1.5rem;
            box-shadow: 2px 0 8px rgba(0, 0, 0, 0.05);
            min-height: 100%;
        }

        .sidebar h2 {
            color: var(--primary-color);
            font-size: 1.1rem;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .sidebar ul {
            list-style: none;
        }

        .sidebar li {
            padding: 0.8rem 1rem;
            margin-bottom: 0.5rem;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.95rem;
            color: var(--light-text);
        }

        .sidebar li:hover {
            background: var(--secondary-color);
            color: var(--primary-color);
            transform: translateX(5px);
        }

        .sidebar li.active {
            background: var(--primary-color);
            color: white;
            font-weight: 500;
        }

        /* MAIN CONTENT */
        .content {
            flex: 1;
            padding: 2rem;
            background: #f8f8f8;
        }

        .footer {
            background: #f0f0f0;
            padding: 1.5rem 2rem;
            text-align: center;
            color: var(--light-text);
            border-top: 1px solid var(--border-color);
            font-size: 0.85rem;
        }

        /* MENU & ORDER BUILDER STYLES */
        .menu-header {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-bottom: 2rem;
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: var(--shadow);
        }

        .menu-header h1 {
            color: var(--dark-text);
            font-size: 1.5rem;
            margin: 0;
        }

        .category-buttons {
            display: flex;
            gap: 1rem;
            overflow-x: auto;
            flex: 1;
            padding: 0 1rem;
        }

        .category-btn {
            padding: 0.6rem 1.5rem;
            border: 2px solid var(--border-color);
            background: white;
            color: var(--dark-text);
            border-radius: 25px;
            cursor: pointer;
            transition: all 0.3s ease;
            white-space: nowrap;
            font-weight: 500;
            font-size: 0.9rem;
        }

        .category-btn:hover {
            border-color: var(--primary-color);
            color: var(--primary-color);
        }

        .category-btn.active {
            background: var(--primary-color);
            border-color: var(--primary-color);
            color: white;
        }

        .main-container {
            display: grid;
            grid-template-columns: 1fr 300px;
            gap: 2rem;
            margin-bottom: 2rem;
        }

        /* PRODUCT GRID */
        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 1.5rem;
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: var(--shadow);
        }

        .product-card {
            border: 1px solid var(--border-color);
            border-radius: 10px;
            overflow: hidden;
            transition: all 0.3s ease;
            cursor: pointer;
            display: flex;
            flex-direction: column;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow-md);
            border-color: var(--primary-color);
        }

        .product-image {
            width: 100%;
            height: 180px;
            background: linear-gradient(135deg, #f5e6d3, #e8d4c0);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 3rem;
            overflow: hidden;
        }

        .product-info {
            padding: 1rem;
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .product-category {
            font-size: 0.75rem;
            color: var(--primary-color);
            font-weight: 600;
            text-transform: uppercase;
            margin-bottom: 0.3rem;
        }

        .product-name {
            font-size: 1rem;
            font-weight: 600;
            color: var(--dark-text);
            margin-bottom: 0.3rem;
        }

        .product-description {
            font-size: 0.8rem;
            color: var(--light-text);
            line-height: 1.4;
            flex: 1;
            margin-bottom: 0.8rem;
        }

        .product-price {
            font-size: 1.1rem;
            font-weight: 700;
            color: var(--primary-color);
        }

        .product-actions {
            display: flex;
            gap: 0.5rem;
        }

        .btn-add {
            flex: 1;
            padding: 0.5rem;
            background: var(--primary-color);
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s ease;
            font-size: 0.85rem;
        }

        .btn-add:hover {
            background: #8b3f70;
            transform: scale(1.02);
        }

        /* CART SIDEBAR */
        .cart-sidebar {
            background: white;
            border-radius: 10px;
            box-shadow: var(--shadow);
            padding: 1.5rem;
            height: fit-content;
            position: sticky;
            top: 100px;
        }

        .cart-header {
            margin-bottom: 1.5rem;
        }

        .cart-title {
            font-size: 1.1rem;
            font-weight: 600;
            color: var(--dark-text);
            margin-bottom: 0.3rem;
        }

        .cart-order-id {
            font-size: 0.8rem;
            color: var(--light-text);
        }

        .cart-items {
            max-height: 300px;
            overflow-y: auto;
            margin-bottom: 1rem;
            border-bottom: 1px solid var(--border-color);
            padding-bottom: 1rem;
        }

        .cart-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 0.8rem;
            font-size: 0.9rem;
        }

        .cart-item-name {
            color: var(--dark-text);
            font-weight: 500;
            flex: 1;
        }

        .cart-item-price {
            color: var(--primary-color);
            font-weight: 600;
        }

        .cart-summary {
            margin-bottom: 1rem;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            font-size: 0.9rem;
            margin-bottom: 0.5rem;
            color: var(--light-text);
        }

        .summary-row.total {
            font-size: 1.1rem;
            font-weight: 600;
            color: var(--dark-text);
            border-top: 1px solid var(--border-color);
            padding-top: 0.8rem;
            margin-bottom: 1rem;
        }

        .summary-row.total .amount {
            color: var(--primary-color);
        }

        /* BUTTONS */
        .btn-primary {
            width: 100%;
            padding: 0.8rem;
            background: var(--primary-color);
            color: white;
            border: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 0.95rem;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-bottom: 0.5rem;
        }

        .btn-primary:hover {
            background: #8b3f70;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(167, 72, 128, 0.3);
        }

        .btn-secondary {
            width: 100%;
            padding: 0.8rem;
            background: var(--success-color);
            color: white;
            border: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 0.95rem;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .btn-secondary:hover {
            background: #5cb85c;
            transform: translateY(-2px);
        }

        .btn-clear {
            width: 100%;
            padding: 0.8rem;
            background: white;
            color: var(--primary-color);
            border: 2px solid var(--primary-color);
            border-radius: 6px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 0.5rem;
        }

        .btn-clear:hover {
            background: var(--primary-color);
            color: white;
        }

        /* PAYMENT METHODS */
        .payment-methods {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 0.5rem;
            margin-bottom: 1rem;
        }

        .payment-option {
            padding: 0.8rem;
            border: 2px solid var(--border-color);
            border-radius: 6px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .payment-option:hover {
            border-color: var(--primary-color);
        }

        .payment-option.selected {
            background: var(--primary-color);
            border-color: var(--primary-color);
            color: white;
        }

        .payment-label {
            font-size: 0.75rem;
            font-weight: 500;
            text-transform: uppercase;
        }

        /* PRODUCT DETAIL MODAL */
        #productDetailModal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.7);
            z-index: 1000;
            align-items: center;
            justify-content: center;
        }

        #productDetailModal.show {
            display: flex !important;
        }

        .product-detail {
            width: 90%;
            max-width: 600px;
            max-height: 90vh;
            overflow-y: auto;
            background: white;
            border-radius: 10px;
        }

        .close-btn {
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
            color: var(--dark-text);
            padding: 0;
            float: right;
        }

        .close-btn:hover {
            color: var(--primary-color);
        }

        .product-detail-header {
            background: linear-gradient(135deg, #f5e6d3, #e8d4c0);
            padding: 2rem;
            text-align: center;
            font-size: 5rem;
            min-height: 200px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .product-detail-body {
            padding: 2rem;
        }

        .detail-tag {
            color: var(--primary-color);
            font-size: 0.85rem;
            font-weight: 600;
            text-transform: uppercase;
            margin-bottom: 0.5rem;
        }

        .detail-title {
            color: var(--dark-text);
            font-size: 1.5rem;
            margin-bottom: 0.8rem;
            font-weight: 600;
        }

        .detail-description {
            color: var(--light-text);
            margin-bottom: 1.5rem;
            line-height: 1.6;
            font-size: 0.95rem;
        }

        .customize-section {
            margin-bottom: 1.5rem;
        }

        .customize-label {
            font-weight: 600;
            color: var(--dark-text);
            margin-bottom: 0.8rem;
            text-transform: uppercase;
            font-size: 0.85rem;
        }

        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 1rem;
            background: #f5f5f5;
            padding: 1rem;
            border-radius: 6px;
            width: fit-content;
        }

        .qty-btn {
            width: 36px;
            height: 36px;
            border: 2px solid var(--border-color);
            background: white;
            cursor: pointer;
            border-radius: 4px;
            font-weight: bold;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.2rem;
        }

        .qty-btn:hover {
            background: var(--primary-color);
            color: white;
            border-color: var(--primary-color);
        }

        .qty-display {
            min-width: 60px;
            text-align: center;
            font-weight: 600;
            font-size: 1.2rem;
            color: var(--dark-text);
        }

        .size-options {
            display: flex;
            gap: 1rem;
            margin-bottom: 1rem;
        }

        .size-option {
            flex: 1;
            padding: 0.8rem;
            border: 2px solid var(--border-color);
            border-radius: 6px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            font-weight: 500;
            background: white;
            color: var(--dark-text);
        }

        .size-option:hover {
            border-color: var(--primary-color);
            color: var(--primary-color);
        }

        .size-option.selected {
            background: var(--primary-color);
            border-color: var(--primary-color);
            color: white;
        }

        .level-labels {
            display: flex;
            justify-content: space-between;
            font-size: 0.75rem;
            color: var(--light-text);
            margin-bottom: 0.8rem;
            font-weight: 500;
        }

        .slider {
            width: 100%;
            height: 8px;
            border-radius: 4px;
            background: linear-gradient(to right, #e74c3c, #f39c12, #27ae60);
            appearance: none;
            cursor: pointer;
            outline: none;
            margin-bottom: 1rem;
        }

        .slider::-webkit-slider-thumb {
            appearance: none;
            width: 22px;
            height: 22px;
            border-radius: 50%;
            background: var(--primary-color);
            cursor: pointer;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
            border: 2px solid white;
        }

        .slider::-moz-range-thumb {
            width: 22px;
            height: 22px;
            border-radius: 50%;
            background: var(--primary-color);
            cursor: pointer;
            border: 2px solid white;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
        }

        .toppings-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
            gap: 1rem;
        }

        .topping-item {
            padding: 1rem;
            border: 2px solid var(--border-color);
            border-radius: 8px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            background: white;
        }

        .topping-item:hover {
            border-color: var(--primary-color);
            transform: translateY(-3px);
        }

        .topping-item.selected {
            background: var(--primary-color);
            border-color: var(--primary-color);
            color: white;
        }

        .topping-icon {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
        }

        .topping-name {
            font-size: 0.9rem;
            font-weight: 600;
            margin-bottom: 0.3rem;
            color: inherit;
        }

        .topping-price {
            font-size: 0.8rem;
            color: var(--light-text);
            font-weight: 500;
            margin-bottom: 0.5rem;
        }

        .topping-item.selected .topping-price {
            color: rgba(255, 255, 255, 0.9);
        }

        .topping-qty-controls {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            margin-top: 0.5rem;
        }

        .topping-qty-btn {
            width: 24px;
            height: 24px;
            border: 1px solid transparent;
            background: rgba(255, 255, 255, 0.2);
            color: inherit;
            cursor: pointer;
            border-radius: 3px;
            font-weight: bold;
            transition: all 0.2s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0;
        }

        .topping-item.selected .topping-qty-btn {
            background: rgba(255, 255, 255, 0.3);
        }

        .topping-item.selected .topping-qty-btn:hover {
            background: rgba(255, 255, 255, 0.5);
        }

        .topping-qty-display {
            min-width: 20px;
            text-align: center;
            font-weight: 600;
            font-size: 0.85rem;
        }

        .topping-item:not(.selected) .topping-qty-controls {
            display: none;
        }

        /* RESPONSIVE */
        @media (max-width: 1200px) {
            .main-container {
                grid-template-columns: 1fr;
            }

            .cart-sidebar {
                position: static;
            }

            .products-grid {
                grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            }
        }

        @media (max-width: 768px) {
            .menu-header {
                flex-direction: column;
            }

            .category-buttons {
                width: 100%;
                overflow-x: auto;
            }

            .products-grid {
                grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            }

            .payment-methods {
                grid-template-columns: 1fr;
            }

            .header {
                flex-wrap: wrap;
            }

            .header-center {
                flex-basis: 100%;
                margin: 1rem 0 0 0;
            }

            .search-box {
                width: 100%;
            }

            .sidebar {
                width: 100%;
                display: flex;
                flex-wrap: wrap;
                padding: 1rem;
            }

            .sidebar h2 {
                flex-basis: 100%;
            }

            .sidebar ul {
                display: flex;
                flex-wrap: wrap;
                gap: 0.5rem;
                width: 100%;
            }

            .sidebar li {
                flex: 1;
                min-width: 100px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <jsp:include page="/WEB-INF/views/staff/layout/header.jsp" />
        
        <!-- Main Content -->
        <div class="main-wrapper">
            <!-- Sidebar -->
            <jsp:include page="/WEB-INF/views/staff/layout/sidebar.jsp" />
            
            <!-- Content Area -->
            <div class="content">
                <!-- Menu Header with Categories -->
                <div class="menu-header">
                    <h1>☕ All Items</h1>
                    <div class="category-buttons">
                        <button class="category-btn active" onclick="filterByCategory('all')">All Items</button>
                        <button class="category-btn" onclick="filterByCategory('coffee')">Coffee</button>
                        <button class="category-btn" onclick="filterByCategory('milktea')">MilkTea</button>
                    </div>
                </div>

                <!-- Main Container: Products Grid + Cart -->
                <div class="main-container">
                    <!-- Products Grid -->
					<div class="products-grid">
					    <c:forEach items="${beverages}" var="b">
					        <%-- Chỉ hiển thị sản phẩm đang kinh doanh --%>
					        <c:if test="${b.sellable}">
					            <div class="product-card" data-category="drink"> <%-- Bạn có thể thay 'drink' bằng category thực tế từ DB nếu có --%>
					                <div class="product-image">
					                    <c:choose>
					                        <c:when test="${not empty b.imgUrl}">
					                            <img src="${b.imgUrl}" alt="${b.name}" style="width: 100%; height: 100%; object-fit: cover;">
					                        </c:when>
					                        <c:otherwise>
					                            <div style="font-size: 3rem;">☕</div> <%-- Icon mặc định nếu thiếu ảnh --%>
					                        </c:otherwise>
					                    </c:choose>
					                </div>
					                
					                <div class="product-info">
					                    <div class="product-category">BEVERAGE</div>
					                    <div class="product-name">${b.name}</div>
					                    <p class="product-description">
					                        Thưởng thức hương vị tuyệt vời của ${b.name} tại Chip3Chip.
					                    </p>
					                    <div class="product-price">
					                        <fmt:formatNumber value="${b.basePrice}" pattern="#,###" /> VNĐ
					                    </div>
					                    
					                    <div class="product-actions">
											<a href="${pageContext.request.contextPath}/topping?productID=${b.productID}"
											   class="btn-add"
											   onclick="event.stopPropagation();">
											    Add
											</a>
					                    </div>
					                </div>
					            </div>
					        </c:if>
					    </c:forEach>
					</div>

                    
				<div class="cart-sidebar">

				    <div class="cart-header">
				        <div class="cart-title">🛒 Current Order</div>
				        <div class="cart-order-id">Session Cart</div>
				    </div>

				    <!-- CART ITEMS -->
				    <div class="cart-items">

				        <c:if test="${empty sessionScope.order}">
				            <p>Chưa có sản phẩm</p>
				        </c:if>

				        <c:if test="${not empty sessionScope.order}">
				            <c:forEach var="item" items="${sessionScope.order.items}">

				                <div class="cart-item">
				                    <div class="cart-item-name">
				                        ${item.product.description}
				                        <br>
				                        <small>x${item.quantity}</small>
				                    </div>

				                    <div class="cart-item-price">
				                        <fmt:formatNumber value="${item.subTotal}" pattern="#,###"/>đ
				                    </div>
				                </div>

				            </c:forEach>
				        </c:if>

				    </div>

				    <!-- SUMMARY -->
				    <div class="cart-summary">
				        <div class="summary-row total">
				            <span>Total</span>
				            <span class="amount">
				                <c:if test="${not empty sessionScope.order}">
				                    <fmt:formatNumber value="${sessionScope.order.calculateTotal()}" pattern="#,###"/>đ
				                </c:if>
				                <c:if test="${empty sessionScope.order}">
				                    0đ
				                </c:if>
				            </span>
				        </div>
				    </div>

				    <!-- PAYMENT -->
				    <div class="payment-methods">
				        <div class="payment-option selected" onclick="selectPaymentMethod(this)">
				            <span class="payment-label">💳 Cash</span>
				        </div>
				        <div class="payment-option" onclick="selectPaymentMethod(this)">
				            <span class="payment-label">📱 VnPay</span>
				        </div>
				        <div class="payment-option" onclick="selectPaymentMethod(this)">
				            <span class="payment-label">💳 Card</span>
				        </div>
				    </div>

				    <!-- BUTTON -->
				    <button class="btn-primary">🛒 Checkout</button>
				    <button class="btn-secondary">✅ Complete Order</button>

				    <form action="clear-cart" method="post">
				        <button class="btn-clear">Clear All</button>
				    </form>

				</div>

                <!-- Product Detail Modal (Hidden by default) -->
                

                <!-- Custom Brew Section -->
                <div style="background: white; border-radius: 10px; padding: 2rem; margin-top: 2rem; box-shadow: var(--shadow);">
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; align-items: center;">
                        <div>
                            <h3 style="color: var(--primary-color); font-size: 1.3rem; margin-bottom: 0.5rem;">Custom Brew?</h3>
                            <p style="color: var(--light-text); margin-bottom: 1rem;">Create a unique recipe for a regular customer or special request.</p>
                            <button style="background: var(--primary-color); color: white; border: none; padding: 0.8rem 1.5rem; border-radius: 6px; cursor: pointer; font-weight: 600; display: inline-flex; align-items: center; gap: 0.5rem;">
                                ✏️ Open Designer
                            </button>
                        </div>
                        <div style="text-align: center; font-size: 3rem; opacity: 0.3;">
                            ⚙️ ✨
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="/WEB-INF/views/staff/layout/footer.jsp" />
    </div>

    <script>
        // Product data storage
        let currentProduct = {
            id: null,
            name: '',
            price: 0,
            icon: '☕',
            category: 'COFFEE'
        };

        // Open product detail modal
        function selectProduct(productId, productName, productPrice, productIcon = '☕', productCategory = 'COFFEE') {
            currentProduct = { id: productId, name: productName, price: productPrice, icon: productIcon, category: productCategory };
            
            // Update modal content
            document.getElementById('modalProductIcon').textContent = productIcon;
            document.getElementById('modalProductName').textContent = productName;
            document.getElementById('modalProductCategory').textContent = productCategory;
            document.getElementById('modalProductDesc').textContent = 'Customize your ' + productName + ' with your preferred toppings, sugar level, and ice level.';
            
            // Reset customizations
            document.getElementById('quantityDisplay').textContent = '1';
            document.getElementById('sugarLevel').textContent = '38% Selected';
            document.getElementById('iceLevel').textContent = '58% Selected';
            document.getElementById('sugarSlider').value = 38;
            document.getElementById('iceSlider').value = 58;
            
            // Reset size selection
            document.querySelectorAll('.size-option').forEach((el, idx) => {
                el.classList.toggle('selected', idx === 1); // Select M by default
            });
            
            // Show modal
            document.getElementById('productDetailModal').classList.add('show');
        }

        // Close product modal
        function closeProductModal() {
            document.getElementById('productDetailModal').classList.remove('show');
        }

        // Update sugar level display
        function updateSugarLevel(value) {
            document.getElementById('sugarLevel').textContent = value + '% Selected';
        }

        // Update ice level display
        function updateIceLevel(value) {
            document.getElementById('iceLevel').textContent = value + '% Selected';
        }

        // Quantity management
        let currentQuantity = 1;

        function updateQuantity(change) {
            currentQuantity += change;
            if (currentQuantity < 1) currentQuantity = 1;
            document.getElementById('quantityDisplay').textContent = currentQuantity;
        }

        // Size selection
        function selectSize(sizeElement) {
            document.querySelectorAll('.size-option').forEach(el => {
                el.classList.remove('selected');
            });
            sizeElement.classList.add('selected');
            console.log('Selected size: ' + sizeElement.textContent);
        }

        // Topping selection
        function toggleTopping(element) {
            element.classList.toggle('selected');
        }

        // Category filter
        function filterByCategory(category) {
            document.querySelectorAll('.category-btn').forEach(btn => {
                btn.classList.remove('active');
            });
            event.target.classList.add('active');
            
            const products = document.querySelectorAll('.product-card');
            products.forEach(product => {
                if (category === 'all' || product.dataset.category === category) {
                    product.style.display = '';
                } else {
                    product.style.display = 'none';
                }
            });
        }

        // Payment method selection
        function selectPaymentMethod(element) {
            document.querySelectorAll('.payment-option').forEach(el => {
                el.classList.remove('selected');
            });
            element.classList.add('selected');
        }

        // Add to cart
        function addToCart() {
            const size = document.querySelector('.size-option.selected').textContent;
            const sugar = document.getElementById('sugarLevel').textContent;
            const ice = document.getElementById('iceLevel').textContent;
            const selectedToppings = Array.from(document.querySelectorAll('.topping-item.selected'))
                .map(el => el.querySelector('.topping-name').textContent);
            
            console.log('✅ Added to cart:', {
                product: currentProduct.name,
                quantity: currentQuantity,
                size: size,
                sugar: sugar,
                ice: ice,
                toppings: selectedToppings,
                price: currentProduct.price
            });
            
            alert('✅ Added ' + currentQuantity + 'x ' + currentProduct.name + ' to cart!\nSize: ' + size);
        }

        // Clear cart
        function clearCart() {
            if (confirm('Clear entire cart?')) {
                document.querySelectorAll('.cart-item').forEach(item => item.remove());
                console.log('Cart cleared');
            }
        }

        // Search functionality
        function searchProducts(query) {
            const products = document.querySelectorAll('.product-card');
            const lowerQuery = query.toLowerCase();
            
            products.forEach(product => {
                const name = product.querySelector('.product-name').textContent.toLowerCase();
                const description = product.querySelector('.product-description').textContent.toLowerCase();
                
                if (name.includes(lowerQuery) || description.includes(lowerQuery)) {
                    product.style.display = '';
                } else {
                    product.style.display = 'none';
                }
            });
        }

        // Clock functionality
        function updateClock() {
            const clock = document.getElementById('clock');
            if (clock) {
                const now = new Date();
                clock.textContent = now.toLocaleTimeString('vi-VN');
            }
        }

        // Initialize on page load
        document.addEventListener('DOMContentLoaded', function() {
            updateClock();
            setInterval(updateClock, 1000);

            // Handle search
            const searchBox = document.querySelector('.search-box');
            if (searchBox) {
                searchBox.addEventListener('input', function(e) {
                    searchProducts(e.target.value);
                });
            }

            // Close modal when clicking outside
            document.getElementById('productDetailModal').addEventListener('click', function(e) {
                if (e.target === this) {
                    closeProductModal();
                }
            });
        });
    </script>
</body>
</html>