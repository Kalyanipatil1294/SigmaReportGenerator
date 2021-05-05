package com.company;

import java.io.IOException;

public interface ReportWriter {
    public void makeReport();
    public void addReportData() throws IOException;
    public void addProjectData() throws IOException;
}
