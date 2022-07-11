package com.wemakeprice.vms.reportapi.domain.diagnosis;

import lombok.Getter;
import lombok.ToString;

public class DiagnosisTableInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String serviceName;

        public Main(DiagnosisTable diagnosisTable) {
            this.id = diagnosisTable.getId();
            this.serviceName = diagnosisTable.getServiceName();
        }
    }
}
