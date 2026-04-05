<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<aside class="sidebar">
    <div class="sidebar-brand">
        <div class="brand-logo">🍹</div>
        <div class="brand-text">
            <h2>Chip3Chip</h2>
            <span>Barista Station</span>
        </div>
    </div>

    <nav class="sidebar-nav">
        <ul>
            <li class="nav-item active">
                <span class="material-symbols-outlined">dashboard</span>
                <span>Dashboard</span>
            </li>
            <li class="nav-item">
                <span class="material-symbols-outlined">restaurant_menu</span>
                <span>Menu</span>
            </li>
            </ul>
    </nav>

    <div class="sidebar-footer">
        <div class="user-card">
            <div class="user-avatar">
                ${sessionScope.account != null ? sessionScope.account.person.name.substring(0,1) : 'A'} [cite: 301]
            </div>
            <div class="user-details">
                <p class="u-name">${sessionScope.account.person.name}</p> [cite: 301]
                <p class="u-role">${sessionScope.account.role}</p> [cite: 302]
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout"> [cite: 302]
            <span class="material-symbols-outlined">logout</span> [cite: 303]
            <span>Đăng xuất</span>
        </a>
    </div>
</aside>