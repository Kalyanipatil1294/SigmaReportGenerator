package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Project {
    String projectId;
    Map<String, Task> taskMap = new HashMap<>();
    Date startDate;
    Date endDate;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String projectStatus;
    List<Employee> employees = new ArrayList<Employee>();

    public Project(String projectId, String projectStatus) {
        this.projectId = projectId;
        this.projectStatus = projectStatus;
    }

    public Project(String projectId, Task task) {
        this.projectId = projectId;
        taskMap.put(task.taskId, task);
        setDates(task);
        setProjectStatus();
    }

    public Project(String projectId, Task task, Employee employee) {
        this.projectId = projectId;
        taskMap.put(task.taskId, task);
        employees.add(employee);
        setDates(task);
        setProjectStatus();
    }

    public void setDates(Task task) {
        try {
            if (task.getStartDate() != null && !task.getStartDate().isBlank()) {
                setStartDate(dateFormat.parse(task.getStartDate()));
            }
            if (task.getEndDate() != null && !task.getEndDate().isBlank()) {
                setEndDate(dateFormat.parse(task.getEndDate()));
            }
        } catch (Exception e) {
            System.out.println("Date Exception");
        }
    }

    public void setStartDate(Date startDate) {
        if (this.startDate == null || this.startDate.compareTo(startDate) > 0)
            this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        if (this.endDate == null || this.endDate.compareTo(endDate) < 0)
            this.endDate = endDate;
    }

    public void addTask(Task task) {
        taskMap.put(task.taskId, task);
        setDates(task);
        setProjectStatus();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public int getEmployeeCount(String status) {
        int count = 0;
        for (Employee employee : employees) {
            if (employee.getEmployeeStatus().equalsIgnoreCase(status)) {
                count++;
            }
        }
        return count;
    }

    public Map<String, Task> getTaskMap() {
        return taskMap;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void setProjectStatus() {
        String status = "completed";
        for (Map.Entry<String, Task> entry : taskMap.entrySet()) {
            Task task = entry.getValue();
            if (task.getEndDate().isBlank() || task.getEndDate() == null) {
                status = "active";
            }
        }
        this.projectStatus = status;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
