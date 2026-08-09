<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Đăng nhập - Task Manager</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/app.css"/>
    </head>
    <body>
        <main class="login-page">
            <div class="login-shell">
                <div class="login-brand">
                    <h1>Task Manager</h1>
                    <p class="text-muted mb-0">Quản lý công việc đơn giản và rõ ràng</p>
                </div>
                    <form action="<%= request.getContextPath()%>/login" method="POST" class="login-card">
                        <input type="hidden" name="csrfToken" value="<c:out value='${sessionScope.csrfToken}'/>">
                        <h2 class="h4 mb-1">Đăng nhập</h2>
                        <p class="text-muted mb-4">Nhập tài khoản để tiếp tục.</p>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>

                        <div class="mb-3">
                            <label class="form-label">Tài khoản</label>
                            <input type="text" name="name" class="form-control" required
                                   value="<c:out value='${not empty rememberedAccount ? rememberedAccount : param.name}'/>">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="pass" class="form-control" required>
                        </div>

                        <div class="form-check mb-3">
                            <input class="form-check-input" type="checkbox" id="remember" name="remember"
                                   ${rememberChecked ? 'checked' : ''}>
                            <label class="form-check-label" for="remember">Ghi nhớ tài khoản</label>
                        </div>

                        <div class="d-grid">
                            <input type="submit" value="Đăng nhập" class="btn btn-primary">
                        </div>
                    </form>
            </div>
        </main>
    </body>
</html>
