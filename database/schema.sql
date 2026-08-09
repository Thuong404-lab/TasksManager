IF DB_ID('TaskManagerDB') IS NULL CREATE DATABASE TaskManagerDB;
GO
USE TaskManagerDB;
GO

CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_account VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(60) NOT NULL,
    user_name NVARCHAR(100) NOT NULL,
    user_email VARCHAR(150) NOT NULL UNIQUE,
    role VARCHAR(10) NOT NULL CONSTRAINT CK_users_role CHECK (role IN ('admin', 'user'))
);

CREATE TABLE priorities (
    id INT IDENTITY(1,1) PRIMARY KEY,
    priority_name NVARCHAR(50) NOT NULL UNIQUE,
    color_code CHAR(7) NOT NULL CONSTRAINT CK_priorities_color CHECK (color_code LIKE '#[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]')
);

CREATE TABLE tasks (
    id INT IDENTITY(1,1) PRIMARY KEY,
    task_name NVARCHAR(200) NOT NULL,
    user_id INT NOT NULL,
    priority_id INT NOT NULL,
    due_date DATE NULL,
    status VARCHAR(10) NOT NULL CONSTRAINT DF_tasks_status DEFAULT 'doing',
    CONSTRAINT CK_tasks_status CHECK (status IN ('doing', 'done')),
    CONSTRAINT FK_tasks_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_tasks_priorities FOREIGN KEY (priority_id) REFERENCES priorities(id)
);

CREATE INDEX IX_tasks_user_id ON tasks(user_id);
CREATE INDEX IX_tasks_due_date ON tasks(due_date);
GO
