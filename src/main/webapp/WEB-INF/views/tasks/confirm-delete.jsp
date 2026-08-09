<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xóa task</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>

        <style>
            body{
                background-color: #f8f9fa;
            }
            .delete-box{
                max-width: 600px;
                margin: 60px auto;
                background: #fff;
                border-radius: 28px;
                padding: 40px 32px 28px;
                box-shadow: 0 0 20px rgba(0,0,0,0.08);
            }
            .btn-rounded{
                border-radius: 18px;
                min-width: 90px;
                padding: 12px 22px;
            }
        </style>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/app.css"/>
    </head>
    <body>
        <div class="container">
            <div class="delete-box">
                <h2 class="fw-bold mb-3">Xóa task</h2>

                <p class="text-secondary fs-5 mb-5">
                    Bạn có chắc muốn xóa task <strong>${task.taskName}</strong> không?
                </p>

                <form action="<%= request.getContextPath()%>/tasks" method="POST">
                    <input type="hidden" name="csrfToken" value="<c:out value='${sessionScope.csrfToken}'/>">
                    <input type="hidden" name="view" value="delete">
                    <input type="hidden" name="id" value="${task.id}">

                    <div class="d-flex justify-content-end gap-3">
                        <a href="<%= request.getContextPath()%>/tasks" class="btn btn-outline-secondary btn-rounded">
                            Hủy
                        </a>
                        <button type="submit" class="btn btn-danger btn-rounded">
                            Xóa
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <script src="<%= request.getContextPath()%>/assets/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
