package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class XMLReportWriter implements ReportWriter {
    SingletonReportData singletonReportData;
    BufferedWriter fileWriter;

    public XMLReportWriter(SingletonReportData singletonReportData, BufferedWriter fileWriter) {
        this.singletonReportData = singletonReportData;
        this.fileWriter = fileWriter;
    }

    private void addEmployeeList(Project project) throws IOException {
        String result = "";
        for(Employee employee : project.getEmployees())
        {
            result += employee.getEmployeeName() + ", ";
        }
        fileWriter.write("<p>List of Employees (Staff and Contract employees) assigned to the project:" + result + "</p>");
        fileWriter.write("<p>******************************************</p>");

    }

    public void addHeader() throws IOException {
        fileWriter.write("<!DOCTYPE>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>SigmaReportGenerator</title>\n" +
                "</head>\n" +
                "<body>");
    }

    public void addFooter() throws IOException {
        fileWriter.write("</body>\n" +
                "</html>");
    }

    @Override
    public void addReportData() throws IOException {
        fileWriter.write("<p>No.of Projects Canceled:" + singletonReportData.getNoOfCancelledProjects()+ "</p>");
        fileWriter.write("<p>No.of Projects Completed:" + singletonReportData.getNoOfCompletedProjects()+"</p>");
        fileWriter.write("<p>No.of Projects Active:"+ singletonReportData.getNoOfActiveProjects()+ "</p>");
        fileWriter.write("<p>==========================================</p>");
    }

    @Override
    public void addProjectData() throws IOException {
        fileWriter.write("<p>A list of Completed Projects information below.</p>");
        fileWriter.newLine();
        for(Map.Entry<String, Project> entry : singletonReportData.getAllProjects().entrySet()) {
            Project project = entry.getValue();
            if (project.getProjectStatus().equalsIgnoreCase(String.valueOf(PROJECT_STATUS.COMPLETED))) {
                fileWriter.write("<p> Project Id:" + project.getProjectId()+ "</p>");
                fileWriter.write("<p> No.Of Tasks:" + project.getTaskMap().size()+ "</p>");
                fileWriter.write("<p> Start Date:" + project.getStartDate() + "</p>");
                fileWriter.write("<p> Completed Date:" + project.getEndDate()+ "</p>");
                fileWriter.write("<p> No.of Staff:" + project.getEmployeeCount("staff")+ "</p>");
                fileWriter.write("<p> No.of Contract Employees:" + project.getEmployeeCount("contract")+ "</p>");
                addEmployeeList(project);
            }
        }
    }


    @Override
    public void makeReport() {
        try {
            addHeader();
            addReportData();
            addProjectData();
            addFooter();
            fileWriter.close();
        } catch (Exception e) {
        }
    }
}
