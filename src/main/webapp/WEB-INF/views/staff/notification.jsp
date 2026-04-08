<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Notifications</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="bg-gray-50">

<div class="flex min-h-screen">

    <!-- SIDEBAR -->
    <jsp:include page="/WEB-INF/views/staff/layout/sidebar.jsp" />

    <div class="flex-1 flex flex-col">

        <!-- HEADER -->
        <jsp:include page="/WEB-INF/views/staff/layout/header.jsp" />

        <!-- CONTENT -->
        <main class="p-6">

            <!-- TITLE -->
            <div class="bg-white p-5 rounded-2xl shadow-sm border mb-6">
                <h2 class="text-xl font-bold text-gray-800">
                    🔔 Thông báo
                </h2>
                <p class="text-sm text-gray-400">Danh sách thông báo của bạn</p>
            </div>

            <!-- LIST -->
            <div class="space-y-4">

                <c:if test="${empty notifications}">
                    <div class="text-center py-10 text-gray-400">
                        Không có thông báo nào
                    </div>
                </c:if>

                <c:forEach var="n" items="${notifications}">
                    <div class="bg-white p-4 rounded-xl shadow-sm border hover:shadow-md transition">

                        <div class="flex justify-between items-center">

                            <!-- CONTENT -->
                            <div>
                                <p class="text-gray-800 font-medium">
                                    ${n.content}
                                </p>
                            </div>

                            <!-- TIME -->
                            <div class="text-sm text-gray-400">
                                ${n.time}
                            </div>

                        </div>

                    </div>
                </c:forEach>

            </div>

        </main>

        <!-- FOOTER -->
        <jsp:include page="/WEB-INF/views/staff/layout/footer.jsp" />

    </div>

</div>

</body>
</html>