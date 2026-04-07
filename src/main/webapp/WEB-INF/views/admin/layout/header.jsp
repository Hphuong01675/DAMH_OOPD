<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<header class="h-24 flex justify-end items-center px-10 sticky top-0 bg-[#faf7f2]/90 backdrop-blur-md z-40">
    <div class="flex items-center">
        <div class="w-12 h-12 rounded-full overflow-hidden border-2 border-white shadow-sm cursor-pointer hover:scale-105 transition-transform">
            <img src="https://ui-avatars.com/api/?name=${not empty sessionScope.displayName ? sessionScope.displayName : 'User'}&background=974362&color=fff" class="w-full h-full object-cover">
        </div>
    </div>
</header>
