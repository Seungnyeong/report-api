package com.wemakeprice.vms.reportapi.docx;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.File;

@Getter
@Builder
@ToString
public class FileInfo {
    private final File file;
    private final String fileName;
}
