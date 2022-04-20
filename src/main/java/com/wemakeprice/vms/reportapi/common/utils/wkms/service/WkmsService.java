package com.wemakeprice.vms.reportapi.common.utils.wkms.service;

import java.util.Map;

public interface WkmsService {
    int CONNECTION_TIMEOUT = 5000;
    Map<String, String> getWkmsData();
}
