package com.company;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class TextSRG implements SigmaReportGenerator {
    File inputFile;
    SingletonReportData singletonReportData = SingletonReportData.getInstance();
    ReportWriter report;

    public TextSRG(File file) {
        inputFile = file;
    }

    @Override
    public void generateReport() {
        try {
            File myWriter = new File("output.txt");
            FileOutputStream fos = new FileOutputStream(myWriter);
            BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(fos));
            report = new TextReportWriter(singletonReportData, fileWriter);
            report.makeReport();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    @Override
    public void processData() {
        try {
            Scanner fileReader = new Scanner(inputFile);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                if (data.charAt(0) != '-') {
                    String[] values = data.replaceAll("\\s", "").split(",");
                    dataConverter(values);
                }

            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading file.");
            e.printStackTrace();
        }
    }

    public void dataConverter(String[] projectInfo) {
        Map<String, Project> allProjects = singletonReportData.getAllProjects();
        String endDate = "";
        switch (projectInfo.length) {
            case 2:
                if (allProjects.containsKey(projectInfo[1])) {
                    Project updateProject = allProjects.get(projectInfo[1]);
                    updateProject.setProjectStatus(projectInfo[1]);
                } else {
                    singletonReportData.addProjects(new Project(projectInfo[0], projectInfo[1]));
                }
                break;

            case 4:
                endDate = projectInfo[3] != null && !projectInfo[3].isEmpty() ? projectInfo[3] : "";
            case 3:
                if (allProjects.containsKey(projectInfo[1])) {
                    Project updateProject = allProjects.get(projectInfo[1]);
                    updateProject.addTask(new Task(projectInfo[0], projectInfo[2], endDate));
                } else {
                    singletonReportData.addProjects(new Project(projectInfo[1], new Task(projectInfo[0], projectInfo[2], endDate)));
                }
                break;
            case 5:
                if (allProjects.containsKey(projectInfo[1])) {
                    Project updateProject = allProjects.get(projectInfo[1]);
                    updateProject.addEmployee(new Employee(projectInfo[2], projectInfo[3], projectInfo[4]));
                } else {
                    singletonReportData.addProjects(
                            new Project(projectInfo[1],
                                    new Task(projectInfo[0], "", ""),
                                    new Employee(projectInfo[2], projectInfo[3], projectInfo[4])
                            )
                    );
                }
                break;
            default:
                break;
        }
    }

}
