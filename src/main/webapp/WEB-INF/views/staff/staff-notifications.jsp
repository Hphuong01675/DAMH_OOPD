<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Staff Inbox | Chip3Chip</title>

            <script src="https://cdn.tailwindcss.com"></script>
            <link
                href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@300;400;500;700&display=swap"
                rel="stylesheet" />

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
                .line-clamp-1 {
                    display: -webkit-box;
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                    overflow: hidden;
                }

                .custom-scrollbar::-webkit-scrollbar {
                    width: 4px;
                }

                .custom-scrollbar::-webkit-scrollbar-track {
                    background: transparent;
                }

                .custom-scrollbar::-webkit-scrollbar-thumb {
                    background: #EADDE0;
                    border-radius: 10px;
                }
            </style>
        </head>

        <body class="bg-soft text-gray-800">

            <div class="flex min-h-screen">
                <!-- SIDEBAR -->
                <jsp:include page="/WEB-INF/views/staff/layout/sidebar.jsp" />

                <div class="flex-1 flex flex-col min-w-0">
                    <!-- HEADER -->
                    <jsp:include page="/WEB-INF/views/staff/layout/header.jsp" />

                    <!-- CONTENT -->
                    <main class="p-8 flex-1">
                        <div class="max-w-4xl mx-auto">
                            <div class="mb-8">
                                <h1 class="text-3xl font-bold text-primary flex items-center gap-3">
                                    <span class="material-symbols-outlined text-4xl">inbox</span>
                                    Staff Inbox
                                </h1>
                                <p class="text-gray-500 mt-1 italic">Click on any notification to view details</p>
                            </div>

                            <div class="bg-card rounded-3xl shadow-sm border border-gray-100 overflow-hidden">
                                <div class="divide-y divide-gray-50 max-h-[600px] overflow-y-auto custom-scrollbar">

                                    <c:choose>
                                        <c:when test="${not empty notifications}">
                                            <c:forEach var="n" items="${notifications}">
                                                <div onclick="openNotification('${n.notificationID}', '${n.content}', '${n.sentDate}')"
                                                    class="p-6 hover:bg-soft/50 transition-all cursor-pointer group flex items-start gap-4 border-l-4 border-transparent hover:border-primary">

                                                    <div
                                                        class="w-10 h-10 rounded-full bg-accent/30 flex items-center justify-center text-primary shrink-0 group-hover:bg-primary group-hover:text-white transition-all shadow-sm">
                                                        <span class="material-symbols-outlined">notifications</span>
                                                    </div>

                                                    <div class="flex-1 min-w-0">
                                                        <div class="flex justify-between items-start mb-1">
                                                            <h3
                                                                class="font-bold text-gray-800 group-hover:text-primary transition-colors">
                                                                Notification #${n.notificationID}
                                                            </h3>
                                                            <span
                                                                class="text-[10px] text-gray-400 font-medium shrink-0 ml-4 italic bg-gray-50 px-2 py-1 rounded-full">
                                                                ${n.sentDate}
                                                            </span>
                                                        </div>
                                                        <p class="text-sm text-gray-500 line-clamp-1">
                                                            ${n.content}
                                                        </p>
                                                    </div>

                                                    <span
                                                        class="material-symbols-outlined text-gray-300 group-hover:translate-x-1 transition-transform self-center">
                                                        arrow_forward_ios
                                                    </span>
                                                </div>
                                            </c:forEach>
                                        </c:when>

                                        <c:otherwise>
                                            <div class="py-24 flex flex-col items-center justify-center text-gray-400">
                                                <div
                                                    class="w-20 h-20 rounded-full bg-gray-50 flex items-center justify-center mb-4">
                                                    <span
                                                        class="material-symbols-outlined text-5xl opacity-20">drafts</span>
                                                </div>
                                                <p class="font-bold text-lg">Your inbox is empty</p>
                                                <p class="text-sm">Notifications will appear here when assigned to you.
                                                </p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                    </main>

                    <!-- FOOTER -->
                    <jsp:include page="/WEB-INF/views/staff/layout/footer.jsp" />
                </div>
            </div>

            <!-- NOTIFICATION MODAL -->
            <div id="notiModal"
                class="hidden fixed inset-0 z-[100] flex items-center justify-center bg-black/40 backdrop-blur-sm p-4">
                <div class="bg-white w-full max-w-lg rounded-3xl shadow-2xl overflow-hidden transform transition-all scale-95 opacity-0"
                    id="modalContent">
                    <div class="p-6 border-b border-gray-100 flex justify-between items-center bg-gray-50/50">
                        <h2 id="mTitle" class="text-xl font-bold text-primary uppercase tracking-tight"></h2>
                        <button onclick="closeModal()"
                            class="w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-200 transition-colors text-gray-400 hover:text-gray-600">
                            <span class="material-symbols-outlined text-xl">close</span>
                        </button>
                    </div>
                    <div class="p-8">
                        <div class="bg-soft/30 p-6 rounded-2xl border border-accent/20">
                            <p id="mBody" class="text-gray-700 leading-relaxed text-lg"></p>
                        </div>
                        <div class="mt-8 flex items-center justify-between text-[11px] text-gray-400 border-t pt-4">
                            <span class="flex items-center gap-1 italic">
                                <span class="material-symbols-outlined text-[14px]">calendar_today</span>
                                Sent Date: <span id="mDate"></span>
                            </span>
                            <span class="uppercase font-bold tracking-widest opacity-50">Chip3Chip Staff</span>
                        </div>
                    </div>
                    <div class="p-4 bg-gray-50 text-right">
                        <button onclick="closeModal()"
                            class="px-8 py-2.5 bg-primary text-white rounded-xl font-bold hover:scale-[1.02] active:scale-95 transition-all shadow-md shadow-primary/20">
                            Got it
                        </button>
                    </div>
                </div>
            </div>

            <script>
                function openNotification(id, content, date) {
                    const modal = document.getElementById('notiModal');
                    const box = document.getElementById('modalContent');

                    // Gán dữ liệu vào Modal
                    document.getElementById('mTitle').innerText = "Notification #" + id;
                    document.getElementById('mBody').innerText = content;
                    document.getElementById('mDate').innerText = date;

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
                window.onclick = function (e) {
                    if (e.target == document.getElementById('notiModal')) closeModal();
                }
            </script>

        </body>

        </html>