package svkreml.challange7;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import svkreml.FileManager;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

public class AESCrypto {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Security.addProvider(new BouncyCastleProvider());


        Cipher cipher = Cipher.getInstance("AES");
        byte[] keyBytes   = "YELLOW SUBMARINE".getBytes();
        String algorithm  = "RawBytes";
        SecretKeySpec key = new SecretKeySpec(keyBytes, algorithm);
       // cipher.init(Cipher.ENCRYPT_MODE, key);
       // byte[] plainText  = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
       // byte[] cipherText = cipher.doFinal(plainText);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = FileManager.read(new File("Set1\\src\\main\\resources\\svkreml\\challange7\\7.txt"));
        byte[] cipherText = cipher.doFinal(Base64.getMimeDecoder().decode(bytes));

        System.out.println(new String(cipherText));
    }
}
