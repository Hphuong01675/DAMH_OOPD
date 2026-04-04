<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Quản lý thực đơn | Chip3Chip</title>
<script src="https://cdn.tailwindcss.com?plugins=forms"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap"
	rel="stylesheet" />
<style>
body {
	font-family: 'Plus Jakarta Sans', sans-serif;
}
</style>
</head>
<body class="bg-[#faf7f2] text-[#35322d] flex min-h-screen">

	<%@ include file="/views/admin/layout/sidebar.jsp"%>

	<div class="flex-1 ml-64 flex flex-col">
		<%@ include file="/views/admin/layout/header.jsp"%>

		<main class="px-10 pb-8 flex-1">

			<!-- HEADER -->
			<div
				class="flex flex-col md:flex-row justify-between items-center mb-10 mt-4 gap-6">
				<div class="max-w-xl">
					<h1
						class="text-4xl font-extrabold tracking-tight mb-2 text-[#35322d]">
						Menu Atelier</h1>
					<p class="text-gray-500 text-sm font-medium">Quản lý hương vị
						và các loại Topping cao cấp.</p>
				</div>

				<form action="${pageContext.request.contextPath}/admin/products"
					method="GET" class="relative w-full md:w-[400px]">
					<span
						class="material-symbols-outlined absolute left-5 top-1/2 -translate-y-1/2 text-gray-400">
						search </span> <input type="text" name="keyword" value="${param.keyword}"
						placeholder="Tìm tên thức uống..."
						class="w-full h-14 bg-white border-none rounded-full pl-14 pr-6 text-sm font-medium shadow-sm focus:ring-2 focus:ring-[#974362]/20 outline-none transition-all">
				</form>
			</div>

			<!-- FORM -->
			<div class="bg-white rounded-[2rem] p-8 shadow border mb-12">
				<h2 class="text-xl font-bold mb-6 text-[#974362]">${not empty item ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm mới'}
				</h2>

				<form action="${pageContext.request.contextPath}/admin/product/save"
					method="POST" enctype="multipart/form-data"
					class="grid grid-cols-1 md:grid-cols-3 gap-6">

					<input type="hidden" name="productID" value="${item.productID}">

					<input type="text" name="name" value="${item.name}" required
						placeholder="Tên" class="h-14 px-4 rounded-xl bg-[#faf7f2]">

					<input type="number" name="basePrice" value="${item.basePrice}"
						required placeholder="Giá"
						class="h-14 px-4 rounded-xl bg-[#faf7f2]"> <input
						type="file" name="image"
						class="h-14 px-2 py-3 rounded-xl bg-[#faf7f2]">

					<div class="md:col-span-3 flex justify-end gap-3">
						<c:if test="${not empty item}">
							<a href="${pageContext.request.contextPath}/admin/products">
								Hủy </a>
						</c:if>

						<button type="submit"
							class="px-6 py-3 bg-[#974362] text-white rounded-xl">
							${not empty item ? 'Cập nhật' : 'Thêm'}</button>
					</div>
				</form>
			</div>

			<!-- LIST -->
			<div
				class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
				<c:forEach items="${list}" var="b">

					<!-- set class trước cho sạch -->
					<c:set var="cardClass"
						value="${not b.sellable ? 'opacity-60 grayscale-[0.4] bg-gray-50' : 'hover:-translate-y-1'}" />

					<div
						class="bg-white rounded-[2rem] p-5 shadow border flex flex-col group transition-all relative ${cardClass}">

						<!-- trạng thái -->
						<c:if test="${not b.sellable}">
							<div
								class="absolute top-4 left-1/2 -translate-x-1/2 bg-gray-800 text-white px-3 py-1 text-xs rounded-full">
								Tạm ngưng bán</div>
						</c:if>

						<!-- ảnh -->
						<div class="h-52 bg-[#faf7f2] rounded-xl overflow-hidden mb-4">
							<img
								src="${not empty b.imgUrl ? b.imgUrl : 'https://placehold.co/400'}"
								class="w-full h-full object-cover">
						</div>

						<!-- info -->
						<div class="flex justify-between mb-3">
							<h3 class="font-bold">${b.name}</h3>
							<span class="text-[#974362]">${b.basePrice}đ</span>
						</div>

						<!-- actions -->
						<div
							class="mt-auto flex justify-between items-center pt-3 border-t">

							<a
								href="${pageContext.request.contextPath}/admin/product/edit?id=${b.productID}">
								Sửa </a> <a
								href="${pageContext.request.contextPath}/admin/product/toggle?id=${b.productID}"
								onclick="return confirm('${b.sellable ? 'Tạm ngưng bán?' : 'Bán lại?' }')"
								class="${b.sellable ? 'text-gray-400' : 'text-green-600'}">

								<span style="${not b.sellable ? 'font-weight:bold' : ''}">
									${b.sellable ? 'OFF' : 'ON'} </span>

							</a>

						</div>

					</div>

				</c:forEach>
			</div>
			<!-- Topping -->
			<div
				class="mt-16 bg-[#f4e8f6] rounded-[2rem] p-8 border border-[#e6d0eb]">
				<h3
					class="font-extrabold text-[#5c3166] text-xl mb-6 flex items-center gap-2">
					<span class="material-symbols-outlined">bubble_chart</span> Danh
					sách Toppings hiện có
				</h3>
				<div class="flex flex-wrap gap-3">
					<c:forEach items="${toppings}" var="t">
						<span
							class="bg-white text-[#5c3166] px-5 py-2 rounded-full text-sm font-bold shadow-sm border border-white">
							${t.name} (+${t.price}đ) </span>
					</c:forEach>
				</div>
			</div>
		</main>
		<%@ include file="/views/admin/layout/footer.jsp"%>
	</div>

</body>
</html>