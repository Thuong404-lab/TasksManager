<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dang nhap</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <form action="<%= request.getContextPath()%>/login" method="POST" class="border rounded p-4 shadow-sm bg-white">
                        <h2 class="text-center mb-4">Đăng nhập hệ thống</h2>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>

                        <div class="mb-3">
                            <label class="form-label">Tài Khoản</label>
                            <input type="text" name="name" class="form-control" required
                                   value="${not empty rememberedAccount ? rememberedAccount : param.name}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mật Khẩu</label>
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
            </div>
        </div>
    </body>
</html>
