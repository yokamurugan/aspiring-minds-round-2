package securedchat.encryption;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.*;
import java.security.interfaces.RSAPublicKey;

public class RSACipher {

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static byte[] encrypt(byte[] keyBytes, byte[] pubBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec KeySpec = new X509EncodedKeySpec(pubBytes);
        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(KeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(keyBytes);
    }

    public static byte[] decrypt(byte[] keyBytes, byte[] priBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(priBytes));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(keyBytes);
    }
}
