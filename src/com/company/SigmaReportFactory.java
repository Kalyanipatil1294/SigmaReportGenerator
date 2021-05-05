package com.company;

import java.io.File;

public class SigmaReportFactory {
    public SigmaReportGenerator processReport(String dataType) {
        if (dataType.equalsIgnoreCase("TXT")) {
            return new TextSRG(new File("input.txt"));
        } else if (dataType.equalsIgnoreCase("XML")) return new XMLSRG(new File("input.xml"));
        return null;
    }
}
