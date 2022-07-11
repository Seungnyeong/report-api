package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.report.ReportFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.web.dto.ReportDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.docx4j.wml.ObjectFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.docx4j.*;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Slf4j
@Api(tags = "보고서 생성")
public class ReportController {

    private final ReportFacade reportFacade;

//    @ApiOperation(value = "레포트 파일 생성", notes = "개발전")
//    @PostMapping
//    public CommonResponse reportTest(@RequestParam String test) throws Docx4JException, JAXBException, FileNotFoundException {
//        ObjectFactory foo = Context.getWmlObjectFactory();
//
//        ClassPathResource resource = new ClassPathResource("report_template/test.docx");
//
//
//        boolean save = false;
//
//        WordprocessingMLPackage wordMLPackage = org.docx4j.openpackaging.packages.WordprocessingMLPackage.load(
//                new File("/Users/sn/workspace/vms-report-api/src/main/resources/"+resource.getPath())
//        );
//
//        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
//
//        HashMap<String, String> mappings = new HashMap<String, String>();
//
//        mappings.put("icecream", "chocolate");
//        mappings.put("color", "green");
//        long start = System.currentTimeMillis();
//
//        documentPart.variableReplace(mappings);
//
//        String xml = XmlUtils.marshaltoString(documentPart.getJaxbElement(), true);
//        Object obj = XmlUtils.unmarshallFromTemplate(xml, mappings);
//        documentPart.setJaxbElement((Document) obj);
//
//        long end = System.currentTimeMillis();
//        long total = end - start;
//
//
//        Save save2 = new Save(wordMLPackage);
//        save2.save(new FileOutputStream("/Users/sn/workspace/vms-report-api/src/main/resources/abc.docx"));
//
//        return CommonResponse.success(test);
//    }

    @ApiOperation(value = "레포트 파일 생성", notes = "개발전")
    @PostMapping
    public CommonResponse reportTest(@RequestBody @Valid ReportDto.GenerateReportRequest request) {
        var response = reportFacade.generateReport(request.toCommand());
        return CommonResponse.success(response);
    }

}
