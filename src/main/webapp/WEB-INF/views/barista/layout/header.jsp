<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<header class="h-24 flex justify-between items-center px-10 sticky top-0 bg-[#faf7f2]/90 backdrop-blur-md z-40 md:ml-64">

    <div class="flex items-center gap-4">
        <form action="${pageContext.request.contextPath}/barista/shift" method="post">
            <button name="action" value="start"
                class="px-5 py-2.5 rounded-xl bg-[#974362] text-white font-medium shadow-sm hover:bg-[#7a3550] transition-all flex items-center gap-2">
                Vào ca
            </button>
        </form>

        <form action="${pageContext.request.contextPath}/barista/shift" method="post">
            <button name="action" value="end"
                class="px-5 py-2.5 rounded-xl bg-gray-200 text-gray-700 font-medium shadow-sm hover:bg-gray-300 transition-all flex items-center gap-2">
                <span class="material-symbols-outlined text-[20px]">logout</span>
                Kết ca
            </button>
        </form>
    </div>

    <div class="flex items-center gap-4 relative">
        <a href="${pageContext.request.contextPath}/barista/orders" 
           class="bg-[#C8F2D0] px-5 py-2 rounded-full text-green-700 font-medium flex items-center gap-2 hover:bg-green/80 transition shadow-sm">
            <span class="w-2 h-2 bg-green-700 rounded-full animate-pulse"></span>
            Live Queue: ${pendingOrders}
        </a>

        <div onclick="toggleMenu()"
             class="w-12 h-12 rounded-full overflow-hidden border-2 border-white shadow-sm cursor-pointer hover:scale-105 transition-transform bg-[#EADDE0] flex items-center justify-center text-primary font-bold">
            ${sessionScope.user.fullName.substring(0,1).toUpperCase()}
        </div>

        <div id="menu" class="hidden absolute right-0 top-14 w-40 bg-white rounded-xl shadow-lg border border-gray-100 overflow-hidden">
            <div class="px-4 py-3 text-sm text-gray-500 italic">Không có tùy chọn</div>
        </div>
    </div>

</header>

<script>
    function toggleMenu() {
        const menu = document.getElementById("menu");
        menu.classList.toggle("hidden");
    }
    document.addEventListener("click", function (e) {
        const menu = document.getElementById("menu");
        if (!e.target.closest(".relative")) { menu.classList.add("hidden"); }
    });
</script>