package com.wemakeprice.vms.reportapi.common.utils.crypto;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 위메프에서 사용하는 암/복호화 유틸.
 */
public class WmpCryptoUtils {
    /**
     * initialVector size
     */
    private static final Integer IV_SIZE = 16;

    /**
     * sha1 암호화.
     *
     * @param data 암호화할 데이터.
     */
    public static String sha1(String data) {
        Assert.notNull(data, "data is null!");
        return DigestUtils.sha1Hex(data);
    }

    /**
     * 위메프 신규 시스템의 암호화 처리방식
     * (AES-256, CBC, PKCS5Padding, encodeHexString)
     * Key 길이가 256이므로 알고리즘은 AES-256이 됨
     * IV는 랜덤하게 생성하여 암호화된 문자열 앞에 붙임
     *
     * @param plainString 평문 문자열
     * @param key         SecretKeySpec 객체
     * @param ivBytes     initialVector 값으로 사용할 byte[]
     * @return encryptResultString 암호화된 문자열
     */
    public static String encrypt(String plainString, SecretKeySpec key, byte[] ivBytes) {
        return doEncrypt(plainString, key, ivBytes);
    }

    /**
     * 위메프 신규 시스템의 암호화 처리방식
     * (AES-256, CBC, PKCS5Padding, encodeHexString)
     * Key 길이가 256이므로 알고리즘은 AES-256이 됨
     * IV는 랜덤하게 생성하여 암호화된 문자열 앞에 붙임
     * 이니셜 벡터에 의해 결과 값이 랜덤으로 나옴.
     *
     * @param plainString 평문 문자열
     * @param key         SecretKeySpec 객체
     * @return encryptResultString 암호화된 문자열
     */
    public static String encrypt(String plainString, SecretKeySpec key) {
        return doEncrypt(plainString, key, null);
    }

    /**
     * 위메프 신규 시스템의 복호화 처리방식
     * (AES-256, CBC, PKCS5Padding, decodeHex
     * Key 길이가 256이므로 알고리즘은 AES-256이 됨
     * IV는 암호화된 문자열에서 추출하고 나머지 암호화된 문자열을 복호화
     *
     * @param encryptString 암호화된 문자열
     * @return decryptResultString 복호화된 평문 문자열
     */
    public static String decrypt(String encryptString, SecretKeySpec key) {
        return doDecrypt(encryptString, key, null);
    }

    /**
     * 위메프 신규 시스템의 복호화 처리방식
     * (AES-256, CBC, PKCS5Padding, decodeHex
     * Key 길이가 256이므로 알고리즘은 AES-256이 됨
     * IV는 암호화된 문자열에서 추출하고 나머지 암호화된 문자열을 복호화
     *
     * @param encryptString 암호화된 문자열
     * @return decryptResultString 복호화된 평문 문자열
     */
    public static String decrypt(String encryptString, SecretKeySpec key, byte[] ivBytes) {
        return doDecrypt(encryptString, key, ivBytes);
    }

    /**
     * 위메프 레거시 시스템의 회원정보(이메일, 전화번호) 암호화 처리방식
     * (AES-128, ECB, NoPadding, bin2hex)
     *
     * @param plainString 암호화할 평문 문자열
     * @return encryptText 암호화된 문자열 반환
     * @throws Exception - UnsupportedEncodingException, NoSuchPaddingException,
     *                   NoSuchAlgorithmException, InvalidAlgorithmParameterException,
     *                   InvalidKeyException, BadPaddingException, IllegalBlockSizeException
     */
    public static String encryptAesEcbPadding(String plainString, String infoKey) {
        Assert.notNull(plainString, "The plainString must not be null!");
        Assert.notNull(infoKey, "The infoKey must not be null!");
        try {
            byte[] key = infoKey.getBytes(CharEncoding.UTF_8);
            byte[] text = plainString.getBytes(CharEncoding.UTF_8);

            Cipher dcipher = Cipher.getInstance("AES/ECB/NoPadding");
            dcipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));

            // mcyrpt_generic용 padding 추가
            if (text.length % 16 != 0) {
                text = Arrays.copyOf(text, (text.length / 16 + 1) * 16);
            }

            String encryptText = Hex.encodeHexString(dcipher.doFinal(text));
            encryptText = encryptText.toLowerCase();

            return encryptText;
        } catch (Exception ex) {
            throw new RuntimeException("Encrypt UserInfo Exception!", ex);
        }
    }

    /**
     * 위메프 레거시 시스템의 회원정보(이메일, 전화번호) 복호화 처리방식
     * (AES-128, ECB, NoPadding, hex2bin)
     *
     * @param encryptString 복호화할 암호화된 문자열
     * @return plainText 복호화된 문자열 반환
     * @throws Exception - UnsupportedEncodingException, NoSuchPaddingException,
     *                   NoSuchAlgorithmException, InvalidAlgorithmParameterException,
     *                   InvalidKeyException, BadPaddingException, IllegalBlockSizeException
     */
    public static String decryptAesEcbPadding(String encryptString, String infoKey) {
        Assert.notNull(encryptString, "The encryptString must not be null!");
        Assert.notNull(infoKey, "The infoKey must not be null!");
        try {
            byte[] key = infoKey.getBytes(CharEncoding.UTF_8);

            Cipher dcipher = Cipher.getInstance("AES/ECB/NoPadding");
            dcipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] clearbyte = dcipher.doFinal(Hex.decodeHex(encryptString.toCharArray()));
            String plainText = new String(clearbyte).trim();

            return plainText;
        } catch (Exception ex) {
            throw new RuntimeException("Decrypt UserInfo Exception!", ex);
        }
    }

    /**
     * 위메프 레거시 시스템의 계좌번호 암호화 처리 방식이다.
     * ECB mode 이며, 수동으로 padding 값을 plain text 에 append 한 후, no padding 으로 암호화 한다.
     *
     * @param bankAccount 암호화 할 계좌번호.
     * @return 암호화 된 문자열을 반환.
     * @deprecated 레거시 용도로만 사용 권장.
     */
    @Deprecated
    public static String encryptAesEcb(String bankAccount, SecretKeySpec key) {
        Assert.notNull(bankAccount, "The bankAccount must not be null!");
        Assert.notNull(key, "The key must not be null!");
        try {
            String paddedPlainText = padding(bankAccount);
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(paddedPlainText.getBytes());
            return Hex.encodeHexString(encryptedBytes);
        } catch (Exception ex) {
            throw new RuntimeException("Encrypt BankAccount Exception!", ex);
        }
    }

    /**
     * 위메프 레거시 시스템의 계좌번호 복호화 처리 방식이다.
     * ECB mode 이며, 복호화 한 후, trim 처리 하여 padding 값을 제거 한다.
     *
     * @param encryptedBankAccount 복호화 할 암호화된 계좌번호.
     * @return 복호화 된 문자열을 반환.
     * @deprecated 레거시 용도로만 사용 권장.
     */
    @Deprecated
    public static String decryptAesEcb(String encryptedBankAccount, SecretKeySpec key) {
        Assert.notNull(encryptedBankAccount, "The encryptedBankAccount must not be null!");
        Assert.notNull(key, "The key must not be null!");
        try {
            byte[] encryptedBytes = Hex.decodeHex(encryptedBankAccount.toCharArray());
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String plainText = new String(decryptedBytes).trim();
            return plainText;
        } catch (Exception ex) {
            throw new RuntimeException("Decrypt BankAccount Exception!", ex);
        }
    }

    /**
     * 위메프 레거시 시스템의 쿠키 복호화 처리 방식이다.
     * (AES-128, CBC, NoPadding, hex2bin)
     *
     * @param encrypted 복호화 할 암호화된 쿠키정보.
     * @return 복호화 된 쿠키 정보를 반환.
     * @Deprecated 레거시 용도로만 사용 권장.
     */

    public static String decryptAesCbc(String encrypted, String infoKey, String infoIv) {
        Assert.notNull(encrypted, "The encrypted must not be null!");
        Assert.notNull(infoKey, "The infoKey must not be null!");
        Assert.notNull(infoIv, "The infoIv must not be null!");

        if ("".equals(encrypted)) {
            return "";
        }

        try {
            byte[] key = infoKey.getBytes("utf-8");
            byte[] iv = infoIv.getBytes("utf-8");

            Cipher dcipher = Cipher.getInstance("AES/CBC/NoPadding");
            dcipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] clearbyte = dcipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));

            return new String(clearbyte);

        } catch (Exception ex) {
            throw new RuntimeException("Decrypt decryptAesCbc Exception!", ex);
        }
    }

    /**
     * 위메프 신규 시스템의 암호화 처리방식
     * (AES-256, CBC, PKCS5Padding, encodeHexString)
     * Key 길이가 256이므로 알고리즘은 AES-256이 됨
     * iv 을 입력할 경우에는 그대로 사용하여 결과에 iv 가 포함되지 않으며, 입력되지 않을 경우에는 iv 가 랜덤으로 생성되고 결과에 iv 값이 포함된다.
     *
     * @param plainString 평문 문자열
     * @param key         SecretKeySpec 객체
     * @param ivBytes     initialVector 값으로 사용할 byte[] 16 자리로 들어와야 함.
     * @return encryptResultString 암호화된 문자열
     */
    private static String doEncrypt(String plainString, SecretKeySpec key, byte[] ivBytes) {
        Assert.notNull(plainString, "The plainString must not be null!");
        Assert.notNull(key, "The key must not be null!");

        // 결과값에 iv 값을 포함할지 여부.
        boolean isAttachIv = false;

        if ("".equals(plainString)) {
            return "";
        }

        // 입력이 안된 경우에는 랜덤으로 iv 를 만들어서 입력해준다.
        if( ivBytes == null ){
            ivBytes = new byte[IV_SIZE];
            new SecureRandom().nextBytes(ivBytes);
            // 랜덤으로 iv 를 생성할 경우에는 무조건 결과값에 포함.
            isAttachIv = true;
        }

        try {
            // IV Spec
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

            // 암호화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] encryptBytes = cipher.doFinal(plainString.getBytes(CharEncoding.UTF_8));

            String encryptResultString = null;
            if (!isAttachIv) {
                encryptResultString = Hex.encodeHexString(encryptBytes);
            } else {
                // ivBytes, encryptBytes 합치기
                byte[] encryptCombineBytes = new byte[IV_SIZE + encryptBytes.length];
                System.arraycopy(ivBytes, 0, encryptCombineBytes, 0, IV_SIZE);
                System.arraycopy(encryptBytes, 0, encryptCombineBytes, IV_SIZE, encryptBytes.length);
                // Hash 변환
                encryptResultString = Hex.encodeHexString(encryptCombineBytes);
            }
            return encryptResultString;
        } catch (Exception ex) {
            throw new RuntimeException("Encrypt Exception!", ex);
        }
    }

    /**
     * iv 값이 입력된 경우에는 decrypt 에 입력된 값을 그대로 사용하며, 없을 경우에는 암호화된 문자열에서 iv 를 추출하여 사용한다.
     *
     * @param encryptString
     * @param key
     * @param ivBytes
     * @return
     */
    private static String doDecrypt(String encryptString, SecretKeySpec key, byte[] ivBytes){
        Assert.notNull(encryptString, "The encryptString must not be null!");
        Assert.notNull(key, "The key must not be null!");
        if ("".equals(encryptString)) {
            return "";
        }
        try {
            // Hash 변환
            byte[] encryptCombineBytes = Hex.decodeHex(encryptString.toCharArray());

            int encryptSize = encryptCombineBytes.length;
            // 암호화 문자열에 iv 가 포함되어 있지 않을 경우 0.
            int ivSize = 0;

            if (ivBytes == null) {
                // 입력되지 않았을 경우에는 암호화 문자열에 입력된 것으로 처리.
                // IV Spec
                ivBytes = new byte[IV_SIZE];
                System.arraycopy(encryptCombineBytes, 0, ivBytes, 0, ivBytes.length);
                encryptSize -= IV_SIZE;
                // 암호화 문자열에 iv 가 포함되어 있을 경우 IV_SIZE
                ivSize = IV_SIZE;
            }

            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

            // encryptBytes 분리
            byte[] encryptBytes = new byte[encryptSize];
            System.arraycopy(encryptCombineBytes, ivSize, encryptBytes, 0, encryptSize);

            // Decrypt
            Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] decryptBytes = cipherDecrypt.doFinal(encryptBytes);

            String decryptResultString = new String(decryptBytes);

            return decryptResultString;
        } catch (Exception ex) {
            throw new RuntimeException("Decrypt Exception!", ex);
        }
    }

    /**
     * 수동으로 패딩 처리 한다. (위메프 레거시 데이터 암/복호화 시 사용함.)
     */
    private static String padding(String source) {
        char paddingChar = '\0';
        int size = 16;
        int padLength = size - source.length() % size;
        if (padLength != 16) {
            for (int i = 0; i < padLength; i++) {
                source += paddingChar;
            }
        }
        return source;
    }
}
