package com.company;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.Map;

public class XMLSRG implements SigmaReportGenerator{
    File inputFile;
    SingletonReportData singletonReportData =  SingletonReportData.getInstance();
    ReportWriter report;
    public XMLSRG(File file) {
        inputFile = file;
    }

    @Override
    public void generateReport() {
        try {
            File myWriter = new File("output.html");
            FileOutputStream fos = new FileOutputStream(myWriter);
            BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(fos));
            report = new XMLReportWriter(singletonReportData, fileWriter);
            report.makeReport();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    @Override
    public void processData() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList startTaskList = doc.getElementsByTagName("starttask");
            NodeList endTaskList = doc.getElementsByTagName("endtask");
            NodeList projectNodeList = doc.getElementsByTagName("project");
            NodeList assignedTaskList = doc.getElementsByTagName("assignedTask");

            for (int temp = 0; temp < startTaskList.getLength(); temp++) {
                Node nNode = startTaskList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    dataConverter(nNode);
                }
            }

            for (int temp = 0; temp < endTaskList.getLength(); temp++) {
                Node nNode = endTaskList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    dataConverter(nNode);
                }
            }

            for (int temp = 0; temp < projectNodeList.getLength(); temp++) {
                Node nNode = projectNodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    dataConverter(nNode);
                }
            }

            for (int temp = 0; temp < assignedTaskList.getLength(); temp++) {
                Node nNode = assignedTaskList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    dataConverter(nNode);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void dataConverter(Node node) {
        Map<String, Project> allProjects = singletonReportData.getAllProjects();
        Element eElement = (Element) node;
        String projectId = eElement.getAttribute("projectId");
        String taskId = eElement.getAttribute("taskId");
        switch (node.getNodeName()) {
            case "project":
                if (allProjects.containsKey(projectId)) {
                    Project updateProject = allProjects.get(projectId);
                    updateProject.setProjectStatus(eElement.getAttribute("status"));
                } else {
                    singletonReportData.addProjects(new Project(projectId, eElement.getAttribute("status")));
                }
                break;
            case "starttask":
                if (allProjects.containsKey(projectId)) {
                    Project updateProject = allProjects.get(projectId);
                    updateProject.addTask(new Task(taskId, eElement.getAttribute("startDate"), ""));
                } else {
                    singletonReportData.addProjects(new Project(projectId, new Task(taskId, eElement.getAttribute("startDate"), "")));
                }
                break;

            case "endtask":
                if (allProjects.containsKey(projectId)) {
                    Project updateProject = allProjects.get(projectId);
                    updateProject.addTask(new Task(taskId, eElement.getAttribute("startDate"), eElement.getAttribute("endDate")));
                } else {
                    singletonReportData.addProjects(new Project(projectId, new Task(taskId, eElement.getAttribute("startDate"), eElement.getAttribute("endDate"))));
                }
                break;
            case "assignedTask":
                if (allProjects.containsKey(projectId)) {
                    Project updateProject = allProjects.get(projectId);
                    updateProject.addEmployee(new Employee(eElement.getAttribute("empName"), eElement.getAttribute("email"), eElement.getAttribute("status")));
                } else {
                    singletonReportData.addProjects(
                            new Project(projectId,
                                    new Task(taskId,"", ""),
                                    new Employee(eElement.getAttribute("startDate"), eElement.getAttribute("email"),eElement.getAttribute("status"))
                            )
                    );
                }
                break;
            default:
                break;
        }
    }
}
