<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Quản lý thực đơn | Chip3Chip</title>
    <script src="https://cdn.tailwindcss.com?plugins=forms"></script>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet" />
    <style>
        body { font-family: 'Plus Jakarta Sans', sans-serif; }
    </style>
</head>
<body class="bg-[#faf7f2] text-[#35322d] flex min-h-screen">

    <%@ include file="/WEB-INF/views/admin/layout/sidebar.jsp" %>

    <div class="flex-1 ml-64 flex flex-col">
        <%@ include file="/WEB-INF/views/admin/layout/header.jsp" %>

        <main class="px-10 pb-8 flex-1">
            
            <div class="flex flex-col md:flex-row justify-between items-center mb-10 mt-4 gap-6">
                <div class="max-w-xl">
                    <h1 class="text-4xl font-extrabold tracking-tight mb-2 text-[#35322d]">Menu Atelier</h1>
                    <p class="text-gray-500 text-sm font-medium">Quản lý hương vị và các loại Topping cao cấp.</p>
                </div>
                
                <form action="${pageContext.request.contextPath}/admin/products" method="GET" class="relative w-full md:w-[400px]">
                    <span class="material-symbols-outlined absolute left-5 top-1/2 -translate-y-1/2 text-gray-400">search</span>
                    <input type="text" name="keyword" value="${param.keyword}" placeholder="Tìm tên thức uống..." 
                           class="w-full h-14 bg-white border-none rounded-full pl-14 pr-6 text-sm font-medium shadow-sm focus:ring-2 focus:ring-[#974362]/20 outline-none transition-all">
                </form>
            </div>

            <div class="bg-white rounded-[2rem] p-8 shadow-[0_4px_24px_rgba(0,0,0,0.02)] border border-[#f3ede6] mb-12">
                <h2 class="text-xl font-bold mb-6 flex items-center gap-2 text-[#974362]">
                    <span class="material-symbols-outlined">edit_square</span>
                    ${not empty item ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm mới'}
                </h2>
                
                <form action="${pageContext.request.contextPath}/admin/product/save" method="POST" enctype="multipart/form-data" class="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <input type="hidden" name="productID" value="${item.productID}">
                    
                    <div class="space-y-2">
                        <label class="text-xs font-bold uppercase tracking-wider text-gray-400 ml-2">Tên thức uống</label>
                        <input type="text" name="name" value="${item.name}" required class="w-full h-14 bg-[#faf7f2] border-none rounded-2xl px-6 focus:ring-2 focus:ring-[#974362]/20 font-medium">
                    </div>
                    
                    <div class="space-y-2">
                        <label class="text-xs font-bold uppercase tracking-wider text-gray-400 ml-2">Giá niêm yết (VNĐ)</label>
                        <input type="number" name="basePrice" value="${item.basePrice}" required class="w-full h-14 bg-[#faf7f2] border-none rounded-2xl px-6 focus:ring-2 focus:ring-[#974362]/20 font-medium">
                    </div>
                    
                    <div class="space-y-2">
                        <label class="text-xs font-bold uppercase tracking-wider text-gray-400 ml-2">Hình ảnh</label>
                        <input type="file" name="image" class="w-full h-14 bg-[#faf7f2] rounded-2xl px-2 py-3 text-sm file:bg-white file:border-none file:rounded-lg file:font-bold file:text-[#974362] cursor-pointer">
                    </div>
                    
                    <div class="md:col-span-3 flex justify-end gap-3 mt-4">
                        <c:if test="${not empty item}">
                            <a href="${pageContext.request.contextPath}/admin/products" class="h-14 px-8 flex items-center font-bold text-gray-400">Hủy</a>
                        </c:if>
                        <button type="submit" class="h-14 px-10 bg-[#974362] text-white rounded-2xl font-bold shadow-lg shadow-[#974362]/20 hover:scale-[1.02] transition-all">
                            ${not empty item ? 'Cập nhật' : 'Thêm vào Menu'}
                        </button>
                    </div>
                </form>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
                <c:forEach items="${list}" var="b">
                    <div class="bg-white rounded-[2rem] p-5 shadow-[0_4px_20px_rgba(0,0,0,0.03)] border border-[#f3ede6] flex flex-col group hover:-translate-y-1 transition-all">
                        <div class="h-52 w-full bg-[#faf7f2] rounded-[1.5rem] overflow-hidden mb-5">
                            <img src="${not empty b.imgUrl ? b.imgUrl : 'https://placehold.co/400'}" class="w-full h-full object-cover">
                        </div>
                        
                        <div class="flex justify-between items-start mb-4">
                            <h3 class="font-extrabold text-lg text-[#35322d]">${b.name}</h3>
                            <span class="font-bold text-[#974362] text-sm">${b.basePrice}đ</span>
                        </div>
                        
                        <div class="flex gap-2 mb-6">
                            <span class="w-7 h-7 rounded-lg bg-[#faf7f2] flex items-center justify-center text-[10px] font-bold text-gray-400">S</span>
                            <span class="w-7 h-7 rounded-lg bg-[#faf7f2] flex items-center justify-center text-[10px] font-bold text-gray-400">M</span>
                            <span class="w-7 h-7 rounded-lg bg-[#faf7f2] flex items-center justify-center text-[10px] font-bold text-gray-400">L</span>
                        </div>
                        
                        <div class="mt-auto flex justify-between items-center pt-4 border-t border-[#faf7f2]">
                            <span class="text-[11px] font-bold text-gray-300 uppercase tracking-widest">Beverage</span>
                            <div class="flex gap-2">
                                <a href="${pageContext.request.contextPath}/admin/product/edit?id=${b.productID}" class="w-9 h-9 rounded-full flex items-center justify-center hover:bg-[#974362]/10 text-gray-400 hover:text-[#974362] transition-colors">
                                    <span class="material-symbols-outlined text-xl">edit</span>
                                </a>
                                <a href="${pageContext.request.contextPath}/admin/product/delete?id=${b.productID}" onclick="return confirm('Xóa sản phẩm này?')" class="w-9 h-9 rounded-full flex items-center justify-center hover:bg-red-50 text-gray-400 hover:text-red-500 transition-colors">
                                    <span class="material-symbols-outlined text-xl">delete</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="mt-20 bg-[#f4e8f6] rounded-[2rem] p-10 border border-[#e6d0eb] relative overflow-hidden">
                <div class="absolute -right-10 -top-10 w-40 h-40 bg-white/40 blur-3xl rounded-full"></div>
                <h3 class="font-extrabold text-[#5c3166] text-2xl mb-8 flex items-center gap-3 relative z-10">
                    <span class="material-symbols-outlined text-3xl">bubble_chart</span>
                    Danh sách Toppings
                </h3>
                
                <div class="flex flex-wrap gap-4 relative z-10">
                    <c:forEach items="${toppings}" var="t">
                        <div class="bg-white/80 backdrop-blur px-6 py-3 rounded-2xl shadow-sm border border-white flex items-center gap-3 group hover:bg-white transition-all cursor-default">
                            <span class="w-2 h-2 rounded-full bg-[#974362]"></span>
                            <span class="font-bold text-[#5c3166] text-sm">${t.name}</span>
                            <span class="text-[11px] font-bold text-[#974362] bg-[#974362]/10 px-2 py-1 rounded-lg">+${t.price}đ</span>
                        </div>
                    </c:forEach>
                    
                    <c:if test="${empty toppings}">
                        <p class="text-sm font-medium text-[#5c3166]/60 italic">Chưa có topping nào trong hệ thống...</p>
                    </c:if>
                </div>
            </div>

            <%@ include file="/WEB-INF/views/admin/layout/footer.jsp" %>
            
        </main>
    </div>
</body>
</html>