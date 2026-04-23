/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LEGION
 */
public class Tasks {

    private Integer id;
    private Users user;
    private String taskName;
    private Priorities priorities;
    private String dueDate;
    private String status;

    public Tasks() {
    }

    public Tasks(Integer id, Users user, String taskName, Priorities priorities, String dueDate, String status) {
        this.id = id;
        this.user = user;
        this.taskName = taskName;
        this.priorities = priorities;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Priorities getPriorities() {
        return priorities;
    }

    public void setPriorities(Priorities priorities) {
        this.priorities = priorities;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
