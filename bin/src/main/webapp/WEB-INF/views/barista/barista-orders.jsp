<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html class="light" lang="vi">
<head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <title>Fulfillment Station | Chip3Chip</title>

    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=Be+Vietnam+Pro:wght@400;500;600&family=Pinyon+Script&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet" />

    <script id="tailwind-config">
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        "primary": "#974362",
                        "tertiary": "#853bb5",
                        "surface": "#fef8f3",
                        "surface-container-low": "#f9f3ed",
                        "surface-container-lowest": "#ffffff",
                        "on-surface": "#35322d",
                        "secondary": "#3c6942",
                        "secondary-container": "#cbfecc"
                    },
                    borderRadius: {
                        "DEFAULT": "1rem",
                        "lg": "2rem",
                        "full": "9999px"
                    }
                }
            }
        }
    </script>
    <style>
        .material-symbols-outlined { font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24; }
        .editorial-shadow { box-shadow: 0 8px 32px rgba(53, 50, 45, 0.06); }
        .glass-header { background: rgba(254, 248, 243, 0.8); backdrop-filter: blur(16px); }
    </style>
</head>

<body class="bg-surface font-body text-on-surface antialiased">

    <header class="md:hidden glass-header fixed top-0 left-0 w-full flex justify-between items-center px-6 py-4 z-50 editorial-shadow">
        <h1 class="font-headline font-black text-xl tracking-tight">Chip3Chip</h1>
        <span class="material-symbols-outlined text-primary">account_circle</span>
    </header>

    <%@ include file="/WEB-INF/views/barista/layout/sidebar.jsp" %>

    <main class="md:ml-64 min-h-screen pt-20 md:pt-0 pb-24 md:pb-12 px-6 md:px-12 bg-surface">

        <section class="flex flex-col md:flex-row md:items-end justify-between pt-12 pb-8 gap-6 border-b border-outline-variant/10 mb-8">
            <div class="space-y-1">
                <div class="flex items-center gap-2">
                    <span class="w-2 h-2 rounded-full bg-green-500 animate-pulse"></span>
                    <p class="text-green-600 font-headline font-bold text-xs tracking-widest uppercase">Station Online</p>
                </div>
                <h2 class="text-4xl md:text-5xl font-headline font-extrabold text-on-background tracking-tighter">Fulfillment Station</h2>
            </div>

        </section>

        <div class="space-y-6">
            <div class="flex items-center justify-between px-2">
                <h3 class="font-headline font-bold text-lg flex items-center gap-2">
                    <span class="material-symbols-outlined text-tertiary">pending_actions</span>
                    Active Queue
                </h3>
                <span class="text-xs font-bold bg-tertiary/10 text-tertiary px-5 py-2 rounded-full border border-tertiary/20">
                    <c:out value="${pendingOrders.size()}" /> Orders Remaining
                </span>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-8">

                <c:forEach var="order" items="${pendingOrders}">
                    <div class="bg-surface-container-lowest rounded-[2rem] p-8 editorial-shadow border border-outline-variant/10 hover:border-tertiary/30 transition-all group relative overflow-hidden">
                        <div class="flex justify-between items-start mb-6">
                            <div>
                                <%-- Truy cập orderID qua Key của Map (Vượt lỗi thiếu Getter) --%>
                                <p class="text-[10px] font-black text-on-surface/30 uppercase tracking-widest">Order #<c:out value="${order.orderID}" /></p>
                                <h4 class="text-xl font-headline font-bold text-on-surface mt-1">
                                    <c:out value="${order.customer != null ? order.customer.name : 'Guest'}" />
                                </h4>
                            </div>
                            <span class="text-[10px] font-bold bg-secondary-container text-on-secondary-container px-3 py-1 rounded-full uppercase">Paid</span>
                        </div>

                        <div class="space-y-4 mb-8">
                            <c:forEach var="item" items="${order.items}">
                                <div class="flex items-start gap-4 p-3 rounded-2xl bg-surface-container-low/50 border border-outline-variant/5">
                                    <div class="w-10 h-10 rounded-2xl bg-white flex items-center justify-center shrink-0 font-bold text-tertiary shadow-sm">
                                        ${item.quantity}x
                                    </div>
                                    <div>
                                        <p class="text-sm font-bold text-on-surface">${item.beverage.name}</p>
                                        <p class="text-[11px] text-on-surface-variant opacity-70 italic mt-0.5">
                                            Size ${item.size} • ${item.sugar} Sugar • ${item.ice} Ice
                                        </p>
                                        <div class="mt-2 flex flex-wrap gap-1">
                                            <c:forEach var="topping" items="${item.toppings}">
                                                <span class="text-[9px] font-bold bg-tertiary/5 text-tertiary px-2 py-0.5 rounded-md border border-tertiary/10 uppercase tracking-tighter">
                                                    + ${topping.name}
                                                </span>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <form action="${pageContext.request.contextPath}/barista/orders" method="POST">
                            <input type="hidden" name="orderId" value="${order.orderID}">
                            <input type="hidden" name="action" value="COMPLETE">
                            <button type="submit" class="w-full py-4 bg-tertiary text-white font-headline font-bold text-sm rounded-full shadow-lg shadow-tertiary/20 hover:bg-tertiary-dim active:scale-95 transition-all flex items-center justify-center gap-2">
                                <span class="material-symbols-outlined text-[20px]">check_circle</span>
                                Mark Done
                            </button>
                        </form>
                    </div>
                </c:forEach>

                <c:if test="${empty pendingOrders}">
                    <div class="col-span-full py-24 flex flex-col items-center justify-center text-center opacity-40 bg-white/50 rounded-[2rem] border-2 border-dashed border-outline-variant/20">
                        <span class="material-symbols-outlined text-6xl mb-4 text-on-surface/40">coffee_maker</span>
						<h3 class="font-headline font-bold text-xl">Queue is empty</h3>
						<p class="text-sm">All beverages have been successfully
							prepared.</p>
					</div>
                </c:if>

            </div>
        </div>

        <div class="mt-20 relative h-64 rounded-lg overflow-hidden md:block hidden grayscale-[30%] hover:grayscale-0 transition-all duration-1000">
            <img class="w-full h-full object-cover" src="https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?q=80&w=2000&auto=format&fit=crop" />
            <div class="absolute inset-0 bg-gradient-to-t from-surface via-surface/40 to-transparent"></div>
            <div class="absolute bottom-8 left-8">
                <span class="font-['Pinyon_Script'] text-5xl text-tertiary">Handcrafted with love</span>
                <p class="font-headline font-bold text-on-surface/40 tracking-widest uppercase text-xs mt-2">The Chip3Chip Atelier Standard</p>
            </div>
        </div>
    </main>

    <nav class="md:hidden fixed bottom-0 left-0 w-full bg-white/80 backdrop-blur-xl border-t border-outline-variant/10 flex justify-around items-center py-4 px-6 z-50">
        <button class="flex flex-col items-center gap-1 text-tertiary font-bold">
            <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">pending_actions</span>
            <span class="text-[10px]">Queue</span>
        </button>
        <button class="flex flex-col items-center gap-1 text-on-surface/60">
            <span class="material-symbols-outlined">history</span>
            <span class="text-[10px]">History</span>
        </button>
    </nav>
</body>
</html>