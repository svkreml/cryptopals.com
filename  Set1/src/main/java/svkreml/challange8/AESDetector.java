package svkreml.challange8;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import svkreml.FileManager;
import svkreml.challenge1.HexBase64Converter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AESDetector {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Security.addProvider(new BouncyCastleProvider());


        Cipher cipher = Cipher.getInstance("AES");
        byte[] keyBytes = "YELLOW SUBMARINE".getBytes();
        String algorithm = "RawBytes";
        SecretKeySpec key = new SecretKeySpec(keyBytes, algorithm);
        // cipher.init(Cipher.ENCRYPT_MODE, key);
        // byte[] plainText  = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
        // byte[] cipherText = cipher.doFinal(plainText);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = FileManager.read(new File(" Set1\\src\\main\\resources\\svkreml\\challange8\\8.txt"));
        String[] lines = new String(bytes).split("\n");
        ArrayList<byte[]> byteLines = new ArrayList<>();
        for (String line : lines) {
            byteLines.add(HexBase64Converter.hexToBytes(line));
        }

        for (int i = 0; i < byteLines.size(); i++) {
            System.out.println("----------------------");
            byte[] byteLine = byteLines.get(i);
            Set<byte[]> blocks = new HashSet<>();
            AtomicInteger uniq = new AtomicInteger(0);
            for (int j = 0; j < byteLine.length / 16; j++) {
                byte[] copyOfRange = Arrays.copyOfRange(byteLine, j, j + 16);

                blocks.forEach((b) -> {
                    boolean equals = Arrays.equals(b, copyOfRange);
                    if (equals) uniq.getAndAdd(1);
                });
                blocks.add(copyOfRange);

            }
            System.out.println(String.format("%6s, %4d", i, uniq.get()));
/*            try {
                System.out.println(i + "; " + byteLine.length);
                byte[] cipherText = cipher.doFinal(byteLine);
                System.out.println(i + "; " + new String(cipherText));
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                System.out.println(i + "; " + e.getMessage());
            }*/
        }
    }
}
