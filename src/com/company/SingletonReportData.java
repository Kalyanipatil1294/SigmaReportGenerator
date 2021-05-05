package com.company;

import java.util.HashMap;
import java.util.Map;

public class SingletonReportData {
    private static SingletonReportData singletonReportDataInstance = new SingletonReportData();
    private Map<String, Project> allProjects;

    private SingletonReportData() {
        allProjects = new HashMap<String, Project>();
    }

    public Map<String, Project> getAllProjects() {
        return allProjects;
    }

    public static SingletonReportData getInstance() {
        return singletonReportDataInstance;
    }

    private int processHashMapProjects(PROJECT_STATUS status) {
        int count = 0;
        for(Map.Entry<String, Project> entry : allProjects.entrySet()) {
            Project project = entry.getValue();
            if (project.getProjectStatus().equalsIgnoreCase(String.valueOf(status))) {
                count++;
            }
        }
        return count;
    }

    public int getNoOfCancelledProjects() {
        return processHashMapProjects(PROJECT_STATUS.CANCELLED);
    }

    public int getNoOfCompletedProjects() {
        return processHashMapProjects(PROJECT_STATUS.COMPLETED);
    }

    public int getNoOfActiveProjects() {
        return processHashMapProjects(PROJECT_STATUS.ACTIVE);
    }

    public void addProjects(Project project) {
        allProjects.put(project.getProjectId(), project);
    }
}
