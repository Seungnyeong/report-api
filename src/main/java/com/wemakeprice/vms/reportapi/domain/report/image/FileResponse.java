package com.wemakeprice.vms.reportapi.domain.report.image;


import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Builder
@Getter
public class FileResponse {
    private String fileName;
    private String extension;
    private Path filePath;
    private String fileUrl;
}