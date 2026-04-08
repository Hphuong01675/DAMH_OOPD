<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="light" lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Staff Dashboard - Chip3Chip</title>

    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=Be+Vietnam+Pro:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet">

    <script id="tailwind-config">
        tailwind.config = {
          darkMode: "class",
          theme: {
            extend: {
              colors: {
                "secondary-fixed-dim": "#bdefbe",
                "inverse-on-surface": "#a09c98",
                "surface-bright": "#fef8f3",
                "on-primary-container": "#6c203f",
                "surface": "#fef8f3",
                "primary-dim": "#883756",
                "secondary-dim": "#315c37",
                "primary-fixed": "#ffa8c4",
                "on-secondary-fixed-variant": "#426e47",
                "tertiary-container": "#dca2ff",
                "inverse-primary": "#fe97b9",
                "surface-variant": "#e8e1da",
                "primary": "#974362",
                "on-background": "#35322d",
                "on-surface-variant": "#625e59",
                "inverse-surface": "#0f0e0b",
                "error": "#ac3149",
                "on-error": "#fff7f7",
                "surface-dim": "#e0d9d1",
                "surface-container": "#f3ede6",
                "tertiary-fixed": "#dca2ff",
                "primary-fixed-dim": "#fb94b7",
                "outline-variant": "#b7b1aa",
                "surface-container-low": "#f9f3ed",
                "on-tertiary-container": "#570086",
                "secondary-container": "#cbfecc",
                "on-primary": "#fff7f7",
                "outline": "#7f7a74",
                "error-container": "#f76a80",
                "on-secondary-fixed": "#25512d",
                "tertiary": "#853bb5",
                "on-primary-fixed": "#51092a",
                "secondary": "#3c6942",
                "primary-container": "#ffa8c4",
                "on-error-container": "#68001f",
                "on-tertiary-fixed-variant": "#620e92",
                "on-tertiary": "#fff6fd",
                "surface-container-highest": "#e8e1da",
                "tertiary-dim": "#782ca8",
                "surface-container-high": "#eee7e0",
                "on-tertiary-fixed": "#370056",
                "on-secondary": "#e9ffe6",
                "secondary-fixed": "#cbfecc",
                "on-primary-fixed-variant": "#772948",
                "error-dim": "#770326",
                "surface-tint": "#974362",
                "surface-container-lowest": "#ffffff",
                "on-surface": "#35322d",
                "tertiary-fixed-dim": "#d48fff",
                "background": "#fef8f3",
                "on-secondary-container": "#38643e"
              },
              fontFamily: {
                "headline": ["Plus Jakarta Sans"],
                "body": ["Be Vietnam Pro"],
                "label": ["Plus Jakarta Sans"]
              },
              borderRadius: {"DEFAULT": "1rem", "lg": "2rem", "xl": "3rem", "full": "9999px"},
            },
          },
        }
    </script>
    <style>
        body { font-family: 'Be Vietnam Pro', sans-serif; }
        .headline { font-family: 'Plus Jakarta Sans', sans-serif; }
        .material-symbols-outlined {
          font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
        }
        .custom-scrollbar::-webkit-scrollbar { width: 4px; }
        .custom-scrollbar::-webkit-scrollbar-track { background: transparent; }
        .custom-scrollbar::-webkit-scrollbar-thumb { background: #e8e1da; border-radius: 10px; }
    </style>
</head>
<body class="bg-surface text-on-surface selection:bg-primary-container selection:text-on-primary-container">

<aside class="fixed left-0 top-0 h-full flex flex-col p-6 h-screen w-72 border-r-0 bg-[#fef8f3] dark:bg-stone-950 shadow-[32px_0_64px_-20px_rgba(53,50,45,0.06)] z-50">
    <div class="text-2xl font-black text-[#974362] dark:text-[#ffa8c4] mb-8 headline">Chip3Chip Staff</div>
    <div class="flex flex-col gap-2 mb-auto">
        <a class="flex items-center gap-4 p-4 rounded-xl text-[#974362] dark:text-[#ffa8c4] font-bold border-r-4 border-[#974362] bg-[#f9f3ed] dark:bg-stone-900 transition-all duration-300 font-['Plus_Jakarta_Sans'] tracking-tight text-sm font-medium scale-105" href="${pageContext.request.contextPath}/staff/dashboard">
            <span class="material-symbols-outlined">dashboard</span> Dashboard
        </a>
        <a class="flex items-center gap-4 p-4 rounded-xl text-[#35322d]/60 dark:text-stone-400 hover:bg-[#f9f3ed] dark:hover:bg-stone-800 transition-all duration-300 font-['Plus_Jakarta_Sans'] tracking-tight text-sm font-medium" href="${pageContext.request.contextPath}/staff/orders">
            <span class="material-symbols-outlined">receipt_long</span> Orders
        </a>
        <a class="flex items-center gap-4 p-4 rounded-xl text-[#35322d]/60 dark:text-stone-400 hover:bg-[#f9f3ed] dark:hover:bg-stone-800 transition-all duration-300 font-['Plus_Jakarta_Sans'] tracking-tight text-sm font-medium" href="${pageContext.request.contextPath}/staff/menu">
            <span class="material-symbols-outlined">restaurant_menu</span> Menu
        </a>
        <a class="flex items-center gap-4 p-4 rounded-xl text-[#35322d]/60 dark:text-stone-400 hover:bg-[#f9f3ed] dark:hover:bg-stone-800 transition-all duration-300 font-['Plus_Jakarta_Sans'] tracking-tight text-sm font-medium" href="${pageContext.request.contextPath}/staff/chat">
            <span class="material-symbols-outlined">chat_bubble</span> Chat
        </a>
    </div>

    <div class="mt-auto pt-6 border-t border-outline-variant/10">
        <div class="flex items-center gap-3 mb-4">
            <div class="w-10 h-10 rounded-full bg-primary-container overflow-hidden">
                <img alt="Staff Profile Picture" class="w-full h-full object-cover"
                     src="${not empty sessionScope.staffAvatar ? sessionScope.staffAvatar : 'https://ui-avatars.com/api/?name=Staff&background=eee7e0&color=625e59'}">
            </div>
            <div>
                <p class="text-sm font-bold headline leading-tight">${not empty sessionScope.displayName ? sessionScope.displayName : 'Staff'}</p>
                <p class="text-[10px] text-on-surface-variant font-medium">Shift Active: 02:45</p>
            </div>
        </div>
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="w-full py-3 bg-primary text-on-primary rounded-full font-bold text-sm tracking-tight hover:scale-105 transition-transform duration-200">
                Clock Out
            </button>
        </form>
    </div>
</aside>

<header class="flex justify-between items-center ml-72 px-8 w-[calc(100%-18rem)] h-16 bg-[#fef8f3]/80 dark:bg-stone-950/80 backdrop-blur-md fixed top-0 z-40">
    <div class="font-['Plus_Jakarta_Sans'] uppercase tracking-widest text-xs text-[#974362] dark:text-[#ffa8c4]">Shift Overview</div>
    <div class="flex items-center gap-6">
        <div class="flex items-center bg-surface-container-highest px-4 py-2 rounded-full min-w-[240px]">
            <span class="material-symbols-outlined text-outline text-sm">search</span>
            <input class="bg-transparent border-none focus:ring-0 text-sm w-full placeholder:text-outline-variant" placeholder="Search orders..." type="text">
        </div>
        <div class="flex items-center gap-4">
            <button class="material-symbols-outlined text-[#35322d] hover:opacity-70 active:scale-95 transition-all">notifications</button>
            <button class="material-symbols-outlined text-[#35322d] hover:opacity-70 active:scale-95 transition-all">settings</button>
        </div>
    </div>
</header>

<main class="ml-72 pt-24 px-8 pb-12 min-h-screen">
    <div class="grid grid-cols-1 md:grid-cols-12 gap-8 mb-12">
        <div class="md:col-span-8 relative overflow-hidden bg-primary-container/20 rounded-lg p-10 flex flex-col justify-center">
            <div class="absolute -right-12 -top-12 w-64 h-64 bg-primary/10 rounded-full blur-3xl"></div>
            <h1 class="headline text-4xl md:text-5xl font-extrabold tracking-tight mb-4 text-primary">04:30:12</h1>
            <p class="text-lg font-medium text-on-primary-fixed-variant mb-6">Current Shift Duration</p>
            <div class="flex gap-4">
                <span class="px-4 py-2 bg-white/50 backdrop-blur-sm rounded-full text-xs font-bold headline text-primary uppercase tracking-wider italic">Break status: Available</span>
                <span class="px-4 py-2 bg-white/50 backdrop-blur-sm rounded-full text-xs font-bold headline text-primary uppercase tracking-wider">Station: Counter A</span>
            </div>
        </div>
        <div class="md:col-span-4 bg-surface-container-low rounded-lg p-8 flex flex-col justify-between border border-outline-variant/10">
            <div>
                <span class="material-symbols-outlined text-tertiary mb-4">format_quote</span>
                <p class="headline text-xl font-semibold leading-snug text-on-surface">"Every cookie is a small package of happiness you're delivering today."</p>
            </div>
            <p class="text-sm font-medium text-tertiary mt-4">— Quote of the Day</p>
        </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mb-12">
        <div class="bg-surface-container-lowest rounded-lg p-6 shadow-[0_32px_64px_-20px_rgba(53,50,45,0.06)] flex items-center justify-between">
            <div>
                <p class="text-xs font-bold uppercase tracking-widest text-outline-variant mb-1 headline">Handled Today</p>
                <p class="text-3xl font-black headline text-on-surface">${not empty ordersCount ? ordersCount : '0'}</p>
            </div>
            <div class="w-14 h-14 rounded-2xl bg-secondary-container flex items-center justify-center">
                <span class="material-symbols-outlined text-on-secondary-container text-3xl">shopping_basket</span>
            </div>
        </div>

        <div class="bg-surface-container-lowest rounded-lg p-6 shadow-[0_32px_64px_-20px_rgba(53,50,45,0.06)] flex flex-col justify-between">
            <div class="flex justify-between items-start mb-4">
                <div>
                    <p class="text-xs font-bold uppercase tracking-widest text-outline-variant mb-1 headline">Sales Value</p>
                    <p class="text-2xl font-black headline text-on-surface">${not empty salesValue ? salesValue : '-'}</p>
                </div>
                <span class="text-xs font-bold text-secondary flex items-center gap-1 bg-secondary-container px-2 py-1 rounded-full">
                    <span class="material-symbols-outlined text-sm">trending_up</span> ${not empty salesGrowth ? salesGrowth : '-'}
                </span>
            </div>
            <div class="w-full h-10 overflow-hidden">
                <svg class="w-full h-full" viewBox="0 0 200 40">
                    <path d="M0,35 Q20,25 40,30 T80,15 T120,20 T160,5 T200,10" fill="none" stroke="#974362" stroke-linecap="round" stroke-width="3"></path>
                    <path d="M0,35 Q20,25 40,30 T80,15 T120,20 T160,5 T200,10 V40 H0 Z" fill="url(#grad1)" opacity="0.2"></path>
                    <defs>
                        <linearGradient id="grad1" x1="0%" x2="0%" y1="0%" y2="100%">
                            <stop offset="0%" style="stop-color:#974362;stop-opacity:1"></stop>
                            <stop offset="100%" style="stop-color:#974362;stop-opacity:0"></stop>
                        </linearGradient>
                    </defs>
                </svg>
            </div>
        </div>

        <div class="bg-surface-container-lowest rounded-lg p-6 shadow-[0_32px_64px_-20px_rgba(53,50,45,0.06)] flex items-center justify-between">
            <div>
                <p class="text-xs font-bold uppercase tracking-widest text-outline-variant mb-1 headline">Efficiency</p>
                <p class="text-3xl font-black headline text-on-surface">${not empty efficiency ? efficiency : '-'}</p>
            </div>
            <div class="w-14 h-14 rounded-2xl bg-tertiary-container flex items-center justify-center">
                <span class="material-symbols-outlined text-on-tertiary-container text-3xl">workspace_premium</span>
            </div>
        </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-12 gap-8">
        <div class="lg:col-span-5 bg-surface-container-low rounded-lg p-8">
            <h3 class="headline text-xl font-bold mb-6 flex items-center gap-2">
                <span class="w-2 h-2 bg-error rounded-full animate-pulse"></span> Live Feed
            </h3>
            <div class="space-y-6 max-h-[400px] overflow-y-auto custom-scrollbar pr-4">
                <c:choose>
                    <c:when test="${not empty feeds}">
                        <c:forEach items="${feeds}" var="f">
                            <div class="flex gap-4 items-start group">
                                <div class="w-12 h-12 rounded-full flex-shrink-0 bg-white shadow-sm flex items-center justify-center border border-outline-variant/10">
                                    <span class="material-symbols-outlined text-primary">${f.icon}</span>
                                </div>
                                <div>
                                    <p class="text-sm font-medium text-on-surface">${f.content}</p>
                                    <p class="text-[11px] text-outline-variant mt-1">${f.time} • ${f.location}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="flex gap-4 items-start group">
                            <div class="w-12 h-12 rounded-full flex-shrink-0 bg-white shadow-sm flex items-center justify-center border border-outline-variant/10">
                                <span class="material-symbols-outlined text-primary">info</span>
                            </div>
                            <div>
                                <p class="text-sm font-medium text-on-surface">No live feed data available.</p>
                                <p class="text-[11px] text-outline-variant mt-1">Data will appear after syncing from database.</p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="lg:col-span-7">
            <div class="flex justify-between items-center mb-6">
                <h3 class="headline text-xl font-bold">Your Active Orders</h3>
                <button class="text-xs font-bold text-primary headline hover:underline">View All History</button>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div class="bg-surface-container-lowest p-5 rounded-lg border border-outline-variant/10">
                    <p class="text-sm text-on-surface-variant">No active order data loaded from database.</p>
                </div>
            </div>
        </div>
    </div>
</main>

<button class="fixed bottom-10 right-10 w-16 h-16 bg-primary text-on-primary rounded-full shadow-2xl flex items-center justify-center hover:scale-110 transition-transform duration-300 z-50">
    <span class="material-symbols-outlined text-3xl">add</span>
</button>

</body>
</html>