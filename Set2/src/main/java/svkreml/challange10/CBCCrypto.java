package svkreml.challange10;


import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import svkreml.FileManager;
import svkreml.challange3.XORcipher;
import svkreml.challange9.Padding;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class CBCCrypto {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidCipherTextException {
        Security.addProvider(new BouncyCastleProvider());

        byte[] iv = Hex.decode("00000000000000000000000000000000");

       // Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
        byte[] keyBytes = "YELLOW SUBMARINE".getBytes();

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
       // cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] base64 = FileManager.read(new File(
                "Set2\\src\\main\\resources\\svkreml\\challange10\\10.txt"));
        byte[] bytes = Base64.getMimeDecoder().decode(base64);

        ArrayList<byte[]> decoded = new ArrayList<>();
        byte[] mask = iv;

        for (int i = 0; i < bytes.length; i += 16) {
            byte[] copyOfRange = Padding.createPading( Arrays.copyOfRange(bytes, i, i + 16),16);
           //cipher.update();
            int i1 = padCount(copyOfRange);
            System.out.println(i1);
            byte[] decrypted = cipher.doFinal(copyOfRange);
            //cipher.doFinal();
            decoded.add(XORcipher.encode
                    (decrypted, mask));
            mask = copyOfRange;
        }

        decoded.forEach((b) -> {
            System.out.println(String.format("%40s  |  %40s",
                    Hex.toHexString(b),
                    new String(b).replaceAll("(\\r\\n|\\r|\\n)", "")));
        });
        //System.out.println(new String(cipherText));
    }
    /**
     * return the number of pad bytes present in the block.
     */
    public static int padCount(byte[] in)
            throws InvalidCipherTextException
    {
        int count = in[in.length - 1] & 0xff;
        byte countAsbyte = (byte)count;

        // constant time version
        boolean failed = (count > in.length | count == 0);

        for (int i = 0; i < in.length; i++)
        {
            failed |= (in.length - i <= count) & (in[i] != countAsbyte);
        }

        if (failed)
        {
            throw new InvalidCipherTextException("pad block corrupted");
        }

        return count;
    }

}
// cipher.init(Cipher.ENCRYPT_MODE, key);
// byte[] plainText  = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
// byte[] cipherText = cipher.doFinal(plainText);
