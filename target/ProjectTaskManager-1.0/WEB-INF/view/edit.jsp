<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sửa task</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>

        <style>
            body{
                background-color: #f8f9fa;
            }
            .task-modal-box{
                max-width: 860px;
                margin: 40px auto;
                background: #fff;
                border-radius: 24px;
                padding: 32px;
                box-shadow: 0 0 20px rgba(0,0,0,0.05);
            }
            .form-control,
            .form-select{
                min-height: 58px;
                border-radius: 18px;
            }
            .btn-rounded{
                border-radius: 18px;
                padding: 12px 24px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="task-modal-box">

                <div class="d-flex justify-content-between align-items-start mb-4">
                    <div>
                        <h2 class="fw-bold mb-1">Sửa task</h2>
                        <p class="text-secondary mb-0">Cập nhật thông tin công việc</p>
                    </div>

                    <a href="<%= request.getContextPath()%>/tasks" class="btn btn-link text-dark text-decoration-none fs-5 p-0">
                        Đóng
                    </a>
                </div>

                <form action="<%= request.getContextPath()%>/tasks" method="POST">
                    <input type="hidden" name="view" value="edit">
                    <input type="hidden" name="id" value="${task.id}">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </c:if>

                    <div class="mb-4">
                        <label for="taskName" class="form-label fw-semibold">Tên công việc</label>
                        <input type="text" class="form-control" id="taskName" name="name" value="${task.taskName}" placeholder="Nhập tên công việc" required>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <label for="assignee" class="form-label fw-semibold">Người phụ trách</label>
                            <select class="form-select" id="assignee" name="assignee" required>
                                <option value="" disabled>Chọn người phụ trách</option>
                                <c:forEach items="${users}" var="u">
                                    <option value="${u.id}" ${u.id == task.user.id ? 'selected' : ''}>${u.userName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-6 mb-4">
                            <label for="priority" class="form-label fw-semibold">Mức ưu tiên</label>
                            <select class="form-select" id="priority" name="priority" required>
                                <option value="" disabled>Chọn mức ưu tiên</option>
                                <c:forEach items="${priorities}" var="p">
                                    <option value="${p.id}" ${p.id == task.priorities.id ? 'selected' : ''}>${p.priorityName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <label for="deadline" class="form-label fw-semibold">Hạn chót</label>
                            <input type="date" class="form-control" id="deadline" name="deadline" value="${task.dueDate}">
                        </div>

                        <div class="col-md-6 mb-4">
                            <label for="status" class="form-label fw-semibold">Trạng thái</label>
                            <select class="form-select" id="status" name="status">
                                <option value="doing" ${task.status == 'doing' ? 'selected' : ''}>Đang làm</option>
                                <option value="done" ${task.status == 'done' || task.status == 'true' || task.status == '1' ? 'selected' : ''}>Hoàn thành</option>
                            </select>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end gap-3 mt-3">
                        <a href="<%= request.getContextPath()%>/tasks" class="btn btn-outline-secondary btn-rounded">
                            Hủy
                        </a>
                        <button type="submit" class="btn btn-primary btn-rounded">
                            Cập nhật task
                        </button>
                    </div>

                </form>
            </div>
        </div>

        <script src="<%= request.getContextPath()%>/assets/js/bootstrap.bundle.min.js"></script>
    </body>
</html>