<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sửa user</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>

        <style>
            body{ background-color: #f3f4f6; }
            .form-box{ max-width: 860px; margin: 40px auto; background: #fff; border: 1px solid #e5e7eb; border-radius: 30px; padding: 32px; box-shadow: 0 2px 12px rgba(0,0,0,0.04); }
            .form-control, .form-select{ min-height: 58px; border-radius: 18px; }
            .btn-rounded{ border-radius: 18px; padding: 12px 24px; }
        </style>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/app.css"/>
    </head>
    <body>
        <div class="container">
            <div class="form-box">

                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-2">Sửa user</h2>
                        <p class="text-secondary mb-0" style="font-size: 18px;">Cập nhật thông tin người dùng</p>
                    </div>

                    <a href="<%= request.getContextPath()%>/users" class="btn btn-link text-dark text-decoration-none fs-5 p-0">Đóng</a>
                </div>

                <form action="<%= request.getContextPath()%>/users" method="POST">
                    <input type="hidden" name="csrfToken" value="<c:out value='${sessionScope.csrfToken}'/>">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="${user.id}">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </c:if>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <label for="username" class="form-label fw-semibold">Tài khoản</label>
                            <input type="text" class="form-control" id="username" name="username" value="<c:out value='${user.userAccount}'/>" minlength="3" maxlength="50" pattern="[A-Za-z0-9._-]+" required>
                        </div>

                        <div class="col-md-6 mb-4">
                            <label for="fullName" class="form-label fw-semibold">Họ tên</label>
                            <input type="text" class="form-control" id="fullName" name="fullName" value="<c:out value='${user.userName}'/>" minlength="2" maxlength="100" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <label for="email" class="form-label fw-semibold">Email</label>
                            <input type="email" class="form-control" id="email" name="email" value="${user.userEmail}" required>
                        </div>

                        <div class="col-md-6 mb-4">
                            <label for="role" class="form-label fw-semibold">Role</label>
                            <select class="form-select" id="role" name="role">
                                <option value="user" ${user.role == 'user' || user.role == 'USER' ? 'selected' : ''}>USER</option>
                                <option value="admin" ${user.role == 'admin' || user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <label for="password" class="form-label fw-semibold">Mật khẩu mới (để trống nếu không đổi)</label>
                            <input type="password" class="form-control" id="password" name="password" minlength="8" maxlength="72" placeholder="Nhập mật khẩu mới">
                        </div>
                    </div>

                    <div class="d-flex justify-content-end gap-3 mt-3">
                        <a href="<%= request.getContextPath()%>/users" class="btn btn-outline-secondary btn-rounded">Hủy</a>
                        <button type="submit" class="btn btn-primary btn-rounded">Cập nhật</button>
                    </div>
                </form>

            </div>
        </div>

        <script src="<%= request.getContextPath()%>/assets/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
