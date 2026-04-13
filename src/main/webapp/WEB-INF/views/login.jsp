<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="light" lang="en">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>Login - Chip3Chip Atelier</title>
    
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=Be+Vietnam+Pro:wght@300;400;500;600&display=swap" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet"/>
    
    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <script id="tailwind-config">
          tailwind.config = {
            darkMode: "class",
            theme: {
              extend: {
                "colors": {
                        "on-primary-fixed": "#51092a",
                        "on-tertiary-fixed-variant": "#620e92",
                        "on-secondary-container": "#38643e",
                        "surface-container-low": "#f9f3ed",
                        "on-secondary": "#e9ffe6",
                        "tertiary-fixed": "#dca2ff",
                        "surface-tint": "#974362",
                        "primary-fixed-dim": "#fb94b7",
                        "error-container": "#f76a80",
                        "tertiary-dim": "#782ca8",
                        "error-dim": "#770326",
                        "tertiary-container": "#dca2ff",
                        "background": "#fef8f3",
                        "error": "#ac3149",
                        "secondary-dim": "#315c37",
                        "surface-container-highest": "#e8e1da",
                        "on-tertiary-fixed": "#370056",
                        "on-tertiary": "#fff6fd",
                        "surface-container": "#f3ede6",
                        "primary-dim": "#883756",
                        "outline-variant": "#b7b1aa",
                        "secondary": "#3c6942",
                        "on-error": "#fff7f7",
                        "tertiary-fixed-dim": "#d48fff",
                        "tertiary": "#853bb5",
                        "primary": "#974362",
                        "surface-container-high": "#eee7e0",
                        "outline": "#7f7a74",
                        "secondary-container": "#cbfecc",
                        "on-surface": "#35322d",
                        "surface-variant": "#e8e1da",
                        "surface-container-lowest": "#ffffff",
                        "on-tertiary-container": "#570086",
                        "secondary-fixed-dim": "#bdefbe",
                        "primary-fixed": "#ffa8c4",
                        "secondary-fixed": "#cbfecc",
                        "on-error-container": "#68001f",
                        "on-background": "#35322d",
                        "inverse-primary": "#fe97b9",
                        "on-primary": "#fff7f7",
                        "surface": "#fef8f3",
                        "inverse-surface": "#0f0e0b",
                        "surface-dim": "#e0d9d1",
                        "on-secondary-fixed-variant": "#426e47",
                        "on-primary-container": "#6c203f",
                        "surface-bright": "#fef8f3",
                        "primary-container": "#ffa8c4",
                        "on-surface-variant": "#625e59",
                        "inverse-on-surface": "#a09c98",
                        "on-secondary-fixed": "#25512d",
                        "on-primary-fixed-variant": "#772948"
                },
                "borderRadius": {
                        "DEFAULT": "1rem",
                        "lg": "2rem",
                        "xl": "3rem",
                        "full": "9999px"
                },
                "fontFamily": {
                        "headline": ["Plus Jakarta Sans"],
                        "body": ["Be Vietnam Pro"],
                        "label": ["Plus Jakarta Sans"]
                }
              },
            },
          }
    </script>
    <style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
        }
        .editorial-gradient {
            background: linear-gradient(135deg, #974362 0%, #ffa8c4 100%);
        }
        .glass-panel {
            background: rgba(254, 248, 243, 0.8);
            backdrop-filter: blur(16px);
        }
        .role-active {
            background-color: white !important;
            color: #974362 !important;
            font-weight: 700;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body class="bg-surface font-body text-on-surface min-h-screen flex flex-col items-center justify-start md:justify-center relative overflow-x-hidden overflow-y-auto py-8 md:py-12">

<c:set var="currentRole" value="${empty selectedRole ? 'staff' : selectedRole}" />

<div class="absolute -top-24 -right-24 w-96 h-96 bg-secondary-container/30 rounded-full blur-3xl opacity-50"></div>
<div class="absolute -bottom-24 -left-24 w-96 h-96 bg-primary-container/20 rounded-full blur-3xl opacity-50"></div>

<main class="w-full max-w-7xl px-6 flex flex-col md:flex-row items-center justify-center gap-12 lg:gap-24 relative z-10 flex-1">
    
    <div class="hidden md:flex flex-col items-start max-w-md">
        <div class="relative mb-12">
           <img
                alt="Chip3Chip Management Team"
                class="w-full h-[500px] object-cover rounded-lg shadow-2xl rotate-2 hover:rotate-0 transition-transform duration-700"
                src="https://res.cloudinary.com/dluherpa2/image/upload/f_auto,q_auto/Nhom_03_axg2ny"
                onerror="this.onerror=null;this.src='https://placehold.co/800x1000?text=Chip3Chip+Team';"
>
            <div class="absolute -bottom-6 -left-6 editorial-gradient p-8 rounded-lg text-on-primary">
                <p class="font-headline text-4xl font-extrabold tracking-tighter leading-none">GROUP 3.</p>
            </div>
        </div>
        <h1 class="font-headline text-5xl font-extrabold text-primary tracking-tighter mb-4">Chip3Chip</h1>
        <p class="text-on-surface-variant text-lg font-light leading-relaxed">
            <em style="font-size: 1.125rem;">“Chip3Chip – Little Chips, Big Happiness"</em>
        </p>
    </div>

    <div class="w-full max-w-md">
        <div class="bg-surface-container-lowest p-8 md:p-12 rounded-xl shadow-[0_32px_64px_-12px_rgba(53,50,45,0.06)] border border-outline-variant/15 flex flex-col items-center">
            
            <div class="mb-10 text-center">
                <div class="inline-flex items-center justify-center w-20 h-20 bg-secondary-container rounded-full mb-6">
                    <span class="material-symbols-outlined text-4xl text-on-secondary-container">coffee</span>
                </div>
                <h2 class="font-headline text-3xl font-bold text-on-surface tracking-tight mb-2">Welcome Back</h2>
                <p class="text-on-surface-variant font-medium">Please enter your atelier credentials</p>
            </div>

            <c:if test="${not empty error}">
                <div class="w-full mb-4 p-4 bg-error-container text-on-error-container rounded-full text-center text-sm font-bold">
                    ${error}
                </div>
            </c:if>

            <div class="w-full bg-surface-container p-1 rounded-full flex mb-8">
                <button type="button" onclick="selectRole('staff', this)" class="role-btn flex-1 py-2 px-4 rounded-full text-on-surface-variant font-medium transition-all duration-300">
                    Staff
                </button>
                <button type="button" onclick="selectRole('admin', this)" class="role-btn flex-1 py-2 px-4 rounded-full text-on-surface-variant font-medium transition-all duration-300">
                    Admin
                </button>
                <button type="button" onclick="selectRole('barista', this)" class="role-btn flex-1 py-2 px-4 rounded-full text-on-surface-variant font-medium transition-all duration-300">
                    Barista
                </button>
            </div>

            <form action="${pageContext.request.contextPath}/login" method="POST" class="w-full space-y-6">
                <input type="hidden" name="role" id="selected-role" value="${currentRole}">

                <div class="space-y-2">
                    <label class="block text-sm font-headline font-bold text-on-surface-variant ml-4">Phone Number</label>
                    <div class="relative">
                        <span class="material-symbols-outlined absolute left-5 top-1/2 -translate-y-1/2 text-outline">call</span>
                        <input name="phone" required class="w-full pl-14 pr-6 py-4 bg-surface-container-highest border-none rounded-full focus:ring-2 focus:ring-primary/20 focus:bg-surface-container-lowest transition-all duration-300 placeholder:text-outline" 
                               placeholder="+1 (555) 000-0000" type="tel" value="<c:out value='${phone}'/>"/>
                    </div>
                </div>

                <div class="space-y-2">
                    <label class="block text-sm font-headline font-bold text-on-surface-variant ml-4">Password</label>
                    <div class="relative">
                        <span class="material-symbols-outlined absolute left-5 top-1/2 -translate-y-1/2 text-outline">lock</span>
                        <input name="password" required class="w-full pl-14 pr-6 py-4 bg-surface-container-highest border-none rounded-full focus:ring-2 focus:ring-primary/20 focus:bg-surface-container-lowest transition-all duration-300 placeholder:text-outline" 
                               placeholder="••••••••" type="password"/>
                    </div>
                </div>

                <button class="w-full editorial-gradient py-5 px-8 rounded-full text-on-primary font-headline font-bold text-lg shadow-lg hover:scale-[1.02] active:scale-[0.98] transition-all duration-300 flex items-center justify-center gap-3 group" type="submit">
                    Login to Atelier
                    <span class="material-symbols-outlined transition-transform group-hover:translate-x-1">arrow_forward</span>
                </button>
            </form>

            <div class="mt-10 pt-8 border-t border-outline-variant/15 w-full text-center">
                <p class="text-on-surface-variant text-sm">
                    New to the team? <a class="text-primary font-bold" href="#">Contact Admin</a>
                </p>
            </div>
        </div>
    </div>
</main>

<footer class="w-full py-8 relative z-10">
    <div class="max-w-7xl mx-auto px-8 flex flex-col md:flex-row justify-between items-center opacity-60">
        <span class="font-headline font-bold text-primary tracking-tighter text-xl">Chip3Chip</span>
        <div class="flex gap-8">
            <a class="text-sm font-medium hover:text-primary transition-colors" href="#">Privacy Policy</a>
            <a class="text-sm font-medium hover:text-primary transition-colors" href="#">Help Center</a>
            <span class="text-sm">© 2026 Chip3Chip Atelier</span>
        </div>
    </div>
</footer>

<script>
    function selectRole(role, btn) {
        // Cập nhật giá trị vào input hidden
        document.getElementById('selected-role').value = role;
        
        // Xóa class active ở tất cả các nút
        document.querySelectorAll('.role-btn').forEach(b => {
            b.classList.remove('role-active', 'bg-surface-container-lowest', 'text-primary', 'font-bold', 'shadow-sm');
        });
        
        // Thêm class active cho nút hiện tại
        btn.classList.add('role-active');
    }

    document.addEventListener('DOMContentLoaded', function () {
        const currentRole = '${currentRole}';
        const activeButton = Array.from(document.querySelectorAll('.role-btn')).find(function (btn) {
            return btn.textContent.trim().toLowerCase() === currentRole;
        });

        if (activeButton) {
            selectRole(currentRole, activeButton);
        }
    });
</script>

</body>
</html>
