package thresholdEncryption;

import java.io.*;
import java.security.*;
import java.lang.System;
import javax.crypto.Cipher;

public class RSA {
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keyLength = 4096;
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(keyLength);
        return keyGenerator.genKeyPair();
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(encrypted);
    }

    public static byte[] digitalSign(String message, PrivateKey privateKey) throws Exception {
        Signature privateKeySig = Signature.getInstance("SHA256withRSA");
        privateKeySig.initSign(privateKey);
        privateKeySig.update(message.getBytes());
        byte[] signature = privateKeySig.sign();
        return signature;

    }


    public static boolean digitalSignVerify(String message, PublicKey publicKey, byte[] signature) throws Exception {
        Signature publicKeySig = Signature.getInstance("SHA256withRSA");
        publicKeySig.initVerify(publicKey);
        publicKeySig.update(message.getBytes());
        return publicKeySig.verify(signature);
    }
}
