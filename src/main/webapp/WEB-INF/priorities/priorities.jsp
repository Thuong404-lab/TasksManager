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
                                    <a href="<%= request.getContextPath()%>/users" class="sidebar-link">Users</a>
                                    <a href="<%= request.getContextPath()%>/priorities" class="sidebar-link active">Priorities</a>
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
                                <h2 class="fw-bold mb-1">Priorities</h2>
                                <p class="text-muted mb-0">Quản lý các mức độ ưu tiên của công việc</p>
                            </div>
                            <div class="d-flex gap-2">
                                <a href="<%= request.getContextPath()%>/priorities?action=add" class="btn btn-primary rounded-3">+ Thêm priority</a>
                            </div>
                        </div>
                    </div>

                    <div class="card main-card rounded-4">
                        <div class="card-body p-4">
                            


                            <div class="table-responsive">
                                <table class="table align-middle">
                                    <thead class="text-muted">
                                        <tr>
                                            <th>Tên mức ưu tiên</th>
                                            <th>Mã màu</th>
                                            <th>Hiển thị</th>
                                            <th class="text-end">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${priorities}" var="p">
                                            <tr>
                                                <td class="fw-semibold">${p.priorityName}</td>
                                                <td><code>${p.colorCode}</code></td>
                                                <td>
                                                    <span class="badge rounded-pill px-3 py-2" style="background-color: ${p.colorCode}; color: #fff; text-shadow: 0px 0px 2px rgba(0,0,0,0.5);">
                                                        ${p.priorityName}
                                                    </span>
                                                </td>
                                                <td class="text-end">
                                                    <a href="<%= request.getContextPath()%>/priorities?action=edit&id=${p.id}" class="btn btn-outline-secondary btn-sm">Sửa</a>
                                                    <a href="<%= request.getContextPath()%>/priorities?action=delete&id=${p.id}" class="btn btn-outline-danger btn-sm">Xóa</a>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        <c:if test="${empty priorities}">
                                            <tr>
                                                <td colspan="4" class="text-center text-muted py-4">Chưa có mức ưu tiên nào</td>
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