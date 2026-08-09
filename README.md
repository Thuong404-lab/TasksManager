# Task Manager

Ứng dụng web quản lý công việc cá nhân được xây dựng bằng Jakarta EE, Servlet,
JSP/JSTL và Microsoft SQL Server.

## Tính năng

- Đăng nhập, đăng xuất và ghi nhớ tài khoản.
- Phân quyền `admin` và `user`.
- Quản lý công việc, người dùng và mức ưu tiên.
- Người dùng thường chỉ thao tác trên công việc được giao.
- Thống kê công việc hoàn thành và sắp đến hạn.
- Bảo mật mật khẩu bằng BCrypt, CSRF token và kiểm tra dữ liệu đầu vào.

## Công nghệ

- Java 11
- Jakarta EE 10
- Servlet, JSP và JSTL
- JDBC
- Microsoft SQL Server
- Bootstrap 5
- Maven
- JUnit 5

## Tài khoản demo

```yaml
Admin: admin / admin123
User:  user  / 123123123
```

> Chỉ sử dụng các tài khoản này trong môi trường học tập hoặc chạy local.

## Cài đặt

### 1. Khởi tạo database

Chạy lần lượt hai file trong SQL Server:

```text
database/schema.sql
database/seed.sql
```

### 2. Cấu hình kết nối

Thiết lập các biến môi trường:

```text
TASK_DB_URL=jdbc:sqlserver://127.0.0.1:1433;databaseName=TaskManagerDB;encrypt=false
TASK_DB_USER=sa
TASK_DB_PASSWORD=your_password
```

Nếu không thiết lập, ứng dụng sử dụng cấu hình local mặc định trong
`DatabaseConnectionProvider`.

### 3. Build và kiểm thử

```bash
mvn clean package
```

File WAR được tạo tại:

```text
target/task-manager-1.0.war
```

### 4. Chạy ứng dụng

Deploy file WAR lên Tomcat 10.1, sau đó truy cập:

```text
http://localhost:8080/ProjectTaskManager/login
```

## Cấu trúc chính

```text
src/main/java/com/taskmanager
├── config
├── controller
├── dao
├── filter
├── model
└── util

src/main/webapp/WEB-INF/views
├── auth
├── fragments
├── priorities
├── tasks
└── users
```

## Lưu ý

- Không commit mật khẩu database hoặc file `.env`.
- Nên sử dụng HTTPS và tài khoản database có quyền giới hạn khi triển khai thật.
- Đổi mật khẩu demo trước khi đưa ứng dụng lên môi trường công khai.
