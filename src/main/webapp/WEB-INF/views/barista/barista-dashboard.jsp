<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Barista Dashboard | Chip3Chip</title>

<script src="https://cdn.tailwindcss.com"></script>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet" />

<script>
tailwind.config = {
  theme: {
    extend: {
      colors: {
        /* Bảng màu thiết kế mới của Dashboard */
        primary: "#9C3D54",
        soft: "#F6F2EF",
        card: "#FFFFFF",
        accent: "#EADDE0",
        green: "#C8F2D0",
        purple: "#B57EDC",
        
        /* Bảng màu cũ bắt buộc phải giữ lại để file Sidebar.jsp không bị vỡ giao diện */
        "tertiary": "#853bb5",
        "surface-container-low": "#f9f3ed",
        "surface-container-lowest": "#ffffff",
        "on-surface": "#35322d",
        "on-surface-variant": "#6B645E"
      }
    }
  }
}
</script>
<style>
  .editorial-shadow { box-shadow: 0 8px 32px rgba(53, 50, 45, 0.06); }
  .material-symbols-outlined { font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24; }
</style>
</head>

<body class="bg-soft text-gray-800">
<%@ include file="/WEB-INF/views/barista/layout/header.jsp" %>
<%@ include file="/WEB-INF/views/barista/layout/sidebar.jsp" %>

<div class="p-8 md:ml-64 min-h-screen">

  <div class="flex flex-col md:flex-row justify-between md:items-center mb-8 gap-4">
    <div>
      <h1 class="text-4xl font-bold text-primary">Welcome, ${sessionScope.user.fullName}</h1>
      <p class="text-gray-500 mt-1">Crafting excellence ☕</p>
    </div>

    <div class="flex items-center gap-4">
        
    </div>
  </div>

  <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">

    <div class="md:col-span-2 bg-card p-6 rounded-2xl shadow">
      <p class="text-gray-400 text-sm mb-2 font-semibold">TODAY'S REVENUE</p>
      <h2 class="text-4xl font-bold text-gray-800">$${totalRevenue}</h2>
      <p class="text-gray-400 text-sm mt-2 italic">
        "Exceeding yesterday's average"
      </p>
    </div>

    <div class="bg-card p-6 rounded-2xl shadow flex flex-col justify-center">
      <p class="text-gray-400 text-sm mb-2 font-semibold">ORDERS PROCESSED</p>
      <h2 class="text-4xl font-bold text-gray-800">${totalOrders}</h2>
      <div class="bg-gray-100 h-2 rounded-full mt-4 overflow-hidden">
        <div class="bg-purple h-2 rounded-full" style="width:${(totalOrders / 200) * 100}%"></div>
      </div>
      <p class="text-xs text-gray-400 mt-2 font-medium">
        Daily goal: 200 orders
      </p>
    </div>

  </div>

  <div class="grid grid-cols-1 md:grid-cols-3 gap-6">

    <div class="md:col-span-2 bg-card p-6 rounded-2xl shadow">
      <div class="flex justify-between items-center mb-4">
        <h3 class="font-bold text-gray-800">Performance Chart</h3>
        <span class="text-xs bg-accent text-primary px-3 py-1 rounded-full font-semibold">
          Last 8 Hours
        </span>
      </div>

      <div class="flex items-end gap-3 h-40">
        <c:choose>
            <c:when test="${not empty chartData}">
                <c:forEach var="bar" items="${chartData}">
                    <div class="w-10 rounded-t-xl transition-all duration-300 hover:opacity-80
                                ${bar.isMax ? 'bg-primary shadow-lg' : 'bg-[#EE86A6]'}" 
                         style="height: ${bar.percentage}%;">
                        <span class="opacity-0 hover:opacity-100 text-[10px] text-center block -mt-5 font-bold text-gray-600">
                            ${bar.value}
                        </span>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="w-full h-full flex items-center justify-center text-gray-400 text-sm italic border-2 border-dashed border-gray-100 rounded-xl">
                    Chưa có dữ liệu thống kê biểu đồ
                </div>
            </c:otherwise>
        </c:choose>
      </div>
    </div> <div class="bg-card p-6 rounded-2xl shadow">
      <h3 class="font-bold text-gray-800 mb-6 flex items-center gap-2">
          <span class="material-symbols-outlined text-primary">local_cafe</span> Top Drinks
      </h3>

      <div class="space-y-5">
        <c:forEach var="drink" items="${topDrinks}" varStatus="status">
            <div class="flex items-center justify-between group">
              <div class="flex items-center gap-4">
                
                <c:choose>
                    <c:when test="${status.index == 0}">
                        <div class="w-12 h-12 bg-red-100 text-red-600 rounded-full flex items-center justify-center font-bold text-lg shadow-sm">
                            ${drink.beverageName.substring(0,1)}
                        </div>
                    </c:when>
                    <c:when test="${status.index == 1}">
                        <div class="w-12 h-12 bg-green font-bold text-green-700 rounded-full flex items-center justify-center text-lg shadow-sm">
                            ${drink.beverageName.substring(0,1)}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="w-12 h-12 bg-accent font-bold text-primary rounded-full flex items-center justify-center text-lg shadow-sm">
                            ${drink.beverageName.substring(0,1)}
                        </div>
                    </c:otherwise>
                </c:choose>

                <div>
                  <p class="font-bold text-gray-800 group-hover:text-primary transition-colors">${drink.beverageName}</p>
                  <p class="text-xs text-gray-400 font-medium">${drink.totalQuantity} cups crafted</p>
                </div>
              </div>
            </div>
        </c:forEach>

        <c:if test="${empty topDrinks}">
            <div class="flex flex-col items-center justify-center py-6 opacity-40">
                <span class="material-symbols-outlined text-4xl mb-2">inventory_2</span>
                <p class="text-sm font-medium">No drinks yet.</p>
            </div>
        </c:if>
      </div>
    </div> </div> </div> </body>
</html>