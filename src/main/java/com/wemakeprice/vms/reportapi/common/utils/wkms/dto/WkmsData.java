package com.wemakeprice.vms.reportapi.common.utils.wkms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class WkmsData {
    private String key;
    private Map<String, String> value;
    private String description;
}