<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông báo Barista | Chip3Chip</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@300;400;500;700&display=swap" rel="stylesheet" />

    <script>
    tailwind.config = {
      theme: {
        extend: {
          colors: {
            primary: "#9C3D54",
            soft: "#F6F2EF",
            card: "#FFFFFF",
            accent: "#EADDE0"
          }
        }
      }
    }
    </script>
    
    <style>
        /* Cắt nội dung chỉ hiển thị 1 dòng kèm dấu ... */
        .line-clamp-1 {
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;  
            overflow: hidden;
        }
        .custom-scrollbar::-webkit-scrollbar { width: 4px; }
        .custom-scrollbar::-webkit-scrollbar-thumb { background: #EADDE0; border-radius: 10px; }
    </style>
</head>

<body class="bg-soft text-gray-800">

    <%@ include file="/WEB-INF/views/barista/layout/header.jsp" %>
    <%@ include file="/WEB-INF/views/barista/layout/sidebar.jsp" %>

    <main class="p-8 md:ml-64 min-h-screen">
        
        <div class="max-w-3xl mx-auto">
            <div class="mb-8">
                <h1 class="text-3xl font-bold text-primary flex items-center gap-3">
                    <span class="material-symbols-outlined text-4xl">inbox</span>
                    Hộp thư Barista
                </h1>
                <p class="text-gray-500 mt-1">Bấm vào từng dòng để xem chi tiết thông báo</p>
            </div>

            <div class="bg-card rounded-3xl shadow-sm border border-gray-100 overflow-hidden">
                <div class="divide-y divide-gray-50">
                    
                    <c:choose>
                        <c:when test="${not empty notifications}">
                            <c:forEach var="noti" items="${notifications}">
                                <div onclick="openNotification('${noti.notificationID}', '${noti.content}', '${noti.sentDate}')" 
                                     class="p-6 hover:bg-soft/50 transition-all cursor-pointer group flex items-start gap-4">
                                    
                                    <div class="w-10 h-10 rounded-full bg-accent/30 flex items-center justify-center text-primary shrink-0 group-hover:bg-primary group-hover:text-white transition-all">
                                        <span class="material-symbols-outlined">notifications</span>
                                    </div>
                                    
                                    <div class="flex-1 min-w-0">
                                        <div class="flex justify-between items-start mb-1">
                                            <%-- TIÊU ĐỀ: Lấy ID làm tiêu đề vì Entity không có trường title --%>
                                            <h3 class="font-bold text-gray-800 group-hover:text-primary transition-colors">
                                                Thông báo #${noti.notificationID}
                                            </h3>
                                            <span class="text-[10px] text-gray-400 font-medium shrink-0 ml-4 italic">
                                                ${noti.sentDate}
                                            </span>
                                        </div>
                                        
                                        <%-- NỘI DUNG: Hiện 1 dòng đầu tiên [cite: 21] --%>
                                        <p class="text-sm text-gray-500 line-clamp-1 italic">
                                            ${noti.content}
                                        </p>
                                    </div>
                                    
                                    <span class="material-symbols-outlined text-gray-300 group-hover:translate-x-1 transition-transform self-center">
                                        arrow_forward_ios
                                    </span>
                                </div>
                            </c:forEach>
                        </c:when>
                        
                        <c:otherwise>
                            <div class="py-20 flex flex-col items-center justify-center opacity-30">
                                <span class="material-symbols-outlined text-6xl">drafts</span>
                                <p class="mt-4 font-bold">Hộp thư trống</p>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
    </main>

    <div id="notiModal" class="hidden fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm p-4">
        <div class="bg-white w-full max-w-lg rounded-3xl shadow-2xl overflow-hidden transform transition-all scale-95 opacity-0" id="modalContent">
            <div class="p-6 border-b border-gray-100 flex justify-between items-center bg-gray-50/50">
                <h2 id="mTitle" class="text-xl font-bold text-primary uppercase tracking-tight"></h2>
                <button onclick="closeModal()" class="text-gray-400 hover:text-gray-600">
                    <span class="material-symbols-outlined">close</span>
                </button>
            </div>
            <div class="p-8">
                <p id="mBody" class="text-gray-700 leading-relaxed text-lg"></p>
                <div id="mDate" class="mt-8 text-xs text-gray-400 border-t pt-4 italic"></div>
            </div>
            <div class="p-4 bg-gray-50 text-right">
                <button onclick="closeModal()" class="px-8 py-2 bg-primary text-white rounded-xl font-bold hover:opacity-90">Đã hiểu</button>
            </div>
        </div>
    </div>

    <script>
        function openNotification(id, content, date) {
            const modal = document.getElementById('notiModal');
            const box = document.getElementById('modalContent');
            
            // Gán dữ liệu vào Modal
            document.getElementById('mTitle').innerText = "Thông báo #" + id;
            document.getElementById('mBody').innerText = content;
            document.getElementById('mDate').innerText = "Ngày gửi: " + date;

            // Hiện Modal với hiệu ứng
            modal.classList.remove('hidden');
            setTimeout(() => {
                box.classList.remove('scale-95', 'opacity-0');
                box.classList.add('scale-100', 'opacity-100');
            }, 10);
        }

        function closeModal() {
            const modal = document.getElementById('notiModal');
            const box = document.getElementById('modalContent');
            
            box.classList.add('scale-95', 'opacity-0');
            setTimeout(() => {
                modal.classList.add('hidden');
            }, 200);
        }

        // Đóng khi bấm ra ngoài Modal
        window.onclick = function(e) {
            if (e.target == document.getElementById('notiModal')) closeModal();
        }
    </script>

</body>
</html>