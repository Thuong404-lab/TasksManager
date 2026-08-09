USE TaskManagerDB;
GO

-- Tai khoan admin/admin123 dung MD5 cu de khoi tao. Lan dang nhap dau tien
-- ung dung se tu dong nang cap hash sang BCrypt.
INSERT INTO users (user_account, user_password, user_name, user_email, role)
VALUES ('admin', '0192023a7bbd73250516f069df18b500', N'Quản trị viên', 'admin@example.com', 'admin');

INSERT INTO priorities (priority_name, color_code)
VALUES (N'Cao', '#DC3545'), (N'Trung bình', '#FFC107'), (N'Thấp', '#198754');
GO
