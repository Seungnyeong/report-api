package com.wemakeprice.vms.reportapi.docx;

import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import javax.xml.bind.JAXBException;
import java.io.IOException;


public interface DocxService {
    void createReport(ReportInfo.Main report) throws Exception;
}
