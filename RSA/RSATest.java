package thresholdEncryption;

import java.io.File;
import java.io.RandomAccessFile;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

  public class RSATest {
      public static void main(String[] args) throws Exception {
        // generate public and private keys
        long beginTime = System.nanoTime();
        KeyPair keyPair = RSA.buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        long endingTime = System.nanoTime();
        System.out.println("Total time for key generation " + (endingTime - beginTime) + " ");

        File file = new File("/Users/prathyushachintala/Desktop/Study_Notes/CrackingCoding");
        try (RandomAccessFile data = new RandomAccessFile(file, "r")) {
            byte[] array = new byte[256];
            for (long i = 0, len = data.length() / 256; i < len; i++) {
                data.readFully(array);
                String s = new String(array);

                //RSA Encryption//
                long beginTime1 = System.nanoTime();
                byte[] encrypted = RSA.encrypt(privateKey, s);
                long endTime1 = System.nanoTime();
                System.out.println("Total time to encrypt " + (endTime1 - beginTime1) + " ");

                //RSA decryption//
                long beginTime2 = System.nanoTime();
                byte[] secret = RSA.decrypt(pubKey, encrypted);
                long endTime2 = System.nanoTime();
                System.out.println("Total time to decrypt " + (endTime2 - beginTime2) + " ");
                System.out.println(new String(secret) + "  m");

                //generating digital signature//
                long beginTime3 = System.nanoTime();
                byte[] signature = RSA.digitalSign("Hello World", privateKey);
                long endingTime3 = System.nanoTime();
                System.out.println("Total time to sign " + (endingTime3 - beginTime3) + " ");

                //Digital signature verification//
                long beginTime4 = System.nanoTime();
                boolean verification = RSA.digitalSignVerify("Hello World", pubKey, signature);
                long endingTime4 = System.nanoTime();
                System.out.println("Total time to verify " + (endingTime4 - beginTime4) + " ");
                System.out.println("It is a valid signature: " + verification);


            }
        }
      }
  }
