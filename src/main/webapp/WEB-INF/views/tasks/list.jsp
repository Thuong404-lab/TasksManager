<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/fragments/header.jsp" %>
    <body>
        <div class="container-fluid py-4">
            <div class="row g-4">

                <div class="col-12 col-lg-3 col-xl-2">
                    <div class="card sidebar-card rounded-4 h-100">
                        <div class="card-body p-4">
                            <h3 class="fw-bold mb-1">Task Manager</h3>
                            <p class="text-muted small mb-4">Quản lý công việc cá nhân</p>

                            <div class="d-flex flex-column gap-2 mb-4">
                                <a href="<%= request.getContextPath()%>/tasks" class="sidebar-link active">Công việc</a>
                                <c:if test="${sessionScope.loggedInUser.role == 'admin' || sessionScope.loggedInUser.role == 'ADMIN'}">
                                    <a href="<%= request.getContextPath()%>/users" class="sidebar-link">Người dùng</a>
                                    <a href="<%= request.getContextPath()%>/priorities" class="sidebar-link">Mức ưu tiên</a>
                                </c:if>
                                <form action="<%= request.getContextPath()%>/logout" method="POST">
                                    <input type="hidden" name="csrfToken" value="<c:out value='${sessionScope.csrfToken}'/>">
                                    <button type="submit" class="sidebar-link text-danger border-0 bg-transparent w-100 text-start">Đăng xuất</button>
                                </form>
                            </div>

                            <div class="bg-light rounded-4 p-3">
                                <div class="text-muted small">Đăng nhập bởi</div>
                                <div class="fw-bold"><c:out value="${sessionScope.loggedInUser.userName}"/></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12 col-lg-9 col-xl-10">
                    
                    <div class="card main-card rounded-4 mb-4">
                        <div class="card-body p-4 d-flex justify-content-between align-items-center flex-wrap gap-3">
                            <div>
                                <h2 class="fw-bold mb-1">Dashboard</h2>
                                <p class="text-muted mb-0">Theo dõi nhiệm vụ, người dùng và mức độ ưu tiên</p>
                            </div>
                            <div class="d-flex gap-2">
                                <a href="<%= request.getContextPath()%>/tasks?view=add" class="btn btn-primary rounded-3">+ Tạo task</a>
                            </div>
                        </div>
                    </div>

                    <div class="row g-3 mb-4">
                        <div class="col-12 col-md-4">
                            <div class="card stat-card rounded-4">
                                <div class="card-body">
                                    <div class="text-muted">Tổng công việc</div>
                                    <h2 class="fw-bold mb-0">${totalTasks}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-4">
                            <div class="card stat-card rounded-4">
                                <div class="card-body">
                                    <div class="text-muted">Đã hoàn thành</div>
                                    <h2 class="fw-bold mb-0">${completedTasks}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-4">
                            <div class="card stat-card rounded-4">
                                <div class="card-body">
                                    <div class="text-muted">Sắp đến hạn</div>
                                    <h2 class="fw-bold mb-0">${upcomingTasks}</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card main-card rounded-4">
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-center flex-wrap gap-3 mb-4">
                                <h3 class="fw-bold mb-0">Danh sách công việc</h3>
                               
                            </div>

                            <div class="table-responsive">
                                <table class="table align-middle">
                                    <thead class="text-muted">
                                        <tr>
                                            <th>Tên công việc</th>
                                            <th>Người phụ trách</th>
                                            <th>Ưu tiên</th>
                                            <th>Hạn chót</th>
                                            <th>Trạng thái</th>
                                            <th class="text-end">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${tasks}" var="t">
                                            <tr>
                                                <td class="fw-semibold"><c:out value="${t.taskName}"/></td>
                                                <td><c:out value="${t.user.userName}"/></td>
                                                <td>
                                                    <span class="badge rounded-pill"
                                                          style="background:${empty t.priority.colorCode ? '#e9ecef' : t.priority.colorCode}; color:#111;">
                                                        <c:out value="${t.priority.priorityName}"/>
                                                    </span>
                                                </td>
                                                <td>${t.dueDate}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${t.status == 'done' || t.status == 'true' || t.status == '1'}">
                                                            <span class="badge rounded-pill text-bg-success">Hoàn thành</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge rounded-pill text-bg-secondary">Đang làm</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="text-end">
                                                    <a href="<%= request.getContextPath()%>/tasks?view=edit&id=${t.id}"
                                                       class="btn btn-outline-secondary btn-sm">Sửa</a>
                                                    <a href="<%= request.getContextPath()%>/tasks?view=delete&id=${t.id}"
                                                       class="btn btn-outline-danger btn-sm">Xóa</a>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        <c:if test="${empty tasks}">
                                            <tr>
                                                <td colspan="6" class="text-center text-muted py-4">
                                                    Không có công việc nào
                                                </td>
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
