package com.wemakeprice.vms.reportapi.domain.diagnosis;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class DiagnosisTableCommand {

    @Getter
    @Builder
    @ToString
    public static class DiagnosisId {
        private final String serviceName;
        private final Long id;
    }
}
