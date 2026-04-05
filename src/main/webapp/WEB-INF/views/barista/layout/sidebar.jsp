<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<aside class="hidden md:flex fixed left-0 top-0 h-screen w-64 bg-surface-container-low flex-col py-8 z-40 border-r border-outline-variant/10">
    <div class="px-6 mb-10">
        <h2 class="font-headline font-bold text-lg text-tertiary">Chip3Chip</h2>
        <p class="text-xs font-body text-on-surface-variant opacity-70">Barista Station</p>
    </div>
    
    <nav class="flex-1 space-y-2 px-3">
        <a class="flex items-center gap-3 px-4 py-3 rounded-full bg-[#853bb5]/10 text-tertiary font-semibold active-nav-border transition-all duration-200" 
           href="${pageContext.request.contextPath}/barista/orders">
            <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">pending_actions</span>
            <span class="font-body">Live Queue</span>
        </a>
        <a class="flex items-center gap-3 px-4 py-3 rounded-full text-on-surface/70 hover:bg-surface-container-highest transition-colors duration-200" 
           href="#">
            <span class="material-symbols-outlined">chat_bubble</span>
            <span class="font-body">Chat</span>
        </a>
    </nav>

    <div class="px-6 mt-auto">
        <div class="flex flex-col gap-4 p-4 bg-surface-container-lowest rounded-lg editorial-shadow border border-outline-variant/5">
            <div class="flex items-center gap-3">
                <div class="w-10 h-10 rounded-full bg-primary-container flex items-center justify-center text-on-primary-container font-bold">
                    ${sessionScope.account.person.name.substring(0,1)}
                </div>
                <div class="overflow-hidden">
                    <p class="text-sm font-bold truncate">${sessionScope.account.person.name}</p>
                    <p class="text-[10px] text-on-surface-variant opacity-70 uppercase tracking-widest font-bold">
                        ${sessionScope.account.role}
                    </p>
                </div>
            </div>
            
            <a href="${pageContext.request.contextPath}/logout" class="w-full py-2 bg-primary/10 text-primary rounded-full text-xs font-bold flex items-center justify-center gap-2 hover:bg-primary/20 transition-all">
                <span class="material-symbols-outlined text-sm">logout</span> Đăng xuất
            </a>
        </div>
    </div>
</aside>