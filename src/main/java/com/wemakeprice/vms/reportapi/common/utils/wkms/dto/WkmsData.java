package com.wemakeprice.vms.reportapi.common.utils.wkms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WkmsData {
    private String key;
    private Value value;
    private String description;


    @Getter
    @Setter
    public static class Value {
        String username;
        String password;
    }
}