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


@Service
@Slf4j
public class DocxServiceImpl implements DocxService {

    private final ObjectFactory factory = new ObjectFactory();
    private final VItemService vItemService;
    private final Path reportStorageLocation;
    private static final String DOC_TYPE = "docx";
    private final String TEMPLATE_FILE;

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
        var main = getTemplate(resource.getURI().getPath());

        List<Object> texts = getAllElementFromObject(main.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<>(){
            {
                this.put("${controlnumber}", reportInfo.getReportControlNumber());
                this.put("${title}", reportInfo.getTitle());
                this.put("${created}", reportInfo.getCreated().toString());
                this.put("${grade}", reportInfo.getReportVGrade().toString());
                this.put("${possibility}", reportInfo.getReportVPossibility().toString());
                this.put("${review}", reportInfo.getGeneralReview());
            }
        });

        var tbl = createMainTbl(main, vItemService.retrieveVItemList(), reportInfo.getReportOptionGroupsList());
        main.getMainDocumentPart().addObject(tbl);

        Br br = factory.createBr();
        br.setType(org.docx4j.wml.STBrType.PAGE);
        main.getMainDocumentPart().addObject(br);

        int length = reportInfo.getReportOptionGroupsList().size();
        for (int i = 0; i < reportInfo.getReportOptionGroupsList().size(); i++) {
            createDetail(reportInfo.getReportOptionGroupsList().get(i), main, i, length);
        }
        UUID uuid = UUID.randomUUID();
        Path targetLocation = this.reportStorageLocation.resolve(uuid + "." + DOC_TYPE);
        main.save(new File(targetLocation.toAbsolutePath().toString()));
        return targetLocation.toAbsolutePath();
    }


    private Tbl createMainTbl(WordprocessingMLPackage mlPackage, List<VItemInfo.Main> data, List<ReportInfo.ReportOptionGroupInfo> reportOptionGroupInfos) {

        Tbl table = factory.createTbl();
        createTblTh(table, mlPackage);
        addBorders(table);
        for (VItemInfo.Main item : data) {
            int groupInfoLength = item.getVItemDetailInfoGroupList().size();
            for (int k = 0; k < groupInfoLength; k++) {
                Tc tableCell = factory.createTc();
                TcPr tableCellProperties = new TcPr();
                TcPrInner.VMerge merge = new TcPrInner.VMerge();
                tableCell.getContent().add(
                        mlPackage.getMainDocumentPart().
                                createParagraphOfText(item.getVCategoryName()));
                if (groupInfoLength - 1 == k) {
                    merge.setVal("restart");
                } else {
                    merge.setVal(null);
                }
                tableCellProperties.setVMerge(merge);
                tableCell.setTcPr(tableCellProperties);
                Tr tableRow1 = factory.createTr();
                tableRow1.getContent().add(tableCell);
                addTableCell(tableRow1, item.getVItemDetailInfoGroupList().get(k).getVGroupName(), mlPackage);
                addTableCell(tableRow1, item.getVItemDetailInfoGroupList().get(k).getVGroupGrade().name(), mlPackage);
                boolean find = false;
                for (ReportInfo.ReportOptionGroupInfo groupInfo : reportOptionGroupInfos) {
                    if(Objects.equals(groupInfo.getVItemDetailGroupInfo().getId(), item.getVItemDetailInfoGroupList().get(k).getId())) {
                        addTableCell(tableRow1, "취약", mlPackage);
                        find = true;
                        break;
                    }
                }
                if(!find) {
                    addTableCell(tableRow1, "", mlPackage);
                }

                table.getContent().add(tableRow1);
            }
        }
        return table;
    }

    private void createTblTh(Tbl tbl, WordprocessingMLPackage mlPackage) {
        Tr tr = factory.createTr();
        addTableCell(tr, "분류", mlPackage);
        addTableCell(tr, "세부 진단 항목", mlPackage);
        addTableCell(tr, "취약점 등급", mlPackage);
        addTableCell(tr, "점검 결과", mlPackage);
        tbl.getContent().add(tr);
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
    private void addTableCell(Tr tr, String content, WordprocessingMLPackage mlPackage) {
        Tc tc1 = factory.createTc();
        tc1.getContent().add(
                mlPackage.getMainDocumentPart().createParagraphOfText(
                        content));
        tr.getContent().add(tc1);
    }

    /**
     *  In this method we'll add the borders to the table.
     */
    private void addBorders(Tbl table) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");
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

        mlPackage.getMainDocumentPart().addStyledParagraphOfText("a0",  String.format("%d-%d. %s",
                reportOptionGroupInfo.getVItemDetailGroupInfo().getOrdering(),
                reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupCode(),
                reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupName())
        );

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
            br.setType(org.docx4j.wml.STBrType.PAGE);
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

    private WordprocessingMLPackage getTemplate(String name)
            throws Docx4JException, FileNotFoundException {
        return WordprocessingMLPackage
                .load(new FileInputStream(name));
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
}
