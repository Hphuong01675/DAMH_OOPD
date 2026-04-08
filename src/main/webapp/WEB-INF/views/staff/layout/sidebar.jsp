<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="w-64 bg-white border-r border-gray-100 hidden md:flex flex-col shadow-sm h-screen sticky top-0">
    <div class="p-6 flex flex-col h-full">

        <div class="flex flex-col mb-10 px-2">
            <h1 class="text-2xl font-bold italic tracking-tighter leading-none text-[#9D3C4D]">Chip3Chip</h1>
            <span class="text-[10px] opacity-60 uppercase tracking-[0.2em] mt-1 font-semibold">Staff Station</span>
        </div>

        <nav class="flex-1 space-y-2">
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest px-3 mb-4">Main Menu</p>

            <a href="${pageContext.request.contextPath}/staff/dashboard"
               class="flex items-center space-x-3 p-3 rounded-xl bg-orange-50 text-[#9D3C4D] font-bold transition-all">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z" />
                </svg>
                <span>Dashboard</span>
            </a>

            <a href="${pageContext.request.contextPath}/order"
               class="flex items-center space-x-3 p-3 rounded-xl text-gray-500 hover:bg-gray-50 hover:text-[#9D3C4D] transition-all">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
                </svg>
                <span>Orders</span>
            </a>

            <a href="${pageContext.request.contextPath}/staff/notifications"
               class="flex items-center space-x-3 p-3 rounded-xl text-gray-500 hover:bg-gray-50 hover:text-[#9D3C4D] transition-all">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
                </svg>
                <span>Notifications</span>
            </a>
        </nav>

        <div class="mt-auto pt-6 border-t border-gray-50">
            <form action="${pageContext.request.contextPath}/logout" method="post" class="mb-4">
                <button type="submit" 
                        class="w-full flex items-center space-x-3 p-3 rounded-xl text-gray-500 hover:bg-red-50 hover:text-red-600 transition-all">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
                    </svg>
                    <span class="font-bold text-sm">Logout</span>
                </button>
            </form>
            <p class="text-[10px] text-gray-400 text-center uppercase tracking-widest font-semibold opacity-50">
                Chip3Chip v1.0.4
            </p>
        </div>
    </div>
</aside>
