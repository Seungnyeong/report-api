package com.wemakeprice.vms.reportapi.docx;

import com.wemakeprice.vms.reportapi.application.report.ReportFacade;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocxServiceImpl implements DocxService {

    @Override
    public void createHeaderTemplate(ReportInfo.Main report) throws FileNotFoundException, JAXBException, Docx4JException {

        ClassPathResource resource = new ClassPathResource("report_template/header.docx");
        boolean save = false;

        WordprocessingMLPackage wordMLPackage = org.docx4j.openpackaging.packages.WordprocessingMLPackage.load(
                new File("/Users/sn/workspace/vms-report-api/src/main/resources/"+resource.getPath())
        );

        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        HashMap<String, String> mappings = new HashMap<String, String>();

        mappings.put("controlnumber", report.getReportControlNumber());
        mappings.put("title", report.getTitle());
        mappings.put("created", report.getCreated().toString());
        mappings.put("grade", report.getReportVGrade().toString());
        mappings.put("possibility", report.getReportVPossibility().toString());
        mappings.put("review", report.getGeneralReview());
        documentPart.variableReplace(mappings);

        String xml = XmlUtils.marshaltoString(documentPart.getJaxbElement(), true);
        Object obj = XmlUtils.unmarshallFromTemplate(xml, mappings);
        documentPart.setJaxbElement((Document) obj);
        Save save2 = new Save(wordMLPackage);
        save2.save(new FileOutputStream("/Users/sn/workspace/vms-report-api/src/main/resources/abc.docx"));
    }
}
