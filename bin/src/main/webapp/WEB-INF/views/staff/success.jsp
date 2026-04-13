<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Order Confirmation | Chip3Chip</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap"
                rel="stylesheet" />
            <link
                href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap"
                rel="stylesheet" />
            <style>
                body {
                    font-family: 'Plus Jakarta Sans', sans-serif;
                }
            </style>
        </head>

        <body class="bg-[#faf7f2] flex items-center justify-center min-h-screen text-[#35322d] p-6">

            <div
                class="bg-white p-10 rounded-[2.5rem] shadow-[0_8px_32px_rgba(53,50,45,0.06)] border border-[#f3ede6] max-w-lg w-full relative overflow-hidden">

                <div class="absolute -right-10 -top-10 w-40 h-40 bg-[#974362]/5 blur-3xl rounded-full"></div>

                <div class="text-center relative z-10">
                    <div
                        class="mx-auto w-20 h-20 bg-green-50 rounded-full flex items-center justify-center mb-6 border-4 border-white shadow-sm">
                        <span class="material-symbols-outlined text-green-500 text-4xl"
                            style="font-variation-settings: 'FILL' 1;">check_circle</span>
                    </div>
                    <h2 class="text-3xl font-extrabold text-[#35322d] mb-2 tracking-tight">Payment Successful!</h2>
                    <p class="text-gray-500 font-medium mb-6">Your order has been recorded successfully.</p>

                    <%-- Badges trang thai --%>
                        <div class="flex justify-center gap-3 mb-6">
                            <span
                                class="inline-flex items-center gap-1.5 bg-amber-100 text-amber-700 text-xs font-bold px-3 py-1.5 rounded-full">
                                <span class="material-symbols-outlined text-sm">pending</span>
                                Order: ${orderState}
                            </span>
                            <span
                                class="inline-flex items-center gap-1.5 bg-green-100 text-green-700 text-xs font-bold px-3 py-1.5 rounded-full">
                                <span class="material-symbols-outlined text-sm">payments</span>
                                Payment: ${paymentStatus}
                            </span>
                        </div>
                </div>

                <div class="bg-[#faf7f2] rounded-3xl p-6 mb-8 relative z-10 border border-white">
                    <div class="space-y-4 text-sm font-medium">
                        <div class="flex justify-between items-center pb-4 border-b border-gray-200/50">
                            <span class="text-gray-500 flex items-center gap-2">
                                <span class="material-symbols-outlined text-lg">account_balance_wallet</span>
                                Payment Method
                            </span>
                            <span
                                class="font-bold text-[#974362] uppercase tracking-wider bg-[#974362]/10 px-3 py-1 rounded-lg">${paymentMethod}</span>
                        </div>

                        <div class="flex justify-between items-center pb-4 border-b border-gray-200/50">
                            <span class="text-gray-500 flex items-center gap-2">
                                <span class="material-symbols-outlined text-lg">sell</span>
                                Promotion
                            </span>
                            <span class="font-bold uppercase">${promoType != null ? promoType : "None"}</span>
                        </div>

                        <div class="flex justify-between items-center pt-2">
                            <span class="text-gray-500">Subtotal:</span>
                            <span class="font-semibold">
                                <fmt:formatNumber value="${subtotal}" type="currency" currencySymbol="đ" />
                            </span>
                        </div>

                        <div class="flex justify-between items-center text-green-600">
                            <span>Discount:</span>
                            <span class="font-bold">-
                                <fmt:formatNumber value="${discount}" type="currency" currencySymbol="đ" />
                            </span>
                        </div>

                        <div class="flex justify-between items-center">
                            <span class="text-gray-500">Tax (10%):</span>
                            <span class="font-semibold">
                                <fmt:formatNumber value="${tax}" type="currency" currencySymbol="đ" />
                            </span>
                        </div>
                    </div>
                </div>

                <div class="flex justify-between items-end mb-8 px-2 relative z-10">
                    <span class="text-lg font-bold text-gray-500">Total</span>
                    <span class="text-4xl font-extrabold text-[#974362] tracking-tighter">
                        <fmt:formatNumber value="${finalTotal}" type="currency" currencySymbol="đ" />
                    </span>
                </div>

                <div class="relative z-10">
                    <a href="${pageContext.request.contextPath}/payment"
                        class="flex justify-center items-center gap-2 w-full bg-[#974362] text-white font-bold py-5 rounded-2xl hover:bg-[#8e2b52] hover:shadow-lg hover:-translate-y-1 transition-all duration-300">
                        <span class="material-symbols-outlined">verified</span>
                        Confirmed Successfully
                    </a>
                </div>
            </div>

        </body>

        </html>