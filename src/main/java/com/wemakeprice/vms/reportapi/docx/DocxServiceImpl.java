package com.wemakeprice.vms.reportapi.docx;

import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class DocxServiceImpl implements DocxService {

    @Override
    public void createReport(ReportInfo.Main reportInfo) throws IOException, Docx4JException {

        ClassPathResource resource = new ClassPathResource("report_template/header.docx");

        boolean save = false;
        var main = getTemplate("/Users/sn/workspace/vms-report-api/src/main/resources/"+resource.getPath());

        StyleDefinitionsPart sdp = main.getMainDocumentPart().getStyleDefinitionsPart();
        Styles templateStyle = sdp.getJaxbElement();



        List<Object> texts = getAllElementFromObject(main.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<>(){
            {
                this.put("${controlnumber}", reportInfo.getReportControlNumber());
                this.put("${title}", reportInfo.getTitle());
                this.put("${created}", reportInfo.getCreated().toString());
                this.put("${grade}", reportInfo.getReportVGrade().toString());
                this.put("${possibility}", reportInfo.getReportVPossibility().toString());
                this.put("${review}", reportInfo.getGeneralReview());
                this.put("${10}", "취약");
            }
        });


        reportInfo.getReportOptionGroupsList().forEach(reportOptionGroupInfo -> {
            try {
                var detail =  createDetail(reportOptionGroupInfo);
                getBody(main).getContent().addAll(detail);
            } catch (Exception ignored) {

            }
        });

        writeDocxToStream(main, "/Users/sn/workspace/vms-report-api/src/main/resources/abc.docx");
    }


    private List<Object> createDetail(ReportInfo.ReportOptionGroupInfo reportOptionGroupInfo) throws Exception {
        ClassPathResource resource = new ClassPathResource(("report_template/detail.docx"));
        var template = getTemplate("/Users/sn/workspace/vms-report-api/src/main/resources/"+resource.getPath());

        MainDocumentPart mainDocumentPart = template.getMainDocumentPart();
        ObjectFactory factory = new ObjectFactory();
        List<Object> texts = getAllElementFromObject(mainDocumentPart, Text.class);

        searchAndReplace(texts, new HashMap<>(){
            {
                this.put("${ordering}", String.valueOf(reportOptionGroupInfo.getVItemDetailGroupInfo().getOrdering()));
                this.put("${groupname}", String.valueOf(reportOptionGroupInfo.getVItemDetailGroupInfo().getVGroupName()));
            }
        });

        mainDocumentPart.addStyledParagraphOfText("a0", "문제점");
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> {
            mainDocumentPart.addStyledParagraphOfText("af8", reportOptionInfo.getReportVIssue());

            reportOptionInfo.getReportOptionImageInfoList().forEach(reportOptionImageInfo -> {

                File file = new File(reportOptionImageInfo.getFilePath());


                try {
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    BinaryPartAbstractImage imagepart = BinaryPartAbstractImage.createImagePart(template, fileContent);
                    Inline inline = imagepart.createImageInline(  "Baeldung Image (filename hint)", "Alt Text", 1, 2, false);
                    P Imageparagraph = addImageToParagraph(inline);
                    mainDocumentPart.getContent().add(Imageparagraph);
                    mainDocumentPart.addStyledParagraphOfText("12", reportOptionImageInfo.getCaption());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });

        mainDocumentPart.addStyledParagraphOfText("a0", "대응방안");
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> {
            mainDocumentPart.addStyledParagraphOfText("3", reportOptionInfo.getReportVResponse());

        });

        mainDocumentPart.addStyledParagraphOfText("a0", "관련함수");
        reportOptionGroupInfo.getReportOptionInfoList().forEach(reportOptionInfo -> {
            reportOptionInfo.getReportOptionMethodInfoList().forEach(reportOptionMethodInfo -> {
                mainDocumentPart.addStyledParagraphOfText("af2", reportOptionMethodInfo.getMethodName());
                mainDocumentPart.addStyledParagraphOfText("af2", reportOptionMethodInfo.getMethodPackage());
                mainDocumentPart.addStyledParagraphOfText("af2", reportOptionMethodInfo.getMethodDescription());
            });
        });

        Br br = factory.createBr();
        br.setType(org.docx4j.wml.STBrType.PAGE);

        mainDocumentPart.addObject(br);
        return getBody(template).getContent();
    }

    private static P addImageToParagraph(Inline inline) {
        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        Drawing drawing = factory.createDrawing();
        r.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
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
            String newVal = "";
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
                            newVal +=textSofar.toString()
                                    +(null==values.get(sb.toString())?sb.toString():values.get(sb.toString()));
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
            newVal +=textSofar.toString();
            textElement.setValue(newVal);
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

    private void replacePlaceholder(WordprocessingMLPackage template,
                                    String name, String placeholder) {
        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {
                textElement.setValue(name);
            }
        }
    }

    private void writeDocxToStream(WordprocessingMLPackage template,
                                   String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }

    private Body getBody( WordprocessingMLPackage wordMLPackage ) {
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)documentPart.getJaxbElement();
        return  wmlDocumentEl.getBody();
    }
}
