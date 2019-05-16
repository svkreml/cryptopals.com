package svkreml.challange8;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
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
        byte[] bytes = FileManager.read(new File("Set1\\src\\main\\resources\\" +
                "svkreml\\challange8\\8.txt"));
        String[] lines = new String(bytes).split("\n");
        ArrayList<byte[]> byteLines = new ArrayList<>();
        for (String line : lines) {
            byteLines.add(HexBase64Converter.hexToBytes(line));
        }
// todo https://thmsdnnr.com/tutorials/javascript/cryptopals/2017/09/22/cryptopals-set1-challenge-8-detecting-aes-in-ecb-mode.html
        for (int i = 0; i < byteLines.size(); i++) {
            System.out.println("----------------------");
            byte[] byteLine = byteLines.get(i);
            Set<SetBytes> blocks = new HashSet<>();
            for (int j = 0; j < byteLine.length; j+=16) {
                byte[] copyOfRange = Arrays.copyOfRange(byteLine, j, j + 16);
                blocks.add(new SetBytes(copyOfRange));
                System.out.println(Hex.toHexString(copyOfRange));
            }
            System.out.println(String.format("%6s, %4d", i, blocks.size()));
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
class SetBytes{
    byte[] bytes;

    public SetBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetBytes setBytes = (SetBytes) o;
        return Arrays.equals(bytes, setBytes.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }
}
