package com.wemakeprice.vms.reportapi.common.utils.common;

import com.wemakeprice.vms.reportapi.common.utils.crypto.WmpCryptoUtils;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wemakeprice.vms.reportapi.config.CryptoKeyConfig.KEY;
import static com.wemakeprice.vms.reportapi.config.CryptoKeyConfig.IV;

@Slf4j
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


    /**
     * 브라우저 종류에 따라 한글을 인코딩하여 리턴한다.
     * @param filename
     * @return
     * @throws Exception
     */

    public static String getEncodingFilename(String filename, boolean attachmentPrefix) {

        String encodedFilename = new String(filename);

        try {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < filename.length(); i++) {
                    char c = filename.charAt(i);
                    if (c > '~') {
                        sb.append(URLEncoder.encode("" + c, StandardCharsets.UTF_8));
                    } else {
                        sb.append(c);
                    }

                }

                encodedFilename = sb.toString();

            if (attachmentPrefix) {
                return "attachment; filename=" + encodedFilename + ";";
            }
        } catch (Exception e) {
            log.error("Cannot Convert FileName");
        }
        return encodedFilename;
    }

    public static String formatDateYYYYMMDD(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(date);
    }
}
