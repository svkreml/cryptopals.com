package svkreml.challange12;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import svkreml.challange9.Padding;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

public class AESOracle {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String inputBase64 = "Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkg\n" +
                "aGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBq\n" +
                "dXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUg\n" +
                "YnkK";
        byte[] secret = Base64.getMimeDecoder().decode(inputBase64);




        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        byte[] keyBytes = generateRandomBytes(16);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        StringBuilder s = new StringBuilder();
        s.append("AAAAAAAAAAAAARoA"+new String(secret));

        HashMap<String, Byte> prefixes = new HashMap<>();
        for (int b = Byte.MIN_VALUE; b <= Byte.MAX_VALUE; b++) {
            byte[] sBytes = s.toString().getBytes();
            sBytes[15]=(byte)b;
            //System.out.println("--------- " + (byte) b + " ---------");
            byte[] bytes = cipher.doFinal(sBytes);

            prefixes.put(Hex.toHexString(Arrays.copyOfRange(bytes, 0, 16)), (byte)b);

            //System.out.println();
        }

        byte[] bytes = cipher.doFinal(("AAAAAAAAAAAAA" + new String(secret)).getBytes());
        Byte aByte = prefixes.get(Hex.toHexString(Arrays.copyOfRange(bytes, 0, 16)));
        System.out.println(new String(new byte[]{aByte.byteValue()}));


/*        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            byte[] sBytes = s.toString().getBytes();
            System.out.println("---------" + sBytes.length + "---------");
            byte[] bytes = cipher.doFinal(sBytes);
            for (int j = 0; j < bytes.length; j += 16) {
                System.out.print(Hex.toHexString(Arrays.copyOfRange(bytes, j, j + 16)) + " ");
            }
            System.out.println();
            s.append("A");
        }*/



        System.out.println(new String(secret));

    }


    static Random random = new Random();

    public static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

}
