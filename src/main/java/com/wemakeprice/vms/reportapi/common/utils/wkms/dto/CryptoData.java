package com.wemakeprice.vms.reportapi.common.utils.wkms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CryptoData {
    private String key;
    private Value value;
    private String description;

    @Getter
    @Builder
    public static class Value {
        String ETC_KEY;
        String ETC_KEY_IV;
    }
}
