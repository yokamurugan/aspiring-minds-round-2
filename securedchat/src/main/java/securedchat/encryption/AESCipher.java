package securedchat.encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

    public static byte[] generateAESKey() throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }

    public static byte[] encrypt(byte[] input, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secretKey = new SecretKeySpec(key, 0, key.length, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");
        cipher.init(Cipher.DECRYPT_MODE, originalKey);
        return cipher.doFinal(input);
    }
}
