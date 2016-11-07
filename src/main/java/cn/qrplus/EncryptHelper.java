package cn.qrplus;

import cn.qrplus.constant.KeyConstant;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * <p>ProjectName:manager</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/10/30
 */
public class EncryptHelper {


    public Key getKey() {
        KeyConstant keyConstant = new KeyConstant();
        return keyConstant.getKey();
    }

    public String encrypt(Key key, String src) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(src.getBytes());
        return bytesToHexString(result);
    }

    public String decrypt(Key key, byte[] src) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(src);
        return new String(result);
    }

    public String decrypt(Key key, String src) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(src.getBytes());
        return new String(result);
    }

    public String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}
