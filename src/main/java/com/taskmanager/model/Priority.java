/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.taskmanager.model;

/**
 *
 * @author LEGION
 */
public class Priority {
    private Integer id;
    private String priorityName;
    private String colorCode;

    public Priority() {
    }

    public Priority(Integer id, String priorityName, String colorCode) {
        this.id = id;
        this.priorityName = priorityName;
        this.colorCode = colorCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
    
    
    
}
