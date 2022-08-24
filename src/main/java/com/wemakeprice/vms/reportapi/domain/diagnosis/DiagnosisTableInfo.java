package com.wemakeprice.vms.reportapi.domain.diagnosis;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

public class DiagnosisTableInfo {

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Main {
        private final Long id;
        private final String serviceName;

        public Main(DiagnosisTable diagnosisTable) {
            this.id = diagnosisTable.getId();
            this.serviceName = diagnosisTable.getServiceName();
        }
    }
}
