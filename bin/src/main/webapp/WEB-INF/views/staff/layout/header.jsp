<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="bg-[#9D3C4D] text-white p-4 flex justify-between items-center shadow-lg sticky top-0 z-50">
    <div class="flex items-center space-x-4">
        <h1 class="text-2xl font-bold italic tracking-tighter">Chip3Chip</h1>
    </div>
    <div class="flex items-center space-x-4">
        <div class="text-right">
            <p class="text-xs opacity-75 font-semibold uppercase tracking-widest">Staff Member</p>
            <p class="text-sm font-bold">${staffName}</p>
        </div>
        <div class="w-10 h-10 rounded-full border-2 border-orange-200 overflow-hidden shadow-inner">
            <img src="https://ui-avatars.com/api/?name=${staffName}&background=FDBA74&color=9D3C4D" alt="Avatar">
        </div>
    </div>
</header>