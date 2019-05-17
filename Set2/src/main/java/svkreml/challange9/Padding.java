package svkreml.challange9;

import org.bouncycastle.util.encoders.Hex;

import java.util.Arrays;

public class Padding {
    public static void main(String[] args) {
        System.out.println(Hex.toHexString("YELLOW SUBMARINE".getBytes()));
        System.out.println(Hex.toHexString(createPading("YELLOW SUBMARINE".getBytes(),20)));
        System.out.println();
    }
    public static byte[] createPading(byte[] input, int pad){
        int addToPad = pad - input.length % pad;
        byte[] bytes = Arrays.copyOf(input,input.length + addToPad);
       Arrays.fill(bytes, input.length,input.length + addToPad, (byte) addToPad);
       return bytes;
    }
}
