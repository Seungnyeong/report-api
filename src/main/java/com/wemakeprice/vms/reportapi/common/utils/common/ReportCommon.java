package com.wemakeprice.vms.reportapi.common.utils.common;

import com.wemakeprice.vms.reportapi.common.utils.crypto.WmpCryptoUtils;

import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;

import static com.wemakeprice.vms.reportapi.config.CryptoKeyConfig.KEY;
import static com.wemakeprice.vms.reportapi.config.CryptoKeyConfig.IV;

public class ReportCommon {

    public static String getTempPassword(int length) {
        int index = 0;
        char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z' };

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            index = (int) (charArr.length * Math.random());
            sb.append(charArr[index]);
        }

        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
        return WmpCryptoUtils.encrypt(sb.toString(), keySpec, IV.substring(0,16).getBytes(StandardCharsets.UTF_8));
    }
}
