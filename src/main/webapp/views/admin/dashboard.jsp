<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard | Chip3Chip</title>

    <!-- Tailwind -->
    <script src="https://cdn.tailwindcss.com?plugins=forms"></script>

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />

    <style>
        body { font-family: 'Plus Jakarta Sans', sans-serif; }
    </style>
</head>

<body class="bg-[#faf7f2] text-[#35322d] flex min-h-screen">

    <%@ include file="/views/admin/layout/sidebar.jsp" %>

    <div class="flex-1 ml-64 flex flex-col">
        <%@ include file="/views/admin/layout/header.jsp" %>

        <main class="px-10 pb-8 flex-1">

            <!-- HEADER -->
            <div class="mb-10 mt-4">
                <h1 class="text-4xl font-extrabold tracking-tight mb-2">
                    Dashboard
                </h1>
                <p class="text-gray-500 text-sm">
                    Thống kê hoạt động hệ thống bán hàng
                </p>
            </div>

            <!-- STATS -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mb-10">

                <!-- Revenue -->
                <div class="bg-white rounded-[2rem] p-6 shadow border border-[#f3ede6]">
                    <p class="text-sm text-gray-400">Doanh thu hôm nay</p>
                    <h2 class="text-3xl font-extrabold text-[#974362] mt-2">
                        ${totalRevenue} đ
                    </h2>
                </div>

                <!-- Orders -->
                <div class="bg-white rounded-[2rem] p-6 shadow border border-[#f3ede6]">
                    <p class="text-sm text-gray-400">Số đơn hôm nay</p>
                    <h2 class="text-3xl font-extrabold text-[#974362] mt-2">
                        ${totalOrders}
                    </h2>
                </div>

                <!-- Best Seller -->
                <div class="bg-white rounded-[2rem] p-6 shadow border border-[#f3ede6]">
                    <p class="text-sm text-gray-400">Sản phẩm bán chạy</p>
                    <h2 class="text-xl font-extrabold text-[#974362] mt-2">
                        ${bestSeller != null ? bestSeller.name : 'Chưa có dữ liệu'}
                    </h2>
                </div>

            </div>

            <!-- CHART -->
            <div class="bg-white rounded-[2rem] p-8 shadow border border-[#f3ede6]">

                <div class="flex justify-between items-center mb-6">
                    <h3 class="text-lg font-bold text-[#974362]">
                        Doanh thu trong tuần
                    </h3>
                </div>

                <canvas id="weeklyChart" height="100"></canvas>
            </div>

        </main>
    </div>

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <script>
        const ctx = document.getElementById('weeklyChart');

        const chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ${days},      // JSON từ controller
                datasets: [{
                    label: 'Doanh thu (VNĐ)',
                    data: ${revenues}, // JSON từ controller
                    borderWidth: 2,
                    borderRadius: 8
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>

</body>
</html>