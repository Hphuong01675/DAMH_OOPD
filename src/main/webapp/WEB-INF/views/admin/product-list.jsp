<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Quản lý thực đơn | Chip3Chip</title>
    <script src="https://cdn.tailwindcss.com?plugins=forms"></script>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap&subset=vietnamese" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet" />
    <style>
        body { font-family: 'Plus Jakarta Sans', sans-serif; }
        .material-symbols-outlined { font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24; }
    </style>
</head>
<body class="bg-[#faf7f2] text-[#35322d] flex min-h-screen">

    <%@ include file="/WEB-INF/views/admin/layout/sidebar.jsp"%>

    <div class="flex-1 ml-64 flex flex-col">
        <%@ include file="/WEB-INF/views/admin/layout/header.jsp"%>

        <main class="px-10 pb-8 flex-1">

            <div class="flex flex-col md:flex-row justify-between items-end mb-10 mt-4 gap-6">
                <div class="max-w-xl">
                    <h1 class="text-4xl font-extrabold tracking-tight mb-2 text-[#35322d]">Menu Atelier</h1>
                    <p class="text-gray-500 text-sm font-medium">Refine your artisanal beverage offerings. Manage seasonal flavors and premium toppings.</p>
                </div>

                <form action="${pageContext.request.contextPath}/admin/products" method="GET" class="relative w-full md:w-[400px]">
                    <span class="material-symbols-outlined absolute left-5 top-1/2 -translate-y-1/2 text-gray-400">search</span> 
                    <input type="text" name="keyword" value="${param.keyword}" placeholder="Tìm tên thức uống..."
                        class="w-full h-14 bg-white border-none rounded-full pl-14 pr-6 text-sm font-medium shadow-sm focus:ring-2 focus:ring-[#974362]/20 outline-none transition-all">
                </form>
            </div>

            <div class="bg-white rounded-[2rem] p-8 shadow-[0_8px_30px_rgb(0,0,0,0.02)] border border-[#f3ede6] mb-12">
                <h2 class="text-xl font-bold mb-8 flex items-center gap-3 text-[#974362]">
                    <span class="material-symbols-outlined text-[28px]">edit_square</span>
                    ${not empty item ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm mới'}
                </h2>

                <form action="${pageContext.request.contextPath}/admin/product/save" method="POST" enctype="multipart/form-data"
                    class="grid grid-cols-1 md:grid-cols-3 gap-8">

                    <input type="hidden" name="productID" value="${item.productID}">

                    <div class="space-y-2">
                        <label class="text-[11px] font-bold uppercase tracking-widest text-gray-400 ml-2">Tên thức uống</label>
                        <input type="text" name="name" value="${item.name}" required placeholder="Ví dụ: Trà sữa nướng" 
                            class="w-full h-14 px-6 rounded-2xl bg-[#faf7f2] border-none focus:ring-2 focus:ring-[#974362]/20 font-medium">
                    </div>

                    <div class="space-y-2">
                        <label class="text-[11px] font-bold uppercase tracking-widest text-gray-400 ml-2">Giá niêm yết (VNĐ)</label>
                        <input type="number" name="basePrice" value="${item.basePrice}" required placeholder="0.00"
                            class="w-full h-14 px-6 rounded-2xl bg-[#faf7f2] border-none focus:ring-2 focus:ring-[#974362]/20 font-medium">
                    </div>

                    <div class="space-y-2">
                        <label class="text-[11px] font-bold uppercase tracking-widest text-gray-400 ml-2">Hình ảnh minh họa</label>
                        <input type="file" name="image"
                            class="w-full h-14 px-2 py-3 rounded-2xl bg-[#faf7f2] text-sm file:mr-4 file:py-1 file:px-4 file:rounded-xl file:border-0 file:text-xs file:font-bold file:bg-white file:text-[#974362] cursor-pointer">
                    </div>

                    <div class="md:col-span-3 flex justify-end gap-4 mt-2">
                        <c:if test="${not empty item}">
                            <a href="${pageContext.request.contextPath}/admin/products" 
                               class="h-14 px-8 flex items-center font-bold text-gray-400 hover:text-gray-600 transition-colors">Hủy thao tác</a>
                        </c:if>

                        <button type="submit" class="h-14 px-10 bg-[#974362] text-white rounded-2xl font-bold shadow-lg shadow-[#974362]/20 hover:scale-[1.02] active:scale-95 transition-all flex items-center gap-2">
                            <span class="material-symbols-outlined text-[20px]">${not empty item ? 'done_all' : 'add_circle'}</span>
                            ${not empty item ? 'Lưu cập nhật' : 'Thêm sản phẩm'}
                        </button>
                    </div>
                </form>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
                <c:forEach items="${list}" var="b">
                    <c:set var="cardClass" value="${not b.sellable ? 'opacity-60 grayscale-[0.4] bg-gray-50' : 'hover:-translate-y-2'}" />

                    <div class="bg-white rounded-[2.5rem] p-5 shadow-[0_4px_20px_rgba(0,0,0,0.03)] border border-[#f3ede6] flex flex-col group transition-all relative ${cardClass}">

                        <c:if test="${not b.sellable}">
                            <div class="absolute top-6 left-1/2 -translate-x-1/2 bg-gray-800/90 backdrop-blur-sm text-white px-4 py-1.5 text-[10px] font-black uppercase tracking-widest rounded-full z-10 shadow-xl">
                                Tạm ngưng bán
                            </div>
                        </c:if>

                        <div class="h-56 bg-[#faf7f2] rounded-[2rem] overflow-hidden mb-6 relative">
                            <img src="${not empty b.imgUrl ? b.imgUrl : 'https://placehold.co/400'}"
                                class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110">
                            <div class="absolute inset-0 bg-gradient-to-t from-black/5 to-transparent"></div>
                        </div>

                        <div class="px-2 flex-1">
                            <div class="flex justify-between items-start mb-4">
                                <div>
                                    <p class="text-[10px] font-black text-[#974362]/40 uppercase tracking-[0.2em] mb-1">Beverage</p>
                                    <h3 class="font-extrabold text-lg text-[#35322d] leading-tight">${b.name}</h3>
                                </div>
                                <span class="font-black text-[#974362] text-md bg-[#974362]/5 px-3 py-1 rounded-xl">${b.basePrice}đ</span>
                            </div>
                        </div>

                        <div class="mt-6 flex justify-between items-center pt-5 border-t border-[#faf7f2]">
                            <a href="${pageContext.request.contextPath}/admin/product/edit?id=${b.productID}" 
                               class="flex items-center gap-2 text-sm font-bold text-gray-400 hover:text-[#974362] transition-colors">
                                <span class="material-symbols-outlined text-[20px]">edit</span> Sửa
                            </a> 
                            
                            <a href="${pageContext.request.contextPath}/admin/product/toggle?id=${b.productID}"
                                onclick="return confirm('${b.sellable ? 'Xác nhận tạm ngưng bán món này?' : 'Xác nhận bán lại món này?' }')"
                                class="flex items-center gap-2 px-4 py-2 rounded-full transition-all 
                                ${b.sellable ? 'bg-gray-100 text-gray-500 hover:bg-amber-50 hover:text-amber-600' : 'bg-green-100 text-green-600 hover:bg-green-200'}">
                                
                                <span class="material-symbols-outlined text-[18px]" style="${not b.sellable ? 'font-variation-settings: \'FILL\' 1' : ''}">
                                    ${b.sellable ? 'visibility_off' : 'visibility'}
                                </span>
                                <span class="text-[11px] font-black uppercase tracking-tighter">
                                    ${b.sellable ? 'Tạm ngưng' : 'Kích hoạt'}
                                </span>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="mt-20 bg-[#f4e8f6] rounded-[2.5rem] p-10 border border-[#e6d0eb] relative overflow-hidden">
                <div class="absolute -right-20 -top-20 w-64 h-64 bg-white/30 rounded-full blur-3xl"></div>
                <h3 class="font-black text-[#5c3166] text-2xl mb-8 flex items-center gap-3 relative z-10">
                    <span class="material-symbols-outlined text-[32px]">bubble_chart</span>
                    Artisanal Toppings
                </h3>
                <div class="flex flex-wrap gap-4 relative z-10">
                    <c:forEach items="${toppings}" var="t">
                        <div class="bg-white/80 backdrop-blur-sm text-[#5c3166] px-6 py-3 rounded-2xl text-sm font-bold shadow-sm border border-white flex items-center gap-3 hover:bg-white transition-all">
                            <span class="w-1.5 h-1.5 rounded-full bg-[#974362]"></span>
                            ${t.name} <span class="opacity-40 font-medium ml-1">+${t.price}đ</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>
        
        <%@ include file="/WEB-INF/views/admin/layout/footer.jsp"%>
    </div>

</body>
</html>