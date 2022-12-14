package com.wemakeprice.vms.reportapi.common.utils.wkms.service;

import com.wemakeprice.vms.reportapi.common.utils.wkms.dto.CryptoData;
import com.wemakeprice.vms.reportapi.common.utils.wkms.dto.WkmsData;

public interface WkmsService {
    WkmsData.Value getWkmsData();
    CryptoData.Value getCryptoData();
}
