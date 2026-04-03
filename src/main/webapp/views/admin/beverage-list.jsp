<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thực đơn | Chip3Chip Admin</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Plus Jakarta Sans', sans-serif; }
    </style>
</head>
<body class="bg-[#f8fafc] text-slate-900 min-h-screen p-4 md:p-10">

    <div class="max-w-6xl mx-auto">
        <header class="mb-10 flex justify-between items-center">
            <div>
                <h1 class="text-3xl font-extrabold text-indigo-950 tracking-tight">Thực đơn Beverage</h1>
                <p class="text-slate-500 mt-1">Quản lý danh sách món ăn và giá niêm yết hệ thống</p>
            </div>
            <div class="flex gap-3">
                 <a href="${pageContext.request.contextPath}/admin/beverages" 
                    class="bg-white border border-slate-200 text-slate-600 px-5 py-2.5 rounded-2xl font-bold hover:bg-slate-50 transition shadow-sm">
                    Làm mới danh sách
                 </a>
            </div>
        </header>

        <div class="bg-white rounded-[2rem] shadow-sm border border-slate-200 p-8 mb-10 transition-all">
            <div class="flex items-center gap-3 mb-8">
                <div class="w-2 h-8 bg-indigo-600 rounded-full"></div>
                <h2 class="text-xl font-bold text-slate-800">
                    <c:choose>
                        <c:when test="${not empty item}">
                            Chỉnh sửa món: <span class="text-indigo-600">${item.name}</span>
                        </c:when>
                        <c:otherwise>Thêm món mới vào thực đơn</c:otherwise>
                    </c:choose>
                </h2>
            </div>

            <form action="${pageContext.request.contextPath}/admin/beverage/save" method="POST" enctype="multipart/form-data" class="space-y-8">
                <input type="hidden" name="productID" value="${item.productID}">

                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                    <div class="space-y-2">
                        <label class="text-sm font-bold text-slate-500 uppercase tracking-wider ml-1">Tên đồ uống</label>
                        <input type="text" name="name" value="${item.name}" placeholder="Ví dụ: Trà sữa Trân châu" required 
                               class="w-full bg-slate-50 border-slate-200 rounded-2xl py-3.5 px-5 focus:ring-4 focus:ring-indigo-500/10 focus:border-indigo-500 transition-all outline-none">
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm font-bold text-slate-500 uppercase tracking-wider ml-1">Giá gốc (VNĐ)</label>
                        <input type="number" name="basePrice" value="${item.basePrice}" placeholder="Ví dụ: 35000" required 
                               class="w-full bg-slate-50 border-slate-200 rounded-2xl py-3.5 px-5 focus:ring-4 focus:ring-indigo-500/10 focus:border-indigo-500 transition-all outline-none">
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm font-bold text-slate-500 uppercase tracking-wider ml-1">Hình ảnh đại diện</label>
                        <div class="relative group">
                            <input type="file" name="image" 
                                   class="block w-full text-sm text-slate-500 file:mr-4 file:py-3 file:px-6 file:rounded-2xl file:border-0 file:bg-indigo-50 file:text-indigo-700 file:font-bold hover:file:bg-indigo-100 transition cursor-pointer border border-dashed border-slate-300 rounded-2xl p-1">
                            
                            <c:if test="${not empty item and not empty item.imgUrl}">
                                <div class="mt-3 flex items-center gap-3 p-2 bg-indigo-50/50 rounded-xl border border-indigo-100">
                                    <img src="${item.imgUrl}" class="w-12 h-12 rounded-lg object-cover shadow-sm border-2 border-white">
                                    <span class="text-xs text-indigo-600 font-semibold">Đang sử dụng ảnh này</span>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="flex flex-col md:flex-row justify-end gap-4 pt-4 border-t border-slate-100">
                    <c:if test="${not empty item}">
                        <a href="${pageContext.request.contextPath}/admin/beverages" 
                           class="px-8 py-3.5 rounded-2xl bg-slate-100 text-slate-600 font-bold hover:bg-slate-200 transition text-center">
                            Hủy bỏ chỉnh sửa
                        </a>
                    </c:if>
                    <button type="submit" class="px-12 py-3.5 rounded-2xl bg-indigo-600 text-white font-bold hover:bg-indigo-700 shadow-xl shadow-indigo-200 transition-all active:scale-95 text-center">
                        <c:choose>
                            <c:when test="${not empty item}">Cập nhật thay đổi</c:when>
                            <c:otherwise>Thêm vào Menu</c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form>
        </div>

        <div class="bg-white rounded-[2rem] shadow-sm border border-slate-200 overflow-hidden">
            <div class="p-6 border-b border-slate-100 bg-slate-50/30">
                <h3 class="font-bold text-slate-700">Danh sách thực đơn hiện có</h3>
            </div>
            <div class="overflow-x-auto">
                <table class="w-full text-left">
                    <thead>
                        <tr class="text-slate-400 text-[11px] uppercase font-bold tracking-[0.1em] border-b border-slate-100">
                            <th class="px-8 py-5">Sản phẩm</th>
                            <th class="px-8 py-5">Giá niêm yết</th>
                            <th class="px-8 py-5 text-right">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-slate-50">
                        <c:forEach items="${list}" var="b">
                            <tr class="group hover:bg-indigo-50/20 transition-all">
                                <td class="px-8 py-5">
                                    <div class="flex items-center gap-4">
                                        <div class="relative w-16 h-16 rounded-2xl overflow-hidden shadow-sm border border-slate-100 bg-slate-50 flex-shrink-0">
                                            <img src="${not empty b.imgUrl ? b.imgUrl : 'https://placehold.co/200x200?text=No+Image'}" 
                                                 class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500">
                                        </div>
                                        <div>
                                            <p class="font-bold text-slate-800 text-lg">${b.name}</p>
                                            <p class="text-xs text-slate-400">ID: #${b.productID}</p>
                                        </div>
                                    </div>
                                </td>
                                <td class="px-8 py-5">
                                    <span class="inline-flex items-center px-4 py-1.5 rounded-full bg-indigo-50 text-indigo-700 font-bold text-sm">
                                        ${b.basePrice} VNĐ
                                    </span>
                                </td>
                                <td class="px-8 py-5 text-right">
                                    <div class="flex justify-end gap-2">
                                        <a href="${pageContext.request.contextPath}/admin/beverage/edit?id=${b.productID}" 
                                           class="p-2 px-4 rounded-xl bg-white border border-slate-200 text-indigo-600 font-bold text-sm hover:bg-indigo-600 hover:text-white hover:border-indigo-600 transition-all">
                                            Sửa
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/beverage/delete?id=${b.productID}" 
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa món [${b.name}] này không?')"
                                           class="p-2 px-4 rounded-xl bg-white border border-slate-200 text-red-500 font-bold text-sm hover:bg-red-500 hover:text-white hover:border-red-500 transition-all">
                                            Xóa
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        
                        <c:if test="${empty list}">
                            <tr>
                                <td colspan="3" class="px-8 py-20 text-center text-slate-400 italic">
                                    Chưa có món nước nào trong thực đơn. Hãy thêm món mới!
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        
        <footer class="mt-10 text-center text-slate-400 text-sm">
            &copy; 2026 Chip3Chip Beverage Management System.
        </footer>
    </div>

</body>
</html>