<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
.header {
    height: 60px;
    background: linear-gradient(90deg, #a74880, #8b3f70);
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 24px;
    color: white;
}

.nav-menu a {
    color: rgba(255,255,255,0.8);
    margin-right: 20px;
    text-decoration: none;
}

.nav-menu a.active {
    color: white;
    border-bottom: 2px solid white;
}
</style>
<div class="header">
    <div class="header-left">
        <div class="logo">☕ Chip3Chip</div>
        <div class="header-title">Staff Order Builder</div>
    </div>

    <div class="header-center">
        <input type="text" class="search-box" placeholder="Search drinks..." />
    </div>

    <div class="header-right">
        <div class="clock" id="clock"></div>
        <div class="profile-pic">H</div>
    </div>
</div>