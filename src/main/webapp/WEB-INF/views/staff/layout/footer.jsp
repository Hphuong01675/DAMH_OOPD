<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<footer class="footer-modern">
    <div class="footer-content">
        <p class="copyright">© 2026 <strong>Chip3Chip</strong>. Hệ thống quản lý Barista chuyên nghiệp.</p>
        <div class="footer-links">
            <a href="#"><span class="material-symbols-outlined">help</span> Hỗ trợ</a>
            <a href="#"><span class="material-symbols-outlined">menu_book</span> Tài liệu</a>
            <span class="status-indicator">
                <span class="pulse"></span> Hệ thống đang hoạt động
            </span>
        </div>
    </div>
</footer>

<style>
    .footer-modern {
        margin-left: 260px;
        padding: 20px 30px;
        background: #fff;
        border-top: 1px solid #f0f0f0;
    }
    .footer-content {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .copyright { font-size: 0.85rem; color: #888; }
    .copyright strong { color: #a74880; }
    .footer-links { display: flex; align-items: center; gap: 25px; }
    .footer-links a {
        text-decoration: none;
        color: #666;
        font-size: 0.85rem;
        display: flex;
        align-items: center;
        gap: 5px;
        transition: color 0.3s;
    }
    .footer-links a:hover { color: #a74880; }
    .footer-links a .material-symbols-outlined { font-size: 1.1rem; }
    
    .status-indicator {
        font-size: 0.75rem;
        color: #2ecc71;
        display: flex;
        align-items: center;
        gap: 8px;
        background: #eafaf1;
        padding: 5px 12px;
        border-radius: 20px;
    }
    .pulse {
        width: 8px;
        height: 8px;
        background: #2ecc71;
        border-radius: 50%;
        box-shadow: 0 0 0 rgba(46, 204, 113, 0.4);
        animation: pulse-animation 2s infinite;
    }
    @keyframes pulse-animation {
        0% { box-shadow: 0 0 0 0px rgba(46, 204, 113, 0.4); }
        70% { box-shadow: 0 0 0 10px rgba(46, 204, 113, 0); }
        100% { box-shadow: 0 0 0 0px rgba(46, 204, 113, 0); }
    }
</style>