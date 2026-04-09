<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="bg-[#9D3C4D] text-white p-4 flex justify-between items-center shadow-lg sticky top-0 z-50">
    <div class="flex items-center space-x-8">
        <div class="flex items-center space-x-3 ml-4">
            <button class="flex items-center px-4 py-1.5 bg-[#7A2E3C] hover:bg-[#5E242E] text-white text-sm font-semibold rounded-lg transition-all shadow-sm">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
                </svg>
                Vào ca
            </button>
            <button class="flex items-center px-4 py-1.5 bg-white text-[#9D3C4D] hover:bg-gray-100 text-sm font-semibold rounded-lg transition-all shadow-sm">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
                </svg>
                Kết ca
            </button>
        </div>
    </div>

    <div class="flex items-center space-x-4">
        <div class="text-right">
            <p class="text-[10px] opacity-75 font-semibold uppercase tracking-widest leading-none">Staff Member</p>
            <p class="text-sm font-bold">${staffName}</p>
        </div>
        <div class="w-10 h-10 rounded-full border-2 border-white/30 overflow-hidden shadow-inner">
            <img src="https://ui-avatars.com/api/?name=${staffName}&background=ffffff&color=9D3C4D" alt="Avatar">

        </div>

    </div>
</header>