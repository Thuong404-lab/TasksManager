<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xóa priority</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>

        <style>
            body{ background-color: #f3f4f6; }
            .delete-box{ max-width: 620px; margin: 60px auto; background: #fff; border: 1px solid #e5e7eb; border-radius: 28px; padding: 36px 32px 28px; box-shadow: 0 2px 12px rgba(0,0,0,0.04); }
            .btn-rounded{ border-radius: 18px; min-width: 90px; padding: 12px 22px; }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="delete-box">
                <h2 class="fw-bold mb-3">Xóa priority</h2>

                <p class="text-secondary mb-2" style="font-size: 18px;">Bạn có chắc muốn xóa mức ưu tiên: <strong>${priority.priorityName}</strong> không?</p>

                <form action="<%= request.getContextPath()%>/priorities" method="POST">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${priority.id}">

                    <div class="d-flex justify-content-end gap-3 mt-4">
                        <a href="<%= request.getContextPath()%>/priorities" class="btn btn-outline-secondary btn-rounded">Hủy</a>
                        <button type="submit" class="btn btn-danger btn-rounded">Xóa</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>