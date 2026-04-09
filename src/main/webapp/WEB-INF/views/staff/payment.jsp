<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <title>Checkout - Chip3Chip</title>
    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700;900&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1" rel="stylesheet" />

    <script id="tailwind-config">
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        "primary": "#6F4E37",
                        "primary-container": "#D2B48C",
                        "on-primary-container": "#3E2723",
                        "background": "#fef8f3",
                        "surface": "#fef8f3",
                        "on-surface": "#35322d"
                    }
                }
            }
        }
    </script>

    <style>
        html, body { height: 100%; margin: 0; background-color: #fef8f3; }
        header { position: fixed !important; top: 0 !important; left: 0 !important; width: 100% !important; z-index: 100 !important; }
        aside { position: fixed !important; top: 0 !important; left: 0 !important; height: 100vh !important; width: 16rem !important; z-index: 50 !important; padding-top: 5rem !important; }
        .flex.min-h-screen { margin-left: 16rem !important; padding-top: 5rem !important; }
        .silk-gradient { background: linear-gradient(135deg, #6F4E37 0%, #A67B5B 100%); }
        body { font-family: 'Be Vietnam Pro', sans-serif; }
        .modal-overlay { display: none; position: fixed; inset: 0; background: rgba(0, 0, 0, 0.5); z-index: 100; align-items: center; justify-content: center; backdrop-filter: blur(4px); }
        .modal-active { display: flex; }
    </style>
</head>

<body class="bg-surface text-on-surface">
    <%@ include file="/WEB-INF/views/staff/layout/header.jsp" %>
    <%@ include file="/WEB-INF/views/staff/layout/sidebar.jsp" %>
    
    <div id="registerModal" class="modal-overlay">
        <div class="bg-white p-8 rounded-3xl shadow-2xl max-w-sm w-full">
            <div class="text-center mb-6">
                <div class="w-16 h-16 bg-primary-container text-primary rounded-full flex items-center justify-center mx-auto mb-4">
                    <span class="material-symbols-outlined text-3xl">person_add</span>
                </div>
                <h3 class="text-xl font-bold text-primary">New Customer</h3>
                <p class="text-sm text-gray-500">This phone number is not in our system. Please enter a name to create a new member.</p>
            </div>
            
            <div class="space-y-4">
                <div>
                    <label class="text-xs font-bold text-gray-400 uppercase ml-1">Customer Name</label>
                    <input type="text" id="newNameInput" placeholder="e.g. John Doe" 
                           class="w-full border-gray-200 rounded-xl focus:ring-primary focus:border-primary px-4 py-3">
                </div>
                <div class="flex gap-3 pt-2">
                    <button onclick="saveNewCustomer()" class="flex-1 silk-gradient text-white py-3 rounded-xl font-bold shadow-lg active:scale-95 transition-all">Save & Select</button>
                    <button onclick="closeModal('registerModal')" class="flex-1 bg-gray-100 text-gray-500 py-3 rounded-xl font-bold hover:bg-gray-200 transition-all">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <div class="flex min-h-screen">
        <main class="flex-1 pt-24 pb-12 px-8 ml-24 max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-10">

            <form id="checkout-form" action="${pageContext.request.contextPath}/staff/order/confirm" method="POST" class="lg:col-span-7 space-y-10">

                <section class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 space-y-6">
                    <h3 class="text-xl font-bold text-primary flex items-center gap-2">
                        <span class="material-symbols-outlined">person_search</span> Find Customer
                    </h3>

                    <div class="relative">
                        <div class="flex gap-2">
                            <input type="text" id="phoneInput" placeholder="Enter phone number..."
                                class="flex-1 border-gray-200 rounded-xl focus:ring-primary focus:border-primary px-4 py-3">
                            <button type="button" onclick="processCustomerLookup()"
                                class="bg-primary text-white px-8 rounded-xl font-bold hover:bg-opacity-90 active:scale-95 transition-all flex items-center gap-2">
                                <span class="material-symbols-outlined">search</span> Search
                            </button>
                        </div>
                        <p id="searchStatus" class="text-xs mt-2 ml-1 font-medium hidden"></p>
                    </div>

                    <hr class="border-gray-50">

                    <div id="customerCard" class="flex items-center gap-6 p-4 bg-background rounded-2xl border border-primary-container hidden">
                        <div id="avatarInit" class="w-20 h-20 rounded-2xl bg-primary-container flex items-center justify-center text-on-primary-container text-3xl font-bold italic"></div>
                        <div class="flex-1">
                            <h2 id="displayCustName" class="text-2xl font-bold text-primary tracking-tight"></h2>
                            <p id="displayCustPhone" class="text-gray-500 font-medium"></p>
                            <div class="mt-2 inline-flex items-center gap-2 bg-green-100 px-4 py-1.5 rounded-full">
                                <span class="material-symbols-outlined text-[16px] text-green-700">stars</span>
                                <span class="text-sm font-bold text-green-700">Loyalty Points: <span id="displayCustPoints">0</span> pts</span>
                            </div>
                        </div>
                    </div>
                    
                    <div id="noCustomerMsg" class="text-center py-6 text-gray-400 italic bg-gray-50 rounded-2xl border border-dashed">
                        No customer selected for this order.
                    </div>

                    <input type="hidden" name="customerId" id="hiddenCustomerId" value="">
                </section>

                <section class="space-y-6">
                    <h3 class="text-xl font-bold text-on-surface ml-2 italic underline decoration-primary/30">Select Voucher</h3>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <label class="group relative bg-white p-6 rounded-2xl border-2 border-transparent peer-checked:border-primary cursor-pointer shadow-sm">
                            <input type="radio" name="promoStrategy" value="NONE" checked onclick="calculateFinalTotal(0)" class="peer hidden">
                            <div class="peer-checked:border-primary border-2 border-gray-100 rounded-2xl p-6 absolute inset-0 pointer-events-none"></div>
                            <h4 class="font-bold text-lg mb-1">No Voucher</h4>
                        </label>

                        <c:forEach var="promo" items="${promotions}">
                            <label class="group relative bg-white p-6 rounded-2xl border border-gray-100 shadow-sm hover:border-primary/50 transition-all cursor-pointer">
                                <div class="flex justify-between items-start mb-4">
                                    <div class="bg-primary-container/30 p-2 rounded-xl text-primary">
                                        <span class="material-symbols-outlined">confirmation_number</span>
                                    </div>
                                    <input type="radio" name="promoStrategy" value="${promo.id}"
                                        data-strategy="${promo.strategyName}"
                                        onclick="handlePromoCheck(this, '${promo.strategyName}')"
                                        class="w-5 h-5 text-primary focus:ring-primary">
                                </div>
                                <h4 class="font-bold text-lg mb-1">${promo.title}</h4>
                                <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Type: ${promo.strategyName}</p>
                            </label>
                        </c:forEach>
                    </div>
                </section>
            </form>

            <div class="lg:col-span-5">
                <div class="bg-white rounded-3xl shadow-xl p-8 sticky top-28 border border-gray-50">
                    <h3 class="text-2xl font-bold mb-8 flex justify-between items-center">
                        Invoice <span class="text-xs font-normal text-gray-400 uppercase">#KOJI-ORDER</span>
                    </h3>

                    <div class="space-y-4 pt-6 border-t mt-6">
                        <div class="flex justify-between text-gray-500 font-medium">
    <span>Subtotal</span> 
    <span class="font-bold text-on-surface">
        <fmt:formatNumber value="${subtotal}" pattern="#,###" />đ
    </span>
</div>
                        <div class="flex justify-between text-red-500 font-medium italic">
                            <span>Discount</span> <span id="discountLabel">- 0đ</span>
                        </div>
                        <div class="flex justify-between items-center pt-8">
    <span class="text-xl font-bold">Grand Total</span> 
    <span class="text-3xl font-black text-primary tracking-tighter" id="finalPriceLabel"> 
        <fmt:formatNumber value="${subtotal}" pattern="#,###" />đ
    </span>
</div>
                    </div>

                    <div class="grid grid-cols-3 gap-3 my-8">
                        <c:forEach var="m" items='${["cash", "vnpay", "card"]}'>
                            <label class="cursor-pointer group"> 
                                <input type="radio" name="paymentMethod" value="${m}" form="checkout-form" class="peer hidden" ${m == 'cash' ? 'checked' : ''}>
                                <div class="flex flex-col items-center py-4 rounded-2xl bg-gray-50 border-2 peer-checked:bg-primary peer-checked:text-white transition-all text-gray-400">
                                    <span class="material-symbols-outlined text-2xl"> ${m == 'cash' ? 'payments' : (m == 'vnpay' ? 'qr_code_2' : 'credit_card')}</span> 
                                    <span class="text-[10px] font-bold mt-1 uppercase">${m}</span>
                                </div>
                            </label>
                        </c:forEach>
                    </div>

                    <button type="button" onclick="handleCheckout()" class="w-full silk-gradient text-white font-bold py-5 rounded-2xl hover:scale-[1.02] active:scale-95 transition-all shadow-lg flex justify-center items-center gap-2">
                        CONFIRM PAYMENT <span class="material-symbols-outlined">chevron_right</span>
                    </button>
                </div>
            </div>
        </main>
    </div>

    <script>
    	const SUB_TOTAL = Number('${subtotal}') || 0;
        let currentCustomer = { id: null, phone: "", points: 0 };

        async function processCustomerLookup() {
            const phone = document.getElementById('phoneInput').value;
            if (!phone) {
                updateSmallStatus("⚠️ Please enter a phone number", "red");
                return;
            }

            try {
                const response = await fetch(`${pageContext.request.contextPath}/api/customer/process?phone=` + phone);
                const data = await response.json();

                if (data.success) {
                    updateSmallStatus("✅ Member found", "green");
                    renderCustomerInfo(data.customer);
                } else {
                    updateSmallStatus("🔍 Preparing to create new customer...", "blue");
                    openRegisterModal();
                }
            } catch (error) {
                updateSmallStatus("❌ Connection error", "red");
            }
        }

        async function saveNewCustomer() {
            const phone = document.getElementById('phoneInput').value;
            const name = document.getElementById('newNameInput').value;

            if (!name) {
                alert("Please enter customer name");
                return;
            }

            try {
                const response = await fetch(`${pageContext.request.contextPath}/api/customer/process?phone=` + phone + "&name=" + encodeURIComponent(name));
                const data = await response.json();

                if (data.success) {
                    closeModal('registerModal');
                    updateSmallStatus("✨ New customer created successfully", "green");
                    renderCustomerInfo(data.customer);
                }
            } catch (error) {
                alert("Error saving customer");
            }
        }

        function renderCustomerInfo(cust) {
            currentCustomer.id = cust.id;
            currentCustomer.phone = cust.phoneNumber;
            currentCustomer.points = cust.loyaltyPoints;

            document.getElementById('displayCustName').innerText = cust.name;
            document.getElementById('displayCustPhone').innerText = cust.phoneNumber;
            document.getElementById('displayCustPoints').innerText = cust.loyaltyPoints;
            document.getElementById('avatarInit').innerText = cust.name.charAt(0);
            document.getElementById('hiddenCustomerId').value = cust.id;

            document.getElementById('customerCard').classList.remove('hidden');
            document.getElementById('noCustomerMsg').classList.add('hidden');
            
            const selectedPromo = document.querySelector('input[name="promoStrategy"]:checked');
            if (selectedPromo) handlePromoCheck(selectedPromo, selectedPromo.getAttribute('data-strategy'));
        }

        function updateSmallStatus(msg, color) {
            const el = document.getElementById('searchStatus');
            el.innerText = msg;
            el.classList.remove('hidden', 'text-red-500', 'text-green-500', 'text-blue-500');
            
            if (color === "red") el.classList.add('text-red-500');
            else if (color === "green") el.classList.add('text-green-500');
            else el.classList.add('text-blue-500');
            
            el.classList.remove('hidden');
        }

        async function handlePromoCheck(radioBtn, strategyName) {
            const promoCode = radioBtn.value;
            const subtotal = SUB_TOTAL;

            if (promoCode === 'NONE') {
                calculateFinalTotal(0);
                return;
            }

            try {
                // Gửi yêu cầu đến API
                const response = await fetch(`${pageContext.request.contextPath}/api/calculate-discount?code=` + promoCode + "&total=" + subtotal);
                const data = await response.json();

                if (data.error) {
                    console.error("Lỗi từ Server:", data.error);
                    calculateFinalTotal(0);
                    return;
                }

                // Ép kiểu số cho giá trị giảm giá nhận được
                const discountAmount = parseFloat(data.discountAmount) || 0;
                
                // Gọi hàm cập nhật hiển thị ngay lập tức
                calculateFinalTotal(discountAmount);

            } catch (error) {
                console.error("Lỗi kết nối API:", error);
                calculateFinalTotal(0);
            }
        }

        function calculateFinalTotal(discount) {
            const currentSubtotal = Number(SUB_TOTAL) || 0;
            
            // Tính toán số tiền giảm (không vượt quá tổng hóa đơn)
            const finalDiscount = Math.min(discount, currentSubtotal);
            const finalTotal = Math.max(currentSubtotal - finalDiscount, 0);

            // Cập nhật lên giao diện
            const labelDiscount = document.getElementById('discountLabel');
            const labelTotal = document.getElementById('finalPriceLabel');

            if (labelDiscount) {
                labelDiscount.innerText = "- " + finalDiscount.toLocaleString('vi-VN') + "đ";
            }
            if (labelTotal) {
                labelTotal.innerText = finalTotal.toLocaleString('vi-VN') + "đ";
            }
        }

        function resetPromo() {
            const noneRadio = document.querySelector('input[name="promoStrategy"][value="NONE"]');
            if (noneRadio) noneRadio.checked = true;
            calculateFinalTotal(0);
        }

        function openRegisterModal() { document.getElementById('registerModal').classList.add('modal-active'); }
        function closeModal(id) { document.getElementById(id).classList.remove('modal-active'); }

        function handleCheckout() {
            const method = document.querySelector('input[name="paymentMethod"]:checked').value;
            const form = document.getElementById('checkout-form');
            if (method === 'vnpay') {
                // You can add a VNPAY modal or redirect here
                form.submit();
            } else {
                form.submit();
            }
        }
    </script>
</body>
</html>
