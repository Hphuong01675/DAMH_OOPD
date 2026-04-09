<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="currentPath" value="${pageContext.request.servletPath}" />
<aside class="w-64 fixed left-0 top-0 h-screen bg-[#faf7f2] flex flex-col border-r border-[#ebe5df] z-50">
    <div class="p-8 flex items-center gap-4">
        <div class="w-12 h-12 bg-[#fbccd9] rounded-2xl flex items-center justify-center text-[#974362] shadow-sm">
            <span class="material-symbols-outlined text-2xl">storefront</span>
        </div>
        <div>
            <h3 class="font-extrabold text-[#974362] text-lg leading-tight">Chip3Chip</h3>
            <p class="text-[11px] font-bold text-gray-400 uppercase tracking-widest">Admin Store</p>
        </div>
    </div>

    <nav class="flex flex-col gap-2 px-4 mt-4">
        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="py-3.5 px-5 rounded-full flex items-center gap-4 text-sm transition-all ${currentPath == '/admin/dashboard' ? 'font-bold bg-white text-[#974362] shadow-[0_4px_20px_rgba(151,67,98,0.08)]' : 'font-semibold opacity-60 hover:opacity-100 hover:bg-white/50'}">
            <span class="material-symbols-outlined text-[22px]" style="${currentPath == '/admin/dashboard' ? "font-variation-settings: 'FILL' 1" : ''}">dashboard</span> Dashboard
        </a>
        
        <a href="${pageContext.request.contextPath}/admin/products"
           class="py-3.5 px-5 rounded-full flex items-center gap-4 text-sm transition-all ${currentPath == '/admin/products' || currentPath == '/admin/product/edit' || currentPath == '/admin/product/save' || currentPath == '/admin/product/toggle' ? 'font-bold bg-white text-[#974362] shadow-[0_4px_20px_rgba(151,67,98,0.08)]' : 'font-semibold opacity-60 hover:opacity-100 hover:bg-white/50'}">
            <span class="material-symbols-outlined text-[22px]" style="${currentPath == '/admin/products' || currentPath == '/admin/product/edit' || currentPath == '/admin/product/save' || currentPath == '/admin/product/toggle' ? "font-variation-settings: 'FILL' 1" : ''}">restaurant_menu</span> Menu
        </a>
        
        <a href="${pageContext.request.contextPath}/admin/notifications"
           class="py-3.5 px-5 rounded-full flex items-center gap-4 text-sm transition-all ${currentPath == '/admin/notifications' ? 'font-bold bg-white text-[#974362] shadow-[0_4px_20px_rgba(151,67,98,0.08)]' : 'font-semibold opacity-60 hover:opacity-100 hover:bg-white/50'}">
            <span class="material-symbols-outlined text-[22px]" style="${currentPath == '/admin/notifications' ? "font-variation-settings: 'FILL' 1" : ''}">notifications</span> Notification
        </a>
    </nav>

    <div class="mt-auto p-4">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit"
                    class="w-full py-3.5 px-5 rounded-full flex items-center justify-center gap-3 text-sm font-bold text-[#974362] bg-white hover:bg-[#fff7fb] transition-all shadow-[0_4px_20px_rgba(151,67,98,0.08)]">
                <span class="material-symbols-outlined text-[20px]">logout</span>
                Logout
            </button>
        </form>
    </div>
</aside>
