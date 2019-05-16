package svkreml.challenge1;

import java.util.Base64;

public class HexBase64Converter {
    public static void main(String[] args) {
        System.out.println(hexToBase64("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"));
        System.out.println("SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t");
    }


    static public String hexToBase64(String hexInput) {
        byte[] inputBytes = hexToBytes(hexInput);
        return Base64.getEncoder().encodeToString(inputBytes);
    }

    public static byte[] hexToBytes(String hexInput) {
        if (hexInput.length() % 2 != 0) throw new IllegalArgumentException("hex input should be even");
        byte[] inputBytes = new byte[hexInput.length() / 2];
        char[] charArray = hexInput.toCharArray();
        for (int i = 0; i < charArray.length / 2; i++) {
            inputBytes[i] = (byte) Integer.parseInt((charArray[2 * i]) + "" + (charArray[2 * i + 1]), 16);
        }
        return inputBytes;
    }

    public static String bytesToHex( byte[] bytesInput) {
       StringBuilder output = new StringBuilder(bytesInput.length * 2);
        for (int i = 0; i < bytesInput.length; i++) {
            if(Integer.toString(bytesInput[i],16).length()==2)
            output.append(Integer.toString(bytesInput[i],16));
            else
                output.append("0"+Integer.toString(bytesInput[i],16));
        }
        return output.toString();
    }
}
