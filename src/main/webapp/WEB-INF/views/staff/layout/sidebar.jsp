<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
.sidebar {
    width: 220px;
    background: white;
    padding: 20px;
}

.sidebar-menu li {
    padding: 10px;
    border-radius: 6px;
    cursor: pointer;
}

.sidebar-menu li.active {
    background: #f5e6d3;
    color: #a74880;
}
</style>

<div class="sidebar">
    <h2>📋 Menu</h2>
    <ul>
        <li class="active">Dashboard</li>
        <li>Orders</li>
        <li>Products</li>
        <li>Customers</li>
        <li>Reports</li>
    </ul>
</div>