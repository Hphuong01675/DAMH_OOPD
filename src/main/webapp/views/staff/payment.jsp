<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta content="width=device-width, initial-scale=1.0" name="viewport" />
                <title>Checkout - Chip3Chip</title>

                <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
                <link
                    href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=Be+Vietnam+Pro:wght@300;400;500;600;700;900&display=swap"
                    rel="stylesheet" />
                <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1"
                    rel="stylesheet" />

                <script id="tailwind-config">
                    tailwind.config = {
                        darkMode: "class",
                        theme: {
                            extend: {
                                colors: {
                                    "primary": "#974362",
                                    "primary-container": "#ffa8c4",
                                    "on-primary-container": "#6c203f",
                                    "background": "#fef8f3",
                                    "surface": "#fef8f3",
                                    "on-surface": "#35322d",
                                    "on-surface-variant": "#625e59",
                                    "outline-variant": "#b7b1aa",
                                    "secondary-container": "#cbfecc",
                                    "on-secondary-container": "#38643e",
                                    "tertiary-container": "#dca2ff",
                                    "on-tertiary-container": "#570086",
                                    "surface-container-low": "#f9f3ed",
                                    "surface-container-high": "#eee7e0",
                                    "surface-container-lowest": "#ffffff",
                                }
                            }
                        }
                    }
                </script>

                <style>
                    .material-symbols-outlined {
                        font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
                    }

                    .editorial-shadow {
                        box-shadow: 0 8px 32px rgba(53, 50, 45, 0.06);
                    }

                    .silk-gradient {
                        background: linear-gradient(135deg, #974362 0%, #ffa8c4 100%);
                    }

                    body {
                        font-family: 'Be Vietnam Pro', sans-serif;
                        background-color: #fef8f3;
                        color: #35322d;
                    }

                    h1,
                    h2,
                    h3 {
                        font-family: 'Plus Jakarta Sans', sans-serif;
                    }

                    .modal-overlay {
                        display: none;
                        position: fixed;
                        inset: 0;
                        background: rgba(0, 0, 0, 0.5);
                        z-index: 100;
                        align-items: center;
                        justify-content: center;
                        backdrop-filter: blur(4px);
                    }

                    .modal-active {
                        display: flex;
                    }
                </style>
            </head>

            <body class="bg-surface text-on-surface">

                <div id="vnpay-modal" class="modal-overlay">
                    <div class="bg-white p-8 rounded-3xl shadow-2xl max-w-sm w-full text-center">
                        <h3 class="text-xl font-bold mb-4 text-primary">Scan VNPAY QR</h3>
                        <div class="bg-gray-100 p-4 rounded-2xl mb-4 inline-block">
                            <img src="https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=CHIP3CHIP_ORDER_${orderId}"
                                alt="QR Code" class="mx-auto">
                        </div>
                        <p class="text-sm text-gray-500 mb-6 italic">The system will automatically confirm after
                            scanning...</p>
                        <button onclick="closeModal('vnpay-modal')"
                            class="text-on-surface-variant font-bold text-sm hover:underline">Cancel
                            Transaction</button>
                    </div>
                </div>

                <div id="card-modal" class="modal-overlay">
                    <div class="bg-white p-8 rounded-3xl shadow-2xl max-w-md w-full">
                        <h3 class="text-xl font-bold mb-6 text-primary">Card Information</h3>
                        <div class="space-y-4">
                            <div>
                                <label class="text-xs font-bold text-gray-400 block mb-1 uppercase">Card Number</label>
                                <input type="text" placeholder="**** **** **** ****"
                                    class="w-full border-gray-200 rounded-xl p-3 bg-gray-50 focus:ring-primary focus:border-primary">
                            </div>
                            <div class="grid grid-cols-2 gap-4">
                                <div>
                                    <label class="text-xs font-bold text-gray-400 block mb-1 uppercase">Expiry
                                        Date</label>
                                    <input type="text" placeholder="MM/YY"
                                        class="w-full border-gray-200 rounded-xl p-3 bg-gray-50">
                                </div>
                                <div>
                                    <label class="text-xs font-bold text-gray-400 block mb-1 uppercase">CVV</label>
                                    <input type="password" placeholder="***"
                                        class="w-full border-gray-200 rounded-xl p-3 bg-gray-50">
                                </div>
                            </div>
                            <button onclick="processFakePayment(this)"
                                class="w-full silk-gradient text-white py-4 rounded-xl font-bold mt-4 shadow-lg shadow-primary/20 hover:opacity-90 transition-all">
                                Confirm & Pay
                            </button>
                        </div>
                    </div>
                </div>

                <header
                    class="fixed top-0 right-0 left-24 z-50 bg-surface-container-high text-on-surface flex justify-between items-center px-8 h-16 shadow-md border-b border-outline-variant/20">
                    <div class="flex items-center gap-8">
                        <div class="flex items-center gap-2 text-2xl font-extrabold tracking-tight text-primary">
                            <span class="material-symbols-outlined text-primary">bubble_chart</span>
                            Chip3Chip
                        </div>
                        <span
                            class="text-on-surface-variant font-medium text-sm border-l border-outline-variant/30 pl-8 hidden sm:inline">
                            Staff: ${sessionScope.staffName != null ? sessionScope.staffName : "Station 01"}
                        </span>
                    </div>

                    <div class="flex items-center gap-6">
                        <div class="flex flex-col items-end border-r border-outline-variant/30 pr-6">
                            <span
                                class="text-xs font-bold leading-none text-on-surface-variant uppercase tracking-tighter"
                                id="clock">--:--:--</span>
                        </div>
                        <div
                            class="w-10 h-10 rounded-2xl bg-white flex items-center justify-center cursor-pointer border border-outline-variant/20 hover:bg-primary/5 transition-all">
                            <span class="material-symbols-outlined text-lg text-primary">person</span>
                        </div>
                    </div>
                </header>

                <div class="flex min-h-screen">
                    <aside
                        class="fixed left-0 top-0 h-screen w-24 bg-white flex flex-col items-center py-6 border-r border-outline-variant/10 z-[60] shadow-lg shadow-black/5">
                        <div
                            class="h-16 absolute top-0 flex items-center justify-center w-full border-b border-outline-variant/10 bg-surface-container-high">
                            <span class="text-primary font-black text-xs tracking-tighter uppercase">Atelier</span>
                        </div>

                        <nav class="flex flex-col gap-6 w-full items-center mt-16 pt-4">
                            <a href="${pageContext.request.contextPath}/admin/dashboard"
                                class="flex flex-col items-center gap-1 group cursor-pointer text-on-surface-variant hover:text-primary transition-colors">
                                <div class="p-3 rounded-xl group-hover:bg-primary/5 transition-colors">
                                    <span class="material-symbols-outlined text-2xl">dashboard</span>
                                </div>
                                <span class="text-[10px] font-bold">Dashboard</span>
                            </a>

                            <a href="${pageContext.request.contextPath}/admin/products"
                                class="flex flex-col items-center gap-1 group cursor-pointer text-on-surface-variant hover:text-primary transition-colors">
                                <div class="p-3 rounded-xl group-hover:bg-primary/5 transition-colors">
                                    <span class="material-symbols-outlined text-2xl">restaurant_menu</span>
                                </div>
                                <span class="text-[10px] font-bold">Menu</span>
                            </a>

                            <a href="#"
                                class="flex flex-col items-center gap-1 group cursor-pointer text-on-surface-variant hover:text-primary transition-colors">
                                <div class="text-[#9e4368] font-bold">
                                    <span class="material-symbols-outlined text-2xl">receipt_long</span>
                                </div>
                                <span class="text-[10px] font-bold">Orders</span>
                            </a>

                            <a href="#"
                                class="flex flex-col items-center gap-1 group cursor-pointer text-on-surface-variant hover:text-primary transition-colors">
                                <div class="p-3 rounded-xl group-hover:bg-primary/5 transition-colors">
                                    <span class="material-symbols-outlined text-2xl">chat_bubble</span>
                                </div>
                                <span class="text-[10px] font-bold">Chat</span>
                            </a>
                        </nav>
                    </aside>

                    <main
                        class="flex-1 pt-24 pb-12 px-8 ml-24 max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-10">
                        <form id="checkout-form" action="${pageContext.request.contextPath}/staff/order/confirm"
                            method="POST" class="lg:col-span-7 space-y-10">
                            <section
                                class="bg-surface-container-low p-8 rounded-2xl editorial-shadow relative overflow-hidden">
                                <div class="flex items-center gap-6 relative z-10">
                                    <div
                                        class="w-24 h-24 rounded-2xl bg-primary-container flex items-center justify-center text-on-primary-container text-4xl font-bold">
                                        ${customer != null ? customer.name.charAt(0) : "G"}
                                    </div>
                                    <div>
                                        <h2 class="text-2xl font-bold tracking-tight text-primary">
                                            ${customer != null ? customer.name : "Walk-in Guest"}
                                        </h2>
                                        <p class="text-on-surface-variant font-medium">${customer != null ?
                                            customer.phoneNumber : "No phone information"}</p>
                                        <c:if test="${customer != null}">
                                            <div
                                                class="mt-3 inline-flex items-center gap-2 bg-primary-container px-4 py-1.5 rounded-full">
                                                <span class="material-symbols-outlined text-xs"
                                                    style="font-variation-settings: 'FILL' 1;">stars</span>
                                                <span class="text-sm font-bold text-on-primary-container">Reward Points:
                                                    ${customer.points} pts</span>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </section>

                            <section class="space-y-6">
                                <h3
                                    class="text-xl font-bold tracking-tight text-on-surface ml-2 italic underline decoration-primary/30">
                                    Applied Promotions</h3>
                                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                    <c:forEach var="promo" items="${promotions}">
                                        <label
                                            class="group relative bg-surface-container-lowest p-6 rounded-2xl editorial-shadow border border-outline-variant/15 hover:border-primary/50 hover:scale-[1.02] transition-all cursor-pointer">
                                            <div class="flex justify-between items-start mb-4">
                                                <div class="bg-primary-container p-2 rounded-xl">
                                                    <span
                                                        class="material-symbols-outlined text-on-primary-container">confirmation_number</span>
                                                </div>
                                                <input type="radio" name="promoStrategy" value="${promo.id}"
                                                    class="w-5 h-5 text-primary focus:ring-primary">
                                            </div>
                                            <h4 class="font-bold text-lg mb-1">${promo.title}</h4>
                                            <p
                                                class="text-xs text-on-surface-variant uppercase tracking-widest font-bold opacity-60">
                                                Type: ${promo.strategyName}</p>
                                        </label>
                                    </c:forEach>
                                    <c:if test="${empty promotions}">
                                        <div
                                            class="col-span-full py-8 border-2 border-dashed border-outline-variant/20 rounded-2xl text-center text-on-surface-variant">
                                            No available promotions
                                        </div>
                                    </c:if>
                                </div>
                            </section>
                        </form>

                        <div class="lg:col-span-5">
                            <div class="bg-surface-container-high rounded-3xl editorial-shadow p-8 sticky top-28">
                                <h3 class="text-2xl font-bold tracking-tight mb-8 flex justify-between items-center">
                                    Order Details
                                    <span class="text-xs font-normal text-on-surface-variant">#ID-${orderId}</span>
                                </h3>

                                <div class="space-y-6 mb-10 max-h-[35vh] overflow-y-auto pr-2 custom-scrollbar">
                                    <c:forEach var="item" items="${orderItems}">
                                        <div class="flex items-center gap-4">
                                            <div
                                                class="w-14 h-14 rounded-xl bg-white flex items-center justify-center font-black text-[10px] text-primary border border-primary/10">
                                                TEA</div>
                                            <div class="flex-1">
                                                <p class="font-bold text-on-surface leading-tight">${item.productName}
                                                </p>
                                                <p class="text-sm text-on-surface-variant">
                                                    Qty: ${item.quantity} &times;
                                                    <fmt:formatNumber value="${item.price}" type="number" />đ
                                                </p>
                                            </div>
                                            <p class="font-bold text-on-surface">
                                                <fmt:formatNumber value="${item.quantity * item.price}" type="number" />
                                                đ
                                            </p>
                                        </div>
                                    </c:forEach>
                                </div>

                                <div class="space-y-4 pt-6 border-t border-outline-variant/30">
                                    <div class="flex justify-between items-center text-on-surface-variant">
                                        <span class="font-medium">Subtotal</span>
                                        <span class="font-bold">
                                            <fmt:formatNumber value="${subtotal}" type="number" />đ
                                        </span>
                                    </div>
                                    <div class="flex justify-between items-center text-primary">
                                        <span class="font-medium italic">Discount</span>
                                        <span class="font-bold">-${discountAmount != null ? discountAmount : 0}đ</span>
                                    </div>
                                    <div class="flex justify-between items-center pt-6 mt-2">
                                        <span class="text-xl font-bold text-on-surface">Total</span>
                                        <span class="text-3xl font-black text-primary tracking-tighter">
                                            <fmt:formatNumber
                                                value="${subtotal - (discountAmount != null ? discountAmount : 0)}"
                                                type="number" />đ
                                        </span>
                                    </div>
                                </div>

                                <div class="grid grid-cols-3 gap-3 my-8">
                                    <label class="cursor-pointer group relative">
                                        <input type="radio" name="paymentMethod" value="cash" form="checkout-form"
                                            class="peer hidden" checked>
                                        <div
                                            class="flex flex-col items-center py-4 rounded-2xl bg-white text-on-surface-variant border-2 border-outline-variant/20 peer-checked:bg-primary peer-checked:text-white peer-checked:border-primary transition-all shadow-sm">
                                            <span class="material-symbols-outlined text-2xl">payments</span>
                                            <span class="text-[10px] font-bold mt-1">CASH</span>
                                        </div>
                                    </label>
                                    <label class="cursor-pointer group relative">
                                        <input type="radio" name="paymentMethod" value="vnpay" form="checkout-form"
                                            class="peer hidden">
                                        <div
                                            class="flex flex-col items-center py-4 rounded-2xl bg-white text-on-surface-variant border-2 border-outline-variant/20 peer-checked:bg-primary peer-checked:text-white peer-checked:border-primary transition-all shadow-sm">
                                            <span class="material-symbols-outlined text-2xl">qr_code_2</span>
                                            <span class="text-[10px] font-bold mt-1">VNPAY</span>
                                        </div>
                                    </label>
                                    <label class="cursor-pointer group relative">
                                        <input type="radio" name="paymentMethod" value="card" form="checkout-form"
                                            class="peer hidden">
                                        <div
                                            class="flex flex-col items-center py-4 rounded-2xl bg-white text-on-surface-variant border-2 border-outline-variant/20 peer-checked:bg-primary peer-checked:text-white peer-checked:border-primary transition-all shadow-sm">
                                            <span class="material-symbols-outlined text-2xl">credit_card</span>
                                            <span class="text-[10px] font-bold mt-1">CARD</span>
                                        </div>
                                    </label>
                                </div>

                                <button type="button" onclick="handleCheckout()"
                                    class="w-full silk-gradient text-white font-bold py-5 rounded-2xl hover:scale-[1.02] active:scale-95 transition-all editorial-shadow flex justify-center items-center gap-2">
                                    COMPLETE ORDER
                                    <span class="material-symbols-outlined">chevron_right</span>
                                </button>
                            </div>
                        </div>
                    </main>
                </div>

                <script>
                    function updateClock() {
                        const now = new Date();
                        const clockEl = document.getElementById('clock');
                        if (clockEl) clockEl.innerText = now.toLocaleTimeString('en-US', { hour12: false });
                    }
                    setInterval(updateClock, 1000);
                    updateClock();

                    function handleCheckout() {
                        const methodElement = document.querySelector('input[name="paymentMethod"]:checked');
                        if (!methodElement) return;

                        const method = methodElement.value;
                        const form = document.getElementById('checkout-form');

                        if (method === 'vnpay') {
                            document.getElementById('vnpay-modal').classList.add('modal-active');
                            setTimeout(() => form.submit(), 5000);
                        } else if (method === 'card') {
                            document.getElementById('card-modal').classList.add('modal-active');
                        } else {
                            form.submit();
                        }
                    }

                    function closeModal(id) {
                        document.getElementById(id).classList.remove('modal-active');
                    }

                    function processFakePayment(btn) {
                        btn.innerText = "Processing...";
                        btn.disabled = true;
                        btn.classList.add('opacity-50');
                        setTimeout(() => document.getElementById('checkout-form').submit(), 2000);
                    }
                </script>
            </body>

            </html>