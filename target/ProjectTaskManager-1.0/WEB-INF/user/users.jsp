<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/include/hearder.jsp" %>
    <body>
        <div class="container-fluid py-4">
            <div class="row g-4">

                <div class="col-12 col-lg-3 col-xl-2">
                    <div class="card sidebar-card rounded-4 h-100">
                        <div class="card-body p-4">
                            <h3 class="fw-bold mb-1">Task Manager</h3>
                            <p class="text-muted small mb-4">Quản lý công việc cá nhân</p>

                            <div class="d-flex flex-column gap-2 mb-4">
                                <a href="<%= request.getContextPath()%>/tasks" class="sidebar-link">Dashboard</a>
                                <c:if test="${sessionScope.loggedInUser.role == 'admin' || sessionScope.loggedInUser.role == 'ADMIN'}">
                                    <a href="<%= request.getContextPath()%>/users" class="sidebar-link active">Users</a>
                                    <a href="<%= request.getContextPath()%>/priorities" class="sidebar-link">Priorities</a>
                                </c:if>
                                <a href="<%= request.getContextPath()%>/logout" class="sidebar-link text-danger">Logout</a>
                            </div>

                            <div class="bg-light rounded-4 p-3 mt-auto">
                                <div class="text-muted small">Đăng nhập bởi</div>
                                <div class="fw-bold">${sessionScope.loggedInUser.userName}</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12 col-lg-9 col-xl-10">
                    
                    <div class="card main-card rounded-4 mb-4">
                        <div class="card-body p-4 d-flex justify-content-between align-items-center flex-wrap gap-3">
                            <div>
                                <h2 class="fw-bold mb-1">Users</h2>
                                <p class="text-muted mb-0">Theo dõi và quản lý người dùng trong hệ thống</p>
                            </div>
                            <div class="d-flex gap-2">
                                <a href="<%= request.getContextPath()%>/users?action=add" class="btn btn-primary rounded-3">+ Tạo user</a>
                            </div>
                        </div>
                    </div>

                    <div class="row g-3 mb-4">
                        <div class="col-12 col-md-4">
                            <div class="card stat-card rounded-4">
                                <div class="card-body p-4">
                                    <div class="text-muted">Tổng người dùng</div>
                                    <h2 class="fw-bold mb-0">${totalUsers}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-4">
                            <div class="card stat-card rounded-4">
                                <div class="card-body p-4">
                                    <div class="text-muted">Quản trị viên</div>
                                    <h2 class="fw-bold mb-0 text-danger">${totalAdmins}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-4">
                            <div class="card stat-card rounded-4">
                                <div class="card-body p-4">
                                    <div class="text-muted">Người dùng thường</div>
                                    <h2 class="fw-bold mb-0">${totalMembers}</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card main-card rounded-4">
                        <div class="card-body p-4">
                            <h4 class="fw-bold mb-4">Danh sách người dùng</h4>

                            <div class="table-responsive">
                                <table class="table align-middle">
                                    <thead class="text-muted">
                                        <tr>
                                            <th>ID</th>
                                            <th>Tài khoản</th>
                                            <th>Họ tên</th>
                                            <th>Email</th>
                                            <th>Vai trò</th>
                                            <th class="text-end">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${users}" var="u">
                                            <tr>
                                                <td class="fw-semibold">${u.id}</td>
                                                <td>${u.userAccount}</td>
                                                <td>${u.userName}</td>
                                                <td>${u.userEmail}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${u.role == 'admin' || u.role == 'ADMIN'}">
                                                            <span class="badge rounded-pill text-bg-danger">ADMIN</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge rounded-pill text-bg-secondary">USER</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="text-end">
                                                    <a href="<%= request.getContextPath()%>/users?action=edit&id=${u.id}" class="btn btn-outline-secondary btn-sm">Sửa</a>
                                                    <a href="<%= request.getContextPath()%>/users?action=delete&id=${u.id}" class="btn btn-outline-danger btn-sm">Xóa</a>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        <c:if test="${empty users}">
                                            <tr>
                                                <td colspan="6" class="text-center text-muted py-4">Chưa có người dùng nào</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <script src="<%= request.getContextPath()%>/assets/js/bootstrap.bundle.min.js"></script>
    </body>
</html>