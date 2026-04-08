<header class="bg-[#9D3C4D] text-white p-4 flex justify-between items-center shadow-lg sticky top-0 z-50">
    <div class="flex items-center space-x-4">
        <h1 class="text-2xl font-bold italic tracking-tighter">Chip3Chip</h1>
    </div>

    <div class="flex items-center space-x-4"> 
        
        <div class="flex items-center space-x-2">
            <form action="${pageContext.request.contextPath}/barista/shift" method="post">
                <button name="action" value="start"
                    class="px-5 py-2.5 rounded-xl bg-[#974362] text-white font-medium shadow-sm hover:bg-[#7a3550] transition-all flex items-center gap-2 whitespace-nowrap">
                    Clock In
                </button>
            </form>

            <form action="${pageContext.request.contextPath}/barista/shift" method="post">
                <button name="action" value="end"
                    class="px-5 py-2.5 rounded-xl bg-gray-200 text-gray-700 font-medium shadow-sm hover:bg-gray-300 transition-all flex items-center gap-2 whitespace-nowrap">
                    
                    Clock Out
                </button>
            </form>
        </div>

        <div class="flex items-center space-x-3 ml-2">
            <div class="text-right leading-tight">
                <p class="text-[10px] opacity-75 font-semibold uppercase tracking-widest">Staff Member</p>
                <p class="text-sm font-bold">${staffName}</p>
            </div>
            <div class="w-10 h-10 rounded-full border-2 border-orange-200 overflow-hidden shadow-inner">
                <img src="https://ui-avatars.com/api/?name=${staffName}&background=FDBA74&color=9D3C4D" alt="Avatar">
            </div>
        </div>

    </div>
</header>