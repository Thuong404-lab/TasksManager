package com.taskmanager.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.regex.Pattern;

public final class InputValidator {
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9._-]{3,50}$");
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern COLOR = Pattern.compile("^#[0-9A-Fa-f]{6}$");
    private static final Set<String> ROLES = Set.of("admin", "user");
    private static final Set<String> STATUSES = Set.of("doing", "done");

    private InputValidator() {
    }

    public static String validateUser(String username, String password, String fullName, String email,
            String role, boolean passwordRequired) {
        if (username == null || !USERNAME.matcher(username.trim()).matches()) return "Tai khoan phai co 3-50 ky tu hop le.";
        if (fullName == null || fullName.trim().length() < 2 || fullName.trim().length() > 100) return "Ho ten phai co 2-100 ky tu.";
        if (email == null || email.length() > 150 || !EMAIL.matcher(email.trim()).matches()) return "Email khong hop le.";
        if (!ROLES.contains(role == null ? "" : role.toLowerCase())) return "Role khong hop le.";
        if ((passwordRequired || (password != null && !password.isBlank())) && (password == null || password.length() < 8 || password.length() > 72)) return "Mat khau phai co 8-72 ky tu.";
        return null;
    }

    public static String validatePriority(String name, String color) {
        if (name == null || name.trim().length() < 2 || name.trim().length() > 50) return "Ten priority phai co 2-50 ky tu.";
        if (color == null || !COLOR.matcher(color.trim()).matches()) return "Ma mau phai co dang #RRGGBB.";
        return null;
    }

    public static String safeColor(String color) {
        return color != null && COLOR.matcher(color).matches() ? color : "#6C757D";
    }

    public static String validateTask(String name, String deadline, String status) {
        if (name == null || name.trim().length() < 2 || name.trim().length() > 200) return "Ten task phai co 2-200 ky tu.";
        if (!STATUSES.contains(status)) return "Trang thai khong hop le.";
        if (deadline != null && !deadline.isBlank()) {
            try { LocalDate.parse(deadline); } catch (DateTimeParseException ex) { return "Deadline khong hop le."; }
        }
        return null;
    }

    public static Integer parsePositiveInt(String value) {
        try {
            int result = Integer.parseInt(value);
            return result > 0 ? result : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
