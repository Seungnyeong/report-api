package com.wemakeprice.vms.reportapi.infrastructure.external.docx;

import com.wemakeprice.vms.reportapi.config.FileStorageConfig;
import com.wemakeprice.vms.reportapi.docx.DocxService;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemService;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        P Bempty = createParaGraph(" ", "black", 100, JcEnumeration.LEFT, true, 3000);
        P Sempty = createParaGraph(" ", "black", 100, JcEnumeration.LEFT, true, 1000);
        P title = createParaGraph(reportInfo.getTitle(), "black", 60, JcEnumeration.CENTER, true, 50);
        P titleAppendix = createParaGraph("취약점 점검 결과 보고", "black", 60, JcEnumeration.CENTER, true,50);
        P created = createParaGraph(reportInfo.getCreated().format(DateTimeFormatter.ofPattern("yyyy. MM. dd")), "black", 40, JcEnumeration.CENTER, true,50);
        P teamName = createParaGraph("CERT팀", "black", 40, JcEnumeration.CENTER, true,300);

        var controlTbl =  createControlTbl(reportInfo.getReportControlNumber());

        main.getMainDocumentPart().addObject(controlTbl);
        main.getMainDocumentPart().addObject(Bempty);
        main.getMainDocumentPart().addObject(title);
        main.getMainDocumentPart().addObject(titleAppendix);
        main.getMainDocumentPart().addObject(Bempty);
        main.getMainDocumentPart().addObject(created);
        main.getMainDocumentPart().addObject(teamName);
        main.getMainDocumentPart().addObject(Bempty);
        main.getMainDocumentPart().addObject(paging);

        var resultText = createParaGraph("1. 취약점 점검 결과 등급 ",

                "black",
                20,
                JcEnumeration.LEFT,
                true,25
        );

        var resultTbl = resultGradeTbl(reportInfo.getTitle(), reportInfo.getReportVGrade().getDescription());

        main.getMainDocumentPart().addObject(resultText);
        main.getMainDocumentPart().addObject(resultTbl);
        main.getMainDocumentPart().addObject(Sempty);
        var resultCheckTable = createParaGraph("2. 취약점 점검 결과 요약",

                "black",
                20,
                JcEnumeration.LEFT,
                true,25
        );

        var checkListTbl = createMainTbl(vItemService.retrieveVItemList(), reportInfo.getReportOptionGroupsList());
        main.getMainDocumentPart().addObject(resultCheckTable);
        main.getMainDocumentPart().addObject(checkListTbl);

        P check = createParaGraph("※ 체크리스트 기준", "black", 14, JcEnumeration.LEFT, true, 25);
        P owasp = createParaGraph("OWASP(Open Web Application Security Project) Top 10 – www.owasp.org", "black", 12, JcEnumeration.LEFT, false, 25);
        P nist = createParaGraph("NIST(National Institute of standards and Technology) Web Application Check List – c/src.nist.gov", "black", 12, JcEnumeration.LEFT, false, 25);
        main.getMainDocumentPart().getContent().add(check);
        main.getMainDocumentPart().getContent().add(owasp);
        main.getMainDocumentPart().getContent().add(nist);
        main.getMainDocumentPart().addObject(paging);

        int length = reportInfo.getReportOptionGroupsList().size();
        for (int i = 0; i < reportInfo.getReportOptionGroupsList().size(); i++) {
            createDetail(reportInfo.getReportOptionGroupsList().get(i), main, i, length);
        }
        UUID uuid = UUID.randomUUID();
        Path targetLocation = this.reportStorageLocation.resolve(uuid + "." + DOC_TYPE);
        main.save(new File(targetLocation.toAbsolutePath().toString()));
        return targetLocation.toAbsolutePath();
    }

    private Tbl resultGradeTbl(String title, String grade) {
        TblPr tblPr = new TblPr();
        Tbl tbl = factory.createTbl();
        tbl.setTblPr(tblPr);
        Tr first = factory.createTr();
        Tr second = factory.createTr();
        Tr third = factory.createTr();
        TcPr tcpr = new TcPr();
        TcPrInner.HMerge merge = new TcPrInner.HMerge();
        merge.setVal(null);
        tcpr.setHMerge(merge);


        addTableCell(factory.createTc(),first, "대상", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        Tc titleTc = factory.createTc();
        titleTc.setTcPr(tcpr);

        addTableCell(titleTc, first, title, "black", tblContentFontSize, JcEnumeration.CENTER, true);

        for (int i = 0; i < 5; i++) {
            Tc tc = factory.createTc();
            tc.setTcPr(tcpr);
            addTableCell(tc,first, " ", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        }

//        addTableCell(factory.createTc(),first, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);
//        addTableCell(factory.createTc(),first, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);
//        addTableCell(factory.createTc(),first, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);
//        addTableCell(factory.createTc(),first, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),second, "등급", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),second, grade, "red", tblContentFontSize, JcEnumeration.CENTER, true);

        for (int i = 0; i < 5; i++) {
            Tc tc = factory.createTc();
            tc.setTcPr(tcpr);
            addTableCell(tc, second, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        }

        addTableCell(factory.createTc(),third, "결과", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),third, "1차", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),third, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),third, "2차", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),third, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),third, "3차", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),third, "", "black", tblContentFontSize, JcEnumeration.CENTER, true);

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
        addTableCell(factory.createTc(),first, "대외비", "red", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),first, "수신자 외 열람 불가", "red", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),second, "관리번호", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(),second, controlNumber, "black", tblContentFontSize, JcEnumeration.CENTER, true);
        tbl.getContent().add(first);
        tbl.getContent().add(second);
        addBorders(tbl, "orange");
        return tbl;
    }

    private Tbl createMainTbl( List<VItemInfo.Main> data, List<ReportInfo.ReportOptionGroupInfo> reportOptionGroupInfos) {
        TblPr tableProps = new TblPr();
        Tbl table = factory.createTbl();
        CTHeight ctHeight = new CTHeight();
        ctHeight.setHRule(STHeightRule.EXACT);
        JAXBElement<CTHeight> jaxbElement = factory.createCTTrPrBaseTrHeight(ctHeight);
        table.setTblPr(tableProps);
        var header = createTblTh();
        table.getContent().add(header);
        addBorders(table, "black");

        for (VItemInfo.Main item : data) {
            int groupInfoLength = item.getVItemDetailInfoGroupList().size();
            for (int k = 0; k < groupInfoLength; k++) {
                Tc tableCell = factory.createTc();
                TcPr tableCellProperties = new TcPr();
                TcPrInner.VMerge merge = new TcPrInner.VMerge();
                P content = createParaGraph(item.getVCategoryName(), "black", tblContentFontSize, JcEnumeration.CENTER, false, 25);

                if (groupInfoLength - 1 == k) {
                    merge.setVal("restart");
                } else {
                    merge.setVal(null);
                }

                CTVerticalJc ctjc = new CTVerticalJc();
                ctjc.setVal(STVerticalJc.CENTER);
                tableCellProperties.setVAlign(ctjc);
                tableCell.setTcPr(tableCellProperties);
                tableCellProperties.setVMerge(merge);
                tableCell.getContent().add(content);

                TrPr trpr = new TrPr();
                trpr.getCnfStyleOrDivIdOrGridBefore().add(jaxbElement);
                Tr tableRow1 = factory.createTr();
                tableRow1.setTrPr(trpr);
                tableRow1.getContent().add(tableCell);

                addTableCell(factory.createTc(),tableRow1, String.format("%d.%d %s",item.getVCategoryCode(),item.getVItemDetailInfoGroupList().get(k).getVGroupCode(),item.getVItemDetailInfoGroupList().get(k).getVGroupName()), "black", tblContentFontSize, JcEnumeration.LEFT, false);
                addTableCell(factory.createTc(),tableRow1, item.getVItemDetailInfoGroupList().get(k).getVGroupGrade().getDescription(), item.getVItemDetailInfoGroupList().get(k).getVGroupGrade().getColor(), tblContentFontSize, JcEnumeration.CENTER, true);
                boolean find = false;
                for (ReportInfo.ReportOptionGroupInfo groupInfo : reportOptionGroupInfos) {
                    if(Objects.equals(groupInfo.getVItemDetailGroupInfo().getId(), item.getVItemDetailInfoGroupList().get(k).getId())) {
                        addTableCell(factory.createTc(),tableRow1, "취약", "red", tblContentFontSize, JcEnumeration.CENTER, true);
                        find = true;
                        break;
                    }
                }
                if(!find) {
                    addTableCell(factory.createTc(),tableRow1, "", "black", 30, JcEnumeration.CENTER, false);
                }

                table.getContent().add(tableRow1);
            }
        }
        return table;
    }

    private Tr createTblTh() {
        Tr tr = factory.createTr();
        addTableCell(factory.createTc(),tr, "분류", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(), tr, "세부 진단 항목", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(), tr,"취약점 등급", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        addTableCell(factory.createTc(), tr, "점검 결과", "black", tblContentFontSize, JcEnumeration.CENTER, true);
        return tr;
    }

    /**
     *  We create a table cell and then a table cell properties object.
     *  We also create a vertical merge object. If the merge value is not null,
     *  we set it on the object. Then we add the merge object to the table cell
     *  properties and add the properties to the table cell. Finally we set the
     *  content in the table cell and add the cell to the row.
     *
     *  If the merge value is 'restart', a new row is started. If it is null, we
     *  continue with the previous row, thus merging the cells.
     */
    private void addMergedCell(Tr row, String content, String vMergeVal, WordprocessingMLPackage mlPackage) {
        Tc tableCell = factory.createTc();
        TcPr tableCellProperties = new TcPr();

        TcPrInner.VMerge merge = new TcPrInner.VMerge();
        if(vMergeVal != null){
            merge.setVal(vMergeVal);
        }

        tableCellProperties.setVMerge(merge);

        tableCell.setTcPr(tableCellProperties);
        if(content != null) {
            tableCell.getContent().add(
                    mlPackage.getMainDocumentPart().
                            createParagraphOfText(content));
        }

        row.getContent().add(tableCell);
    }

    /**
     * In this method we add a table cell to the given row with the given
     *  paragraph as content.
     */
    private void addTableCell(Tc tc, Tr tr, String content, String colorName, Integer fontSize , JcEnumeration align, Boolean bold) {
        P p = createParaGraph(content, colorName, fontSize, align, bold, 25);
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


    private void createDetail(ReportInfo.ReportOptionGroupInfo reportOptionGroupInfo, WordprocessingMLPackage mlPackage, int i, int length) {

//        mlPackage.getMainDocumentPart().addStyledParagraphOfText("a0",  String.format("%d-%d. %s",
//                reportOptionGroupInfo.getVItemDetailGroupInfo().getOrdering(),
//                reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupCode(),
//                reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupName())
//        );
        var detailName = createParaGraph("3. 상세내용",
                "black",
                20,
                JcEnumeration.LEFT,
                true,25
        );

        mlPackage.getMainDocumentPart().addObject(detailName);

        var categoryName = createParaGraph(String.format("%d-%d. %s",
                        reportOptionGroupInfo.getVItemDetailGroupInfo().getOrdering(),
                        reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupCode(),
                        reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupName()),
                "black",
                20,
                JcEnumeration.LEFT,
                true,25
        );

        mlPackage.getMainDocumentPart().addObject(categoryName);


        mlPackage.getMainDocumentPart().addStyledParagraphOfText("a0", "문제점");
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> {
            mlPackage.getMainDocumentPart().addStyledParagraphOfText("3", reportOptionInfo.getReportVIssue());
            reportOptionInfo.getReportOptionImageInfoList().forEach(reportOptionImageInfo -> {

                try {
                        int id = (int) (Math.random() * 10000);
                        BinaryPartAbstractImage imgPart = BinaryPartAbstractImage.createImagePart(mlPackage, new File(reportOptionImageInfo.getFilePath()));
                        Inline inline = imgPart.createImageInline(  "Baeldung Image (filename hint)", "Alt Text", id, id * 2, false);
                        var p = addImageToParagraph(inline, reportOptionImageInfo.getCaption());
                        mlPackage.getMainDocumentPart().addObject(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });

        mlPackage.getMainDocumentPart().addStyledParagraphOfText("a0", "대응방안");
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> mlPackage.getMainDocumentPart().addStyledParagraphOfText("3", reportOptionInfo.getReportVResponse()));

        mlPackage.getMainDocumentPart().addStyledParagraphOfText("a0", "관련함수");
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> reportOptionInfo.getReportOptionMethodInfoList().forEach(reportOptionMethodInfo -> {
            mlPackage.getMainDocumentPart().addStyledParagraphOfText("3", reportOptionMethodInfo.getMethodName());
            mlPackage.getMainDocumentPart().addStyledParagraphOfText("3", reportOptionMethodInfo.getMethodPackage());
            mlPackage.getMainDocumentPart().addStyledParagraphOfText("3", reportOptionMethodInfo.getMethodDescription());
        }));

        if (i != length - 1) {
            Br br = factory.createBr();
            br.setType(PAGE);
            mlPackage.getMainDocumentPart().addObject(br);
        }
    }

    private P addImageToParagraph(Inline inline, String caption) {
        PPr paragraphProperties = factory.createPPr();
        Jc justification = factory.createJc();
        justification.setVal(JcEnumeration.CENTER);
        paragraphProperties.setJc(justification);
        P p = factory.createP();
        R r = factory.createR();
        Drawing drawing = factory.createDrawing();
        drawing.getAnchorOrInline().add(inline);
        Text text = factory.createText();
        var textWrapped = factory
                .createRT(text);
        r.getContent().add(textWrapped);
        text.setValue(String.format("[그림%d].%s", 1, caption));
        text.setSpace("preserve");
        r.getContent().add(drawing);
        p.getContent().add(paragraphProperties);
        p.getContent().add(r);
        return p;
    }


    private void searchAndReplace(List<Object> texts, Map<String, String> values){

        // -- scan all expressions
        // Will later contain all the expressions used though not used at the moment
        List<String> els = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        int PASS = 0;
        int PREPARE = 1;
        int READ = 2;
        int mode = PASS;

        // to nullify
        List<int[]> toNullify = new ArrayList<int[]>();
        int[] currentNullifyProps = new int[4];

        // Do scan of els and immediately insert value
        for(int i = 0; i<texts.size(); i++){
            Object text = texts.get(i);
            Text textElement = (Text) text;
            StringBuilder newVal = new StringBuilder();
            String v = textElement.getValue();
//          System.out.println("text: "+v);
            StringBuilder textSofar = new StringBuilder();
            int extra = 0;
            char[] vchars = v.toCharArray();
            for(int col = 0; col<vchars.length; col++){
                char c = vchars[col];
                textSofar.append(c);
                switch(c){
                    case '$': {
                        mode=PREPARE;
                        sb.append(c);
//                  extra = 0;
                    } break;
                    case '{': {
                        if(mode==PREPARE){
                            sb.append(c);
                            mode=READ;
                            currentNullifyProps[0]=i;
                            currentNullifyProps[1]=col+extra-1;
                            System.out.println("extra-- "+extra);
                        } else {
                            if(mode==READ){
                                // consecutive opening curl found. just read it
                                // but supposedly throw error
                                sb = new StringBuilder();
                                mode=PASS;
                            }
                        }
                    } break;
                    case '}': {
                        if(mode==READ){
                            mode=PASS;
                            sb.append(c);
                            els.add(sb.toString());
                            newVal.append(textSofar).append(null == values.get(sb.toString()) ? sb.toString() : values.get(sb.toString()));
                            textSofar = new StringBuilder();
                            currentNullifyProps[2]=i;
                            currentNullifyProps[3]=col+extra;
                            toNullify.add(currentNullifyProps);
                            currentNullifyProps = new int[4];
                            extra += sb.toString().length();
                            sb = new StringBuilder();
                        } else if(mode==PREPARE){
                            mode = PASS;
                            sb = new StringBuilder();
                        }
                    }
                    default: {
                        if(mode==READ) sb.append(c);
                        else if(mode==PREPARE){
                            mode=PASS;
                            sb = new StringBuilder();
                        }
                    }
                }
            }
            newVal.append(textSofar.toString());
            textElement.setValue(newVal.toString());
        }

        // remove original expressions
        if(toNullify.size()>0)
            for(int i = 0; i<texts.size(); i++){
                if(toNullify.size()==0) break;
                currentNullifyProps = toNullify.get(0);
                Object text = texts.get(i);
                Text textElement = (Text) text;
                String v = textElement.getValue();
                StringBuilder nvalSB = new StringBuilder();
                char[] textChars = v.toCharArray();
                for(int j = 0; j<textChars.length; j++){
                    char c = textChars[j];
                    if(null==currentNullifyProps) {
                        nvalSB.append(c);
                        continue;
                    }
                    // I know 100000 is too much!!! And so what???
                    int floor = currentNullifyProps[0]*100000+currentNullifyProps[1];
                    int ceil = currentNullifyProps[2]*100000+currentNullifyProps[3];
                    int head = i*100000+j;
                    if(!(head>=floor && head<=ceil)){
                        nvalSB.append(c);
                    }

                    if(j>currentNullifyProps[3] && i>=currentNullifyProps[2]){
                        toNullify.remove(0);
                        if(toNullify.size()==0) {
                            currentNullifyProps = null;
                            continue;
                        }
                        currentNullifyProps = toNullify.get(0);
                    }
                }
                textElement.setValue(nvalSB.toString());
            }
    }

    private WordprocessingMLPackage getTemplate(InputStream template)
            throws Docx4JException, FileNotFoundException {
        return WordprocessingMLPackage
                .load(template);
    }

    private static List<Object> getAllElementFromObject(Object obj,
                                                        Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>) obj).getValue();

        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }

    private P createParaGraph(String content, String colorName, Integer fontSize, JcEnumeration align, boolean bold, int beforeSpace) {
        PPrBase.Spacing spacing = factory.createPPrBaseSpacing();
        spacing.setLine(BigInteger.valueOf(150));
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
        spacing.setAfter(BigInteger.valueOf(25));
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
