package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class TextReportWriter implements ReportWriter {
    SingletonReportData singletonReportData;
    BufferedWriter fileWriter;

    public TextReportWriter(SingletonReportData singletonReportData, BufferedWriter fileWriter) {
        this.singletonReportData = singletonReportData;
        this.fileWriter = fileWriter;
    }


    private void addEmployeeList(Project project) throws IOException {
        String result = "";
        for(Employee employee : project.getEmployees())
        {
            result += employee.getEmployeeName() + ", ";
        }
        fileWriter.write("List of Employees (Staff and Contract employees) assigned to the project:" + result);
        fileWriter.newLine();
        fileWriter.write("******************************************");
        fileWriter.newLine();

    }

    public void addLineSeparator() throws IOException {
        fileWriter.newLine();
        fileWriter.write("==========================================");
        fileWriter.newLine();
    }

    @Override
    public void addReportData() throws IOException {
        fileWriter.write("No.of Projects Canceled:" + singletonReportData.getNoOfCancelledProjects());
        addLineSeparator();
        fileWriter.write("No.of Projects Completed:" + singletonReportData.getNoOfCompletedProjects());
        addLineSeparator();
        fileWriter.write("No.of Projects Active:"+ singletonReportData.getNoOfActiveProjects());
        addLineSeparator();
    }

    @Override
    public void addProjectData() throws IOException {
        fileWriter.write("A list of Completed Projects information below.");
        fileWriter.newLine();
        for(Map.Entry<String, Project> entry : singletonReportData.getAllProjects().entrySet()) {
            System.out.println(entry.getKey());
            Project project = entry.getValue();
            if (project.getProjectStatus().equalsIgnoreCase(String.valueOf(PROJECT_STATUS.COMPLETED))) {
                fileWriter.newLine();
                fileWriter.write(" Project Id:" + project.getProjectId());
                fileWriter.newLine();
                fileWriter.write(" No.Of Tasks:" + project.getTaskMap().size());
                fileWriter.newLine();
                fileWriter.write(" Start Date:" + project.getStartDate());
                fileWriter.newLine();
                fileWriter.write(" Completed Date:" + project.getEndDate());
                fileWriter.newLine();
                fileWriter.write(" No.of Staff:" + project.getEmployeeCount("staff"));
                fileWriter.newLine();
                fileWriter.write(" No.of Contract Employees:" + project.getEmployeeCount("contract"));
                fileWriter.newLine();
                addEmployeeList(project);
            }
        }
    }

    @Override
    public void makeReport() {
        try {
            addReportData();
            addProjectData();
            fileWriter.close();
        } catch (Exception e) {
        }
    }
}
