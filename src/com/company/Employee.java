package com.company;

public class Employee {
    String employeeName;
    String employeeStatus;
    String email;
    public Employee(String employeeName, String email, String employeeStatus) {
        this.employeeName = employeeName;
        this.employeeStatus = employeeStatus;
        this.email = email;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public String getEmployeeName() {
        return employeeName;
    }
}
