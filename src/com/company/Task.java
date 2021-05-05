package com.company;

public class Task {
    String taskId;
    String startDate;
    String endDate;
    Employee employee;

    public Task(String taskId, String startDate, String endDate) {
        this.taskId = taskId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
