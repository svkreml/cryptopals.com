package svkreml.challange7;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class OpenSSLWrapper {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Security.addProvider(new BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes   = "YELLOW SUBMARINE".getBytes();
        String algorithm  = "RawBytes";
        SecretKeySpec key = new SecretKeySpec(keyBytes, algorithm);


       // cipher.init(Cipher.ENCRYPT_MODE, key);
       // byte[] plainText  = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
       // byte[] cipherText = cipher.doFinal(plainText);


        cipher.init(Cipher.DECRYPT_MODE, key);


    }
}
