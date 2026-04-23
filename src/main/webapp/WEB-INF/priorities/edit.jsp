<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sửa priority</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>

        <style>
            body{ background-color: #f3f4f6; }
            .form-box{ max-width: 760px; margin: 40px auto; background: #fff; border: 1px solid #e5e7eb; border-radius: 30px; padding: 32px; box-shadow: 0 2px 12px rgba(0,0,0,0.04); }
            .form-control{ min-height: 58px; border-radius: 18px; }
            .btn-rounded{ border-radius: 18px; padding: 12px 24px; }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="form-box">
                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-2">Sửa priority</h2>
                        <p class="text-secondary mb-0" style="font-size:18px;">Cập nhật thông tin ưu tiên</p>
                    </div>

                    <a href="<%= request.getContextPath()%>/priorities" class="btn btn-link text-dark text-decoration-none fs-5 p-0">Đóng</a>
                </div>

                <form action="<%= request.getContextPath()%>/priorities" method="POST">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="${priority.id}">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </c:if>

                    <div class="mb-4">
                        <label class="form-label fw-semibold">Tên mức ưu tiên</label>
                        <input type="text" class="form-control" name="priorityName" value="${priority.priorityName}" required>
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-semibold">Mã màu</label>
                        <input type="text" class="form-control" name="colorCode" value="${priority.colorCode}" required>
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-semibold">Màu củ</label><br>
                        <span class="px-3 py-2 rounded-pill fw-semibold d-inline-block" style="background:${priority.colorCode}; color:#111;">${priority.priorityName}</span>
                    </div>

                    <div class="d-flex justify-content-end gap-3 mt-3">
                        <a href="<%= request.getContextPath()%>/priorities" class="btn btn-outline-secondary btn-rounded">Hủy</a>
                        <button type="submit" class="btn btn-primary btn-rounded">Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
