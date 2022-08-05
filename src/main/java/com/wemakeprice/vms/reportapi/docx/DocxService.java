package com.wemakeprice.vms.reportapi.docx;

import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import java.nio.file.Path;


public interface DocxService {
    Path createReport(ReportInfo.Main report) throws Exception;
}
