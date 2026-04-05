<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="header-modern">
    <div class="header-search">
        <span class="material-symbols-outlined search-icon">search</span>
        <input type="text" placeholder="Tìm kiếm món nước, mã đơn..." class="search-input">
    </div>

    <div class="header-actions">
        <div class="digital-clock" id="clock">00:00:00</div>
        <button class="icon-btn">
            <span class="material-symbols-outlined">notifications</span>
            <span class="dot"></span>
        </button>
        <button class="icon-btn">
            <span class="material-symbols-outlined">settings</span>
        </button>
        <div class="divider"></div>
        <img src="https://ui-avatars.com/api/?name=Admin&background=a74880&color=fff" class="avatar-img">
    </div>
</header>

<style>
    .header-modern {
        height: 70px;
        background: rgba(255, 255, 255, 0.8);
        backdrop-filter: blur(10px);
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 30px;
        position: sticky;
        top: 0;
        z-index: 900;
        border-bottom: 1px solid rgba(0,0,0,0.05);
        margin-left: 260px; /* Bằng chiều rộng sidebar */
    }
    .header-search {
        position: relative;
        width: 400px;
    }
    .search-icon {
        position: absolute;
        left: 15px;
        top: 50%;
        transform: translateY(-50%);
        color: #999;
    }
    .search-input {
        width: 100%;
        padding: 10px 15px 10px 45px;
        border-radius: 25px;
        border: 1px solid #eee;
        background: #f8f8f8;
        transition: all 0.3s;
    }
    .search-input:focus { background: #fff; border-color: #a74880; outline: none; box-shadow: 0 0 0 4px rgba(167, 72, 128, 0.05); }

    .header-actions { display: flex; align-items: center; gap: 20px; }
    .digital-clock { font-family: 'Courier New', monospace; font-weight: bold; color: #a74880; background: #fdf2f8; padding: 5px 15px; border-radius: 8px; }
    .icon-btn {
        background: none; border: none; cursor: pointer; color: #666; position: relative;
        display: flex; align-items: center;
    }
    .icon-btn:hover { color: #a74880; }
    .dot { position: absolute; top: 0; right: 0; width: 8px; height: 8px; background: #ff4757; border-radius: 50%; border: 2px solid white; }
    .divider { width: 1px; height: 25px; background: #eee; }
    .avatar-img { width: 40px; height: 40px; border-radius: 50%; border: 2px solid #fdf2f8; }
</style>