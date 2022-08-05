package com.wemakeprice.vms.reportapi.common.utils.wkms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CryptoResponse<T> {
    private List<T> data;
}
