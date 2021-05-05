package com.company;

public class Main {
    public static void main(String[] args) {
            SigmaReportFactory reportFactory = new SigmaReportFactory();
            SigmaReportGenerator reportGenerator = reportFactory.processReport("TXT");
            reportGenerator.processData();
            reportGenerator.generateReport();
    }
}
