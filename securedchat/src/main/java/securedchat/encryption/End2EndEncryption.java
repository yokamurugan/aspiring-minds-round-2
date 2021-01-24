package securedchat.encryption;

import org.apache.commons.codec.binary.Base64;

public class End2EndEncryption {
    private static final String ENCODE_START = "@ENCODE@";
    private static final String SPLITER = "@_@";
    private static final String ENCRYPTED_FORM = ENCODE_START + "%s" + SPLITER + "%s ";
    private static Base64 encoder = new Base64();

    public static String encrypt(String message, byte[] publicKey) throws Exception {
        // Simply encrypt the message with a random AES aesKey
        byte[] aesKeyBytes = AESCipher.generateAESKey();
        byte[] messageByes = message.getBytes();
        byte[] encryptedMessage = AESCipher.encrypt(messageByes, aesKeyBytes);
        String encryptedMessageBase64 = encoder.encodeAsString(encryptedMessage);

        // But how will the receiver decrypt? She does not have the random aesKey
        // So, send it key with the message also
        // This time, use the Key that receiver sent us before (Her public key)
        byte[] encryptedAesKeyBytes = RSACipher.encrypt(aesKeyBytes, publicKey);
        String encryptedKeyBase64 = encoder.encodeAsString(encryptedAesKeyBytes);

        // Combine the AES + the Message
        return String.format(ENCRYPTED_FORM, encryptedKeyBase64, encryptedMessageBase64);
    }

    public static String decrypt(String message, byte[] privateKey) throws Exception {
        // The message contains AES Key + the Message
        String[] parts = message.split(SPLITER);

        // Using my private key, first decrypt the AES key
        String encryptedAESKeyBase64 = parts[0].replace(ENCODE_START, "");
        byte[] encryptedAESKeyBytes = encoder.decode(encryptedAESKeyBase64);
        byte[] aesKey = RSACipher.decrypt(encryptedAESKeyBytes, privateKey);

        // Using AES Key, decrypt the message
        String encryptedMessageBase64 = parts[1];
        byte[] encryptedMessage = encoder.decode(encryptedMessageBase64);
        byte[] messageBytes = AESCipher.decrypt(encryptedMessage, aesKey);

        return new String(messageBytes);
    }
}

