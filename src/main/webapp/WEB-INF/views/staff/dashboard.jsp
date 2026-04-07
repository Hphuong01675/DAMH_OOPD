<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Staff Dashboard | Chip3Chip</title>
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Plus Jakarta Sans', sans-serif;
	background-color: #FDF8F5;
}

.bg-maroon {
	background-color: #9D3C4D;
}

.text-maroon {
	color: #9D3C4D;
}
</style>
</head>
<body class="min-h-screen flex flex-col">
	<header
		class="bg-maroon text-white p-4 flex justify-between items-center shadow-md">
		<div class="flex items-center space-x-4">
			<h1 class="text-2xl font-bold italic">Chip3Chip</h1>
			<nav class="hidden md:flex space-x-4 text-sm font-medium opacity-90">
				<a href="#" class="border-b-2 border-white pb-1">Dashboard</a> <a
					href="#" class="hover:opacity-75">Orders</a>
			</nav>
		</div>
		<div class="flex items-center space-x-4">
			<span class="text-sm font-semibold">${staff.fullName}</span>
			<div
				class="w-10 h-10 rounded-full bg-orange-200 border-2 border-white overflow-hidden">
				<img
					src="https://ui-avatars.com/api/?name=${staff.fullName}&background=random"
					alt="Avatar">
			</div>
		</div>
	</header>

	<main class="flex-grow p-6 md:p-12 max-w-5xl mx-auto w-full">
		<section class="mb-10 text-center md:text-left">
			<h2 class="text-4xl font-bold text-gray-800 mb-2">Welcome,
				${staff.fullName}</h2>
			<p class="text-gray-500 font-medium italic">
				Crafting excellence •
				<fmt:formatDate value="<%=new java.util.Date()%>"
					pattern="MMM dd, yyyy" />
			</p>
		</section>

		<div class="grid grid-cols-1 gap-8">
			<div
				class="bg-white rounded-3xl p-8 shadow-sm border border-orange-50 flex flex-col items-center justify-center text-center">
				<div class="bg-orange-50 p-4 rounded-2xl mb-4">
					<svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-maroon"
						fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round"
							stroke-linejoin="round" stroke-width="2"
							d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
				</div>
				<span
					class="text-sm font-bold text-gray-400 tracking-widest uppercase mb-2">Today's
					Revenue</span>
				<h3 class="text-6xl font-bold text-gray-800 mb-2">
					<fmt:formatNumber value="${stats.revenue}" type="currency"
						currencySymbol="$" />
				</h3>
				<p class="text-green-500 font-semibold flex items-center">Ready
					for shift closing</p>
			</div>

			<div class="grid grid-cols-1 md:grid-cols-2 gap-6">
				<div
					class="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 flex items-center space-x-6">
					<div class="bg-green-100 p-4 rounded-xl text-green-600">
						<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6"
							fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                        </svg>
					</div>
					<div>
						<p
							class="text-sm font-bold text-gray-400 uppercase tracking-tight">Completed
							Orders</p>
						<p class="text-3xl font-bold text-gray-800">${stats.completedCount}</p>
					</div>
				</div>

				<div
					class="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 flex items-center space-x-6">
					<div class="bg-red-100 p-4 rounded-xl text-red-600">
						<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6"
							fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2"
								d="M6 18L18 6M6 6l12 12" />
                        </svg>
					</div>
					<div>
						<p
							class="text-sm font-bold text-gray-400 uppercase tracking-tight">Cancelled
							Orders</p>
						<p class="text-3xl font-bold text-gray-800">${stats.cancelledCount}</p>
					</div>
				</div>
			</div>
		</div>
	</main>

	<footer class="p-6 bg-white border-t border-gray-100 mt-auto">
		<div class="max-w-5xl mx-auto flex items-center space-x-2">
			<div class="w-2 h-2 bg-green-500 rounded-full animate-pulse"></div>
			<span
				class="text-xs font-bold text-gray-500 uppercase tracking-widest">Active
				Session | Station 01</span>
		</div>
	</footer>
</body>
</html>