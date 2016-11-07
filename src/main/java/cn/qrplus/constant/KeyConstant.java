package cn.qrplus.constant;


import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * <p>ProjectName:manager</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/10/30
 */
public class KeyConstant {

    public static Key key;

    public KeyConstant() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            byte[] keyBytes = keyGenerator.generateKey().getEncoded();
            key = new SecretKeySpec(keyBytes, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Key getKey() {
        return key;
    }

}
