package com.wemakeprice.vms.reportapi.docx;

import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface DocxService {
    void createHeaderTemplate(ReportInfo.Main report) throws FileNotFoundException, JAXBException, Docx4JException;
}
