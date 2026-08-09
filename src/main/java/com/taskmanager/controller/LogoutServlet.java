package com.taskmanager.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Xóa cookie ghi nhớ tài khoản cùng lúc với session để logout hoàn toàn.
        Cookie rememberCookie = new Cookie("rememberUser", "");
        rememberCookie.setHttpOnly(true);
        rememberCookie.setSecure(request.isSecure());
        rememberCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        rememberCookie.setMaxAge(0);
        response.addCookie(rememberCookie);
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
