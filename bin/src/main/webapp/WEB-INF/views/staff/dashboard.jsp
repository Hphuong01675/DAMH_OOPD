<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard | Chip3Chip Staff</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Plus Jakarta Sans', sans-serif; background-color: #FDF8F5; }
        .stat-card { transition: transform 0.2s; }
        .stat-card:hover { transform: translateY(-4px); }
    </style>
</head>
<body class="min-h-screen flex flex-col">

    <jsp:include page="layout/header.jsp" />

    <div class="flex flex-1 overflow-hidden">
        
        <jsp:include page="layout/sidebar.jsp" />

        <main class="flex-1 p-6 md:p-10 overflow-y-auto">
            
            <header class="mb-10">
                <h2 class="text-3xl font-bold text-gray-800">Hello, ${staffName}! 👋</h2>
                <p class="text-gray-500 font-medium">Here's what's happening at your station today.</p>
            </header>

            <div class="max-w-6xl mx-auto space-y-8">
                
                <div class="stat-card bg-white rounded-[2rem] p-10 shadow-sm border border-orange-50 flex flex-col items-center text-center">
                    <div class="bg-orange-100 p-4 rounded-2xl mb-4 text-[#9D3C4D]">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 9V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2m2 4h10a2 2 0 002-2v-6a2 2 0 00-2-2H9a2 2 0 00-2 2v6a2 2 0 002 2zm7-5a2 2 0 11-4 0 2 2 0 014 0z" />
                        </svg>
                    </div>
                    <span class="text-xs font-black text-gray-400 uppercase tracking-[0.3em] mb-3">Today's Total Revenue</span>
                    <h3 class="text-6xl font-extrabold text-gray-900 mb-4">
                        <fmt:formatNumber value="${stats.revenue}" type="currency" currencySymbol="$" />
                    </h3>
                    <div class="px-4 py-1.5 bg-green-50 rounded-full text-green-600 text-sm font-bold flex items-center">
                        <span class="w-2 h-2 bg-green-500 rounded-full mr-2"></span>
                        Verified SUCCESS payments
                    </div>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    
                    <div class="stat-card bg-white rounded-3xl p-8 shadow-sm border border-gray-50 flex items-center space-x-6">
                        <div class="p-4 bg-emerald-50 rounded-2xl text-emerald-600">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                        </div>
                        <div>
                            <p class="text-xs font-bold text-gray-400 uppercase tracking-widest">Completed</p>
                            <p class="text-4xl font-black text-gray-800">${stats.completedCount}</p>
                        </div>
                    </div>

                    <div class="stat-card bg-white rounded-3xl p-8 shadow-sm border border-gray-50 flex items-center space-x-6">
                        <div class="p-4 bg-rose-50 rounded-2xl text-rose-600">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                        </div>
                        <div>
                            <p class="text-xs font-bold text-gray-400 uppercase tracking-widest">Cancelled</p>
                            <p class="text-4xl font-black text-gray-800">${stats.cancelledCount}</p>
                        </div>
                    </div>

                </div>
            </div>
        </main>
    </div>

    <jsp:include page="layout/footer.jsp" />

</body>
</html>