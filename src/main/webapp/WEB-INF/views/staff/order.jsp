<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Chip3Chip - Order Management</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
/* Một số tùy chỉnh cho slider và modal không có sẵn trong Tailwind mặc định */
.slider {
	-webkit-appearance: none;
	width: 100%;
	height: 8px;
	border-radius: 4px;
	background: linear-gradient(to right, #e74c3c, #f39c12, #27ae60);
	outline: none;
}

.slider::-webkit-slider-thumb {
	-webkit-appearance: none;
	width: 20px;
	height: 20px;
	border-radius: 50%;
	background: #9D3C4D;
	cursor: pointer;
	border: 2px solid white;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}
</style>
</head>
<body class="bg-gray-50 font-sans antialiased text-gray-900">

	<div class="flex min-h-screen">
		<jsp:include page="/WEB-INF/views/staff/layout/sidebar.jsp" />

		<div class="flex-1 flex flex-col">
			<jsp:include page="/WEB-INF/views/staff/layout/header.jsp" />

			<main class="p-6 space-y-6">

				<div
					class="flex flex-col md:flex-row justify-between items-center gap-4 bg-white p-4 rounded-2xl shadow-sm border border-gray-100">
					<div class="flex items-center gap-4 w-full md:w-auto">
						<h2 class="text-xl font-bold text-gray-800">☕ Thực đơn</h2>
						<div class="relative flex-1 md:w-80">
							<span
								class="absolute inset-y-0 left-0 pl-3 flex items-center text-gray-400">
								<svg class="h-5 w-5" fill="none" stroke="currentColor"
									viewBox="0 0 24 24">
									<path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" /></svg>
							</span> <input type="text"
								class="search-box block w-full pl-10 pr-3 py-2 border border-gray-200 rounded-xl leading-5 bg-gray-50 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#9D3C4D] focus:bg-white transition-all sm:text-sm"
								placeholder="Tìm tên đồ uống...">
						</div>
					</div>

					<div class="flex items-center gap-3">
						<div id="clock"
							class="text-lg font-mono font-bold text-[#9D3C4D] bg-orange-50 px-4 py-1 rounded-lg border border-orange-100"></div>
					</div>
				</div>

				<div class="flex gap-2 overflow-x-auto pb-2">
					<button
						class="category-btn active px-6 py-2 rounded-full bg-[#9D3C4D] text-white font-medium shadow-md transition-all"
						onclick="filterByCategory('all')">Tất cả</button>
					<button
						class="category-btn px-6 py-2 rounded-full bg-white text-gray-600 border border-gray-200 hover:border-[#9D3C4D] hover:text-[#9D3C4D] transition-all"
						onclick="filterByCategory('coffee')">Cà phê</button>
					<button
						class="category-btn px-6 py-2 rounded-full bg-white text-gray-600 border border-gray-200 hover:border-[#9D3C4D] hover:text-[#9D3C4D] transition-all"
						onclick="filterByCategory('milktea')">Trà sữa</button>
				</div>

				<div class="grid grid-cols-1 lg:grid-cols-12 gap-8">

					<div class="lg:col-span-8">
						<div class="grid grid-cols-2 md:grid-cols-3 gap-6">
							<c:forEach items="${beverages}" var="b">
								<c:if test="${b.sellable}">
									<div
										class="product-card group bg-white rounded-2xl border border-gray-100 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all duration-300 overflow-hidden"
										data-category="drink">
										<div class="h-48 bg-orange-50 relative overflow-hidden">
											<c:choose>
												<c:when test="${not empty b.imgUrl}">
													<img src="${b.imgUrl}" alt="${b.name}"
														class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500">
												</c:when>
												<c:otherwise>
													<div
														class="flex items-center justify-center h-full text-5xl opacity-30">☕</div>
												</c:otherwise>
											</c:choose>
											<div
												class="absolute top-2 right-2 bg-white/90 backdrop-blur px-2 py-1 rounded-lg text-[10px] font-bold text-[#9D3C4D] uppercase tracking-wider">BEVERAGE</div>
										</div>
										<div class="p-4">
											<h3 class="font-bold text-gray-800 mb-1 truncate">${b.name}</h3>
											<div class="text-[#9D3C4D] font-extrabold text-lg mb-3">
												<fmt:formatNumber value="${b.basePrice}" pattern="#,###" />
												<span class="text-sm">đ</span>
											</div>
											<a
												href="${pageContext.request.contextPath}/topping?productID=${b.productID}"
												class="block w-full text-center py-2.5 bg-gray-50 text-gray-700 font-bold rounded-xl hover:bg-[#9D3C4D] hover:text-white transition-colors border border-gray-100">
												Thêm món </a>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>

					<div class="lg:col-span-4">
						<div
							class="bg-white rounded-3xl shadow-lg border border-gray-100 sticky top-24 overflow-hidden">
							<div class="bg-[#9D3C4D] p-5 text-white">
								<h2 class="text-lg font-bold flex items-center gap-2">🛒
									Đơn hàng hiện tại</h2>
								<p class="text-xs text-white/70 italic">Chi tiết giỏ hàng
									tạm thời</p>
							</div>

							<div class="p-5 max-h-[400px] overflow-y-auto space-y-4">
								<c:if test="${empty sessionScope.order}">
									<div class="text-center py-10">
										<div class="text-4xl mb-2 opacity-20">🛒</div>
										<p class="text-gray-400">Chưa có sản phẩm nào</p>
									</div>
								</c:if>

								<c:forEach var="item" items="${sessionScope.order.items}">
									<div
										class="flex justify-between items-start border-b border-gray-50 pb-3">
										<div>
											<p class="font-bold text-sm text-gray-800">${item.product.description}</p>
											<p class="text-xs text-gray-400">Số lượng:
												x${item.quantity}</p>
										</div>
										<div class="text-right">
											<p class="font-bold text-[#9D3C4D] text-sm">
												<fmt:formatNumber value="${item.subTotal}" pattern="#,###" />
												đ
											</p>
										</div>
									</div>
								</c:forEach>
							</div>

							<div class="p-5 bg-gray-50 border-t border-gray-100 space-y-4">
								<div class="flex justify-between items-center">
									<span class="text-gray-500 font-medium">Tổng cộng</span> <span
										class="text-2xl font-black text-gray-800"> <c:choose>
											<c:when test="${not empty sessionScope.order}">
												<fmt:formatNumber
													value="${sessionScope.order.calculateTotal()}"
													pattern="#,###" />đ
                                            </c:when>
											<c:otherwise>0đ</c:otherwise>
										</c:choose>
									</span>
								</div>

								<div class="grid grid-cols-3 gap-2">
									<button
										class="payment-option selected border-2 border-[#9D3C4D] bg-orange-50 text-[#9D3C4D] p-2 rounded-xl text-center transition-all"
										onclick="selectPaymentMethod(this)">
										<span class="text-[10px] font-bold block">TIỀN MẶT</span>
									</button>
									<button
										class="payment-option border-2 border-gray-100 text-gray-400 p-2 rounded-xl text-center hover:border-[#9D3C4D] transition-all"
										onclick="selectPaymentMethod(this)">
										<span class="text-[10px] font-bold block">VNPAY</span>
									</button>
									<button
										class="payment-option border-2 border-gray-100 text-gray-400 p-2 rounded-xl text-center hover:border-[#9D3C4D] transition-all"
										onclick="selectPaymentMethod(this)">
										<span class="text-[10px] font-bold block">THẺ</span>
									</button>
								</div>

								<div class="space-y-2">
									
									<form action="${pageContext.request.contextPath}/payment"
										method="GET">
										<button type="submit"
											class="w-full py-3 bg-[#9D3C4D] text-white font-bold rounded-xl shadow-lg shadow-red-100 hover:bg-[#833240] transition-colors">
											THANH TOÁN</button>
									</form>
									<form action="clear-cart" method="post">
										<button
											class="w-full py-2 text-gray-400 font-medium hover:text-red-500 transition-colors text-sm">Xóa
											giỏ hàng</button>
									</form>
								</div>
							</div>
						</div>
					</div>

				</div>
			</main>

			<jsp:include page="/WEB-INF/views/staff/layout/footer.jsp" />
		</div>
	</div>

	<script>
        function updateClock() {
            const clock = document.getElementById('clock');
            if (clock) {
                const now = new Date();
                clock.textContent = now.toLocaleTimeString('vi-VN');
            }
        }

        function filterByCategory(category) {
            document.querySelectorAll('.category-btn').forEach(btn => {
                btn.classList.remove('active', 'bg-[#9D3C4D]', 'text-white');
                btn.classList.add('bg-white', 'text-gray-600');
            });
            event.target.classList.add('active', 'bg-[#9D3C4D]', 'text-white');
            
            const products = document.querySelectorAll('.product-card');
            products.forEach(product => {
                if (category === 'all' || product.dataset.category === category) {
                    product.style.display = '';
                } else {
                    product.style.display = 'none';
                }
            });
        }

        function searchProducts(query) {
            const products = document.querySelectorAll('.product-card');
            const lowerQuery = query.toLowerCase();
            products.forEach(product => {
                const name = product.querySelector('h3').textContent.toLowerCase();
                product.style.display = name.includes(lowerQuery) ? '' : 'none';
            });
        }

        function selectPaymentMethod(element) {
            document.querySelectorAll('.payment-option').forEach(el => {
                el.classList.remove('selected', 'border-[#9D3C4D]', 'bg-orange-50', 'text-[#9D3C4D]');
                el.classList.add('border-gray-100', 'text-gray-400');
            });
            element.classList.add('selected', 'border-[#9D3C4D]', 'bg-orange-50', 'text-[#9D3C4D]');
        }

        document.addEventListener('DOMContentLoaded', function() {
            updateClock();
            setInterval(updateClock, 1000);
            const searchBox = document.querySelector('.search-box');
            if (searchBox) {
                searchBox.addEventListener('input', (e) => searchProducts(e.target.value));
            }
        });
    </script>
</body>
</html>