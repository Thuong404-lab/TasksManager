<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task Manager</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/bootstrap.min.css"/>

        <style>
            body {
                background-color: #f8f9fb;
            }
            .rounded-4 {
                border-radius: 1rem !important;
            }
            
            .sidebar-link {
                border-radius: 12px;
                padding: 12px 14px;
                color: #495057;
                text-decoration: none;
                display: block;
                transition: 0.2s ease-in-out;
            }
            .sidebar-link:hover {
                background-color: #e9ecef;
                color: #212529;
            }
            .sidebar-link.active {
                background-color: #0d6efd;
                color: white;
                font-weight: 600;
            }
            
            .stat-card, .main-card, .sidebar-card {
                border: 1px solid #e9ecef;
                box-shadow: 0 2px 8px rgba(0,0,0,0.04);
                background-color: #fff;
            }
        </style>
    </head>