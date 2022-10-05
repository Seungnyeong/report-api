package com.wemakeprice.vms.reportapi.infrastructure.external.docx;

import com.wemakeprice.vms.reportapi.config.FileStorageConfig;
import com.wemakeprice.vms.reportapi.docx.DocxService;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.xmlgraphics.image.loader.ImageSize;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBElement;
import java.io.*;
import java.lang.reflect.TypeVariable;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.docx4j.wml.STBrType.PAGE;


@Service
@Slf4j
public class DocxServiceImpl implements DocxService {

    private final ObjectFactory factory = new ObjectFactory();
    private final VItemService vItemService;
    private final Path reportStorageLocation;
    private static final String DOC_TYPE = "docx";
    private final String TEMPLATE_FILE;
    Integer tblContentFontSize = 16;

    @Autowired
    public DocxServiceImpl(FileStorageConfig fileStorageConfig, VItemService vItemService) throws FileNotFoundException {
        this.vItemService = vItemService;
        this.TEMPLATE_FILE = fileStorageConfig.getReportTemplateFile();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        this.reportStorageLocation = Paths.get(fileStorageConfig.getReportResultDir() + String.format("/%s", now.format(formatter)))
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(reportStorageLocation);
        } catch (Exception ex) {
            throw new FileNotFoundException();
        }
    }



    @Override
    public Path createReport(ReportInfo.Main reportInfo) throws Exception {

        ClassPathResource resource = new ClassPathResource(TEMPLATE_FILE);
        InputStream template = resource.getInputStream();

        var main = getTemplate(template);
        Br paging = factory.createBr();
        paging.setType(PAGE);
        P Bempty = createParaGraph(" ", "black", 100, JcEnumeration.LEFT, true, 1000, 25);
        P Sempty = createParaGraph(" ", "black", 100, JcEnumeration.LEFT, true, 500, 25);
        P title = createParaGraph(reportInfo.getTitle(), "black", 60, JcEnumeration.CENTER, true, 50,25);
        P titleAppendix = createParaGraph("취약점 점검 결과 보고", "black", 60, JcEnumeration.CENTER, true,50, 25);
        P created = createParaGraph(reportInfo.getCreated().format(DateTimeFormatter.ofPattern("yyyy. MM. dd")), "black", 40, JcEnumeration.CENTER, true,50, 25);
        P teamName = createParaGraph("CERT팀", "black", 40, JcEnumeration.CENTER, true,300, 25);

        var controlTbl =  createControlTbl(reportInfo.getReportControlNumber());

        main.getMainDocumentPart().addObject(controlTbl);
        main.getMainDocumentPart().addObject(Bempty);
        main.getMainDocumentPart().addObject(Bempty);
        main.getMainDocumentPart().addObject(title);
        main.getMainDocumentPart().addObject(titleAppendix);
        main.getMainDocumentPart().addObject(Bempty);
        main.getMainDocumentPart().addObject(Bempty);
        main.getMainDocumentPart().addObject(created);
        main.getMainDocumentPart().addObject(teamName);
        main.getMainDocumentPart().createParagraphOfText("");
        main.getMainDocumentPart().addObject(paging);

        var resultText = createUnnumberedList("취약점 점검 결과 등급", 1, 50,0);

        var resultTbl = resultGradeTbl(reportInfo.getTitle(), String.format("%s (%s)", reportInfo.getReportVGrade().getDescription(), reportInfo.getReportVPossibility().getDescription()));

        main.getMainDocumentPart().addObject(resultText);
        main.getMainDocumentPart().addObject(resultTbl);
        main.getMainDocumentPart().addObject(Sempty);
        var resultCheckTable = createUnnumberedList("취약점 점검 결과 요약",1, 50,0);

        var checkListTbl = createMainTbl(vItemService.retrieveVItemList(), reportInfo.getReportOptionGroupsList());
        main.getMainDocumentPart().addObject(resultCheckTable);
        main.getMainDocumentPart().addObject(checkListTbl);

        P check = createParaGraph("※ 체크리스트 기준", "black", 14, JcEnumeration.LEFT, true, 25, 25);
        P owasp = createParaGraph("OWASP(Open Web Application Security Project) Top 10 – www.owasp.org", "black", 12, JcEnumeration.LEFT, false, 25, 25);
        P nist = createParaGraph("NIST(National Institute of standards and Technology) Web Application Check List – c/src.nist.gov", "black", 12, JcEnumeration.LEFT, false, 25, 25);
        main.getMainDocumentPart().getContent().add(check);
        main.getMainDocumentPart().getContent().add(owasp);
        main.getMainDocumentPart().getContent().add(nist);
        main.getMainDocumentPart().addObject(paging);

        int length = reportInfo.getReportOptionGroupsList().size();
        AtomicInteger pageIndex = new AtomicInteger(0);
        reportInfo.getReportOptionGroupsList().stream()
                .sorted(Comparator.comparing(reportOptionGroupInfo -> reportOptionGroupInfo.getVItemDetailGroupInfo().getOrdering()))
                .forEach(reportOptionGroupInfo -> {
                    createDetail(reportOptionGroupInfo, main, pageIndex.intValue(), length);
                    pageIndex.getAndIncrement();
                });
        return saveReport(main, reportInfo);
    }

    private Path saveReport(WordprocessingMLPackage main, ReportInfo.Main reportInfo) throws Docx4JException {
        Path targetLocation;
        if (reportInfo.getReportFilePath() == null) {
            UUID uuid = UUID.randomUUID();
            targetLocation = this.reportStorageLocation.resolve(uuid + "." + DOC_TYPE);
        } else {
            targetLocation = this.reportStorageLocation.resolve(reportInfo.getReportFilePath());
        }
        main.save(new File(targetLocation.toAbsolutePath().toString()));
        return targetLocation.toAbsolutePath();
    }

    private Tbl resultGradeTbl(String title, String grade) {
        TblPr tblPr = new TblPr();
        Tbl tbl = factory.createTbl();
        CTHeight ctHeight = new CTHeight();
        CTShd ctShd = factory.createCTShd();
        CTVerticalJc ctjc = new CTVerticalJc();
        ctjc.setVal(STVerticalJc.CENTER);
        ctShd.setColor("FFFFFF");
        ctShd.setFill("#d1cdcd");
        ctHeight.setHRule(STHeightRule.EXACT);
        JAXBElement<CTHeight> jaxbElement = factory.createCTTrPrBaseTrHeight(ctHeight);
        tbl.setTblPr(tblPr);
        Tr first = factory.createTr();
        Tr second = factory.createTr();
        Tr third = factory.createTr();
        TcPr coloredTcPr = factory.createTcPr();
        coloredTcPr.setVAlign(ctjc);
        coloredTcPr.setShd(ctShd);
        Tc titleTc = factory.createTc();
        Tc gradeTc = factory.createTc();
        TcPr gridSpanTcpr = new TcPr();
        TcPrInner.GridSpan gridSpan = new TcPrInner.GridSpan();

        gridSpanTcpr.setVAlign(ctjc);
        gridSpan.setVal(BigInteger.valueOf(6));
        gridSpanTcpr.setGridSpan(gridSpan);

        addTableCell(factory.createTc(),first, coloredTcPr, "대상", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(titleTc, first, gridSpanTcpr, title, "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(factory.createTc(),second, coloredTcPr,"등급", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(gradeTc, second, gridSpanTcpr, grade, "red", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(factory.createTc(),third, coloredTcPr,"결과", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(factory.createTc(),third, factory.createTcPr(),"1차", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(factory.createTc(),third,factory.createTcPr(), "", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(factory.createTc(),third, factory.createTcPr(),"2차", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(factory.createTc(),third,factory.createTcPr(), "", "black", tblContentFontSize, JcEnumeration.CENTER, true,2000);
        addTableCell(factory.createTc(),third, factory.createTcPr(),"3차", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);
        addTableCell(factory.createTc(),third,factory.createTcPr(), "", "black", tblContentFontSize, JcEnumeration.CENTER, true, 2000);

        tbl.getContent().add(first);
        tbl.getContent().add(second);
        tbl.getContent().add(third);
        addBorders(tbl, "black");
        return tbl;
    }

    private Tbl createControlTbl(String controlNumber) {
        TblPr tblPr = new TblPr();
        Tbl tbl = factory.createTbl();
        tbl.setTblPr(tblPr);
        Tr first = factory.createTr();
        Tr second = factory.createTr();
        addTableCell(factory.createTc(),first, factory.createTcPr(),"대외비", "red", tblContentFontSize, JcEnumeration.CENTER, true, 0);
        addTableCell(factory.createTc(),first, factory.createTcPr(),"수신자 외 열람 불가", "red", tblContentFontSize, JcEnumeration.CENTER, true, 0);
        addTableCell(factory.createTc(),second, factory.createTcPr(),"관리번호", "black", tblContentFontSize, JcEnumeration.CENTER, true, 0);
        addTableCell(factory.createTc(),second,factory.createTcPr(), controlNumber, "black", tblContentFontSize, JcEnumeration.CENTER, true, 0);
        tbl.getContent().add(first);
        tbl.getContent().add(second);
        addBorders(tbl, "orange");
        return tbl;
    }

    private Tbl createMainTbl( List<VItemInfo.Main> data, List<ReportInfo.ReportOptionGroupInfo> reportOptionGroupInfos) {
        TblPr tableProps = new TblPr();
        Tbl table = factory.createTbl();
        table.setTblPr(tableProps);
        var header = createCheckTblTh();
        table.getContent().add(header);
        addBorders(table, "black");

        for (VItemInfo.Main item : data) {
            int groupInfoLength = item.getVItemDetailInfoGroupList().size();
            for (int k = 0; k < groupInfoLength; k++) {
                TcPrInner.VMerge merge = factory.createTcPrInnerVMerge();
                Tc tableCell = factory.createTc();
                TcPr tableCellProperties = new TcPr();
                if ((k == 0) ) {
                    merge.setVal("restart");
                } else {
                    merge.setVal(null);
                }

                CTVerticalJc ctjc = new CTVerticalJc();
                ctjc.setVal(STVerticalJc.CENTER);
                tableCellProperties.setVAlign(ctjc);
                tableCell.setTcPr(tableCellProperties);
                TrPr trpr = new TrPr();
                Tr tableRow1 = factory.createTr();
                tableRow1.setTrPr(trpr);
                tableCellProperties.setVMerge(merge);
                addTableCell(factory.createTc(),tableRow1, tableCellProperties, item.getVCategoryName(), "black", tblContentFontSize, JcEnumeration.CENTER, false, 0);

                addTableCell(factory.createTc(),tableRow1, null, String.format("%d.%d %s",item.getVCategoryCode(),item.getVItemDetailInfoGroupList().get(k).getVGroupCode(),item.getVItemDetailInfoGroupList().get(k).getVGroupName()), "black", tblContentFontSize, JcEnumeration.LEFT, false, 0);
                addTableCell(factory.createTc(),tableRow1, null, item.getVItemDetailInfoGroupList().get(k).getVGroupGrade().getDescription(), item.getVItemDetailInfoGroupList().get(k).getVGroupGrade().getColor(), tblContentFontSize, JcEnumeration.CENTER, true, 0);

                boolean find = false;
                for (ReportInfo.ReportOptionGroupInfo groupInfo : reportOptionGroupInfos) {
                    if(Objects.equals(groupInfo.getVItemDetailGroupInfo().getId(), item.getVItemDetailInfoGroupList().get(k).getId())) {
                        addTableCell(factory.createTc(),tableRow1, null,"취약", "red", tblContentFontSize, JcEnumeration.CENTER, true,0);
                        find = true;
                        break;
                    }
                }
                if(!find) {
                    addTableCell(factory.createTc(),tableRow1, null,"", "black", 30, JcEnumeration.CENTER, false, 0);
                }

                table.getContent().add(tableRow1);
            }
        }
        return table;
    }

    private Tr createCheckTblTh() {
        Tr tr = factory.createTr();
        TcPr tcPr = factory.createTcPr();
        CTShd ctShd = factory.createCTShd();
        ctShd.setColor("FFFFFF");
        ctShd.setFill("#d1cdcd");
        tcPr.setShd(ctShd);
        addTableCell(factory.createTc(), tr, tcPr,"분류", "black", tblContentFontSize, JcEnumeration.CENTER, true,1000);
        addTableCell(factory.createTc(), tr, tcPr,"세부 진단 항목", "black", tblContentFontSize, JcEnumeration.CENTER, true, 4000);
        addTableCell(factory.createTc(), tr, tcPr,"취약점 등급", "black", tblContentFontSize, JcEnumeration.CENTER, true, 1500);
        addTableCell(factory.createTc(), tr, tcPr,"점검 결과", "black", tblContentFontSize, JcEnumeration.CENTER, true, 1500);
        return tr;
    }

    private Tr createFunctionTblTh() {
        Tr tr = factory.createTr();
        TcPr tcPr = factory.createTcPr();
        CTShd ctShd = factory.createCTShd();
        ctShd.setColor("FFFFFF");
        ctShd.setFill("#d1cdcd");
        tcPr.setShd(ctShd);
        addTableCell(factory.createTc(), tr, tcPr,"함수 이름", "black", tblContentFontSize, JcEnumeration.CENTER, true,3000);
        addTableCell(factory.createTc(), tr, tcPr,"함수 패키지", "black", tblContentFontSize, JcEnumeration.CENTER, true, 3000);
        addTableCell(factory.createTc(), tr, tcPr,"함수 설명", "black", tblContentFontSize, JcEnumeration.CENTER, true, 3000);
        return tr;
    }


    /**
     * In this method we add a table cell to the given row with the given
     *  paragraph as content.
     */
    private void addTableCell(Tc tc,
                              Tr tr,
                              TcPr tcpr,
                              String content,
                              String colorName,
                              Integer fontSize ,
                              JcEnumeration align,
                              Boolean bold,
                              Integer width
    ) {
        if (width > 0) {
            TblWidth tblWidth = new TblWidth();
            tblWidth.setW(BigInteger.valueOf(width));
            tblWidth.setType(TblWidth.TYPE_DXA);
            tcpr.setTcW(tblWidth);
        }


        P p = createParaGraph(content, colorName, fontSize, align, bold, 25, 25);
        tc.setTcPr(tcpr);
        tc.getContent().add(p);
        tr.getContent().add(tc);
    }

    /**
     *  In this method we'll add the borders to the table.
     */
    private void addBorders(Tbl table, String color) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor(color);
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }

    public P createUnnumberedList(String text, long deps, Integer idnt, long level) {
        P  p = factory.createP();
        BooleanDefaultTrue boolTrue = new BooleanDefaultTrue();
        PPrBase.NumPr numPr =  factory.createPPrBaseNumPr();
        PPrBase.Ind indent = factory.createPPrBaseInd();
        RPr rpr = factory.createRPr();
        PPr ppr = factory.createPPr();
        ParaRPr paraRPr = factory.createParaRPr();
        R  run = factory.createR();
        boolTrue.setVal(true);
        Text  t = factory.createText();

        t.setValue(text);
        rpr.setB(boolTrue);
        run.getContent().add(t);
        p.getContent().add(run);
        paraRPr.setB(boolTrue);
        // Create and add <w:numPr>
        indent.setRight(BigInteger.valueOf(idnt));
        ppr.setInd(indent);
        ppr.setNumPr(numPr);
        // The <w:ilvl> element
        ppr.setRPr(paraRPr);
        p.setPPr( ppr );


        PPrBase.NumPr.Ilvl ilvlElement = factory.createPPrBaseNumPrIlvl();
        numPr.setIlvl(ilvlElement);
        ilvlElement.setVal(BigInteger.valueOf(level));

        // The <w:numId> element
        PPrBase.NumPr.NumId numIdElement = factory.createPPrBaseNumPrNumId();
        numPr.setNumId(numIdElement);
        numIdElement.setVal(BigInteger.valueOf(deps));

        return p;
    }


    private void createDetail(ReportInfo.ReportOptionGroupInfo reportOptionGroupInfo, WordprocessingMLPackage mlPackage, int i, int length) {

        if ( i == 0) {
            var detailName = createUnnumberedList("상세내용", 1, 100, 0);
            mlPackage.getMainDocumentPart().addObject(detailName);
        }

        var categoryName = createParaGraph(String.format("%d-%d. %s",
                        reportOptionGroupInfo.getVItemDetailGroupInfo().getOrdering(),
                        reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupCode(),
                        reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupName()),
                "black",
                20,
                JcEnumeration.LEFT,
                true,300, 200
        );

        mlPackage.getMainDocumentPart().addObject(categoryName);
        var problem_tag = createTabParaGraph("1) 문제점", true);
        mlPackage.getMainDocumentPart().addObject(problem_tag);
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> {
            var pIssue = createTabParaGraph(reportOptionInfo.getReportVIssue(), false);
            mlPackage.getMainDocumentPart().addObject(pIssue);
            AtomicInteger imageNum = new AtomicInteger(1);
            reportOptionInfo.getReportOptionImageInfoList().forEach(reportOptionImageInfo -> {
                try {
                        int id = (int) (Math.random() * 10000);
                        BinaryPartAbstractImage imgPart = BinaryPartAbstractImage.createImagePart(mlPackage, new File(reportOptionImageInfo.getFilePath()));
                        Inline inline = imgPart.createImageInline(reportOptionImageInfo.getDescription(), "Alt Text", id, id * 2, 8200,  false);
                        var image = addImageToParagraph(inline);
                        var caption = createParaGraph(String.format("%s", reportOptionImageInfo.getCaption()),"black", 16, JcEnumeration.CENTER, false, 100, 30);
                        var desc = createParaGraph(reportOptionImageInfo.getDescription(),"black", 16, JcEnumeration.CENTER, false, 100, 30);
                        mlPackage.getMainDocumentPart().addObject(image);
                        mlPackage.getMainDocumentPart().addObject(desc);
                        mlPackage.getMainDocumentPart().addObject(caption);
                        imageNum.getAndIncrement();
                } catch (Exception e) {
                    log.error(e.toString());
                }
            });

        });

//        var respond_tag = createUnnumberedList("대응방안", reportOptionGroupInfo.getId() + i, 10, 1);
        var respond_tag = createTabParaGraph("2) 대응방안", true);
        mlPackage.getMainDocumentPart().addObject(respond_tag);
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> {
            var response = createTabParaGraph(reportOptionInfo.getReportVResponse(), false);
            mlPackage.getMainDocumentPart().addObject(response);
        });


//        var function_tag = createUnnumberedList("관련함수", reportOptionGroupInfo.getId() + i, 10, 1);
        var function_tag = createTabParaGraph("3) 관련함수", true);
        mlPackage.getMainDocumentPart().addObject(function_tag);
        Tbl functionTbl = factory.createTbl();
        var functionTblHeader= createFunctionTblTh();
        functionTbl.getContent().add(functionTblHeader);

        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo ->{
            if(reportOptionInfo.getReportOptionMethodInfoList().size() > 0) {
                reportOptionInfo.getReportOptionMethodInfoList().forEach(reportOptionMethodInfo -> {
                    var tr = createFunctionTbl(reportOptionMethodInfo);
                    functionTbl.getContent().add(tr);
                });
            }
        });

        addBorders(functionTbl, "black");
        mlPackage.getMainDocumentPart().addObject(functionTbl);

        if (i != length - 1) {
            Br br = factory.createBr();
            br.setType(PAGE);
            mlPackage.getMainDocumentPart().addObject(br);
        }
    }

    private Tr createFunctionTbl(ReportInfo.ReportOptionMethodInfo reportOptionMethodInfo) {
        Tr tr = factory.createTr();
        addTableCell(factory.createTc(), tr ,factory.createTcPr(), reportOptionMethodInfo.getMethodName(), "black", 16, JcEnumeration.LEFT, true, 20);
        addTableCell(factory.createTc(),  tr ,factory.createTcPr(), reportOptionMethodInfo.getMethodPackage(), "black", 16, JcEnumeration.LEFT, true, 20);
        addTableCell(factory.createTc(), tr ,factory.createTcPr(), reportOptionMethodInfo.getMethodDescription(), "black", 16, JcEnumeration.LEFT, true, 20);
        return tr;
    }

    private P addImageToParagraph(Inline inline) {
        CTBorder border = new CTBorder();
        border.setColor("black");
        border.setSz(new BigInteger("10"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);
        PPr ppr = factory.createPPr();
        RPr rpr = factory.createRPr();
        rpr.setBdr(border);
        Jc justification = factory.createJc();
        justification.setVal(JcEnumeration.CENTER);

        ppr.setJc(justification);
        P p = factory.createP();
        R r = factory.createR();
        r.setRPr(rpr);
        Drawing drawing = factory.createDrawing();
        drawing.getAnchorOrInline().add(inline);
        Br br = factory.createBr();
        r.getContent().add(br);
        r.getContent().add(drawing);
        p.getContent().add(ppr);
        p.getContent().add(r);
        return p;
    }


    private WordprocessingMLPackage getTemplate(InputStream template)
            throws Docx4JException {
        return WordprocessingMLPackage
                .load(template);
    }

    private P createTabParaGraph(String content, boolean bold ) {
        PPr pPr = factory.createPPr();
        BooleanDefaultTrue boolTrue = factory.createBooleanDefaultTrue();
        boolTrue.setVal(bold);
        P p = factory.createP();
        R r = factory.createR();
        RPr rPr = factory.createRPr();
        R.Tab rT = factory.createRTab();
        StringBuilder sb = new StringBuilder();
        Br br = factory.createBr();

        for (int tIndex = 0; tIndex < content.length(); tIndex++) {
            sb.append(content.charAt(tIndex));

            if(tIndex == content.length() -1) {
                Text t = factory.createText();
                t.setValue(StringEscapeUtils.unescapeHtml4(sb.toString()));
                r.getContent().add(rT);
                r.getContent().add(t);
                sb.delete(0, sb.length());
            }

            if(content.charAt(tIndex) == '\n') {
                Text t = factory.createText();
                t.setValue(StringEscapeUtils.unescapeHtml4(sb.toString()));
                r.getContent().add(rT);
                r.getContent().add(t);
                r.getContent().add(br);
                sb.delete(0, sb.length());
            }

            if(sb.length() % 47 == 0) {
                Text t = factory.createText();
                t.setValue(StringEscapeUtils.unescapeHtml4(sb.toString()));
                r.getContent().add(rT);
                r.getContent().add(t);
                r.getContent().add(br);
                sb.delete(0, sb.length());
            }
        }

        HpsMeasure size = factory.createHpsMeasure();
        size.setVal(BigInteger.valueOf(20));
        rPr.setB(boolTrue);
        rPr.setSz(size);
        p.setPPr(pPr);
        r.setRPr(rPr);
        p.getContent().add(r);
        return p;
    }

    private P createParaGraph(String content, String colorName, Integer fontSize, JcEnumeration align, boolean bold, int beforeSpace, int afterSpace) {
        PPrBase.Spacing spacing = factory.createPPrBaseSpacing();
        spacing.setLine(BigInteger.valueOf(200));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        PPr ppr = factory.createPPr();
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setSpace("preserve");
        Jc jc = factory.createJc();
        jc.setVal(align);
        ppr.setJc(jc);
        spacing.setBefore(BigInteger.valueOf(beforeSpace));
        spacing.setAfter(BigInteger.valueOf(afterSpace));
        ppr.setSpacing(spacing);
        p.setPPr(ppr);
        t.setValue(content);
        r.getContent().add(t);
        p.getContent().add(r);
        RPr rpr = factory.createRPr();
        Color color = factory.createColor();
        BooleanDefaultTrue boolTrue = new BooleanDefaultTrue();
        ppr.setAutoSpaceDE(boolTrue);
        color.setVal(colorName);
        boolTrue.setVal(bold);
        rpr.setColor(color);
        rpr.setB(boolTrue);
        HpsMeasure size = new HpsMeasure();
        size.setVal(BigInteger.valueOf(fontSize));
        rpr.setSz(size);
        r.setRPr(rpr);
        return p;
    }
}
