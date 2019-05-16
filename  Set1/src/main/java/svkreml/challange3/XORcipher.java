package svkreml.challange3;

import svkreml.challenge1.HexBase64Converter;

import java.util.ArrayList;

public class XORcipher {
    public static byte[] encode(byte[] input, byte[] mask) {
        int maskLength = mask.length;
        byte[] result = new byte[input.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (input[i] ^ mask[i % maskLength]);
        }
        return result;
    }

    public static String encode(String input, String mask) {
        return new String(encode(HexBase64Converter.hexToBytes(input), HexBase64Converter.hexToBytes(mask)));
    }

    public static String encode(String input, byte mask) {
        return new String(encode(HexBase64Converter.hexToBytes(input), new byte[]{mask}));
    }


    public static void main(String[] args) {
        String input = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
     //   String input = "7b5a4215415d544115415d5015455447414c155c46155f4058455c5b523f";
        ArrayList<String> results = decodeString(input);
        System.out.println(results);
    }

    public static ArrayList<String> decodeString(String input) {
        ArrayList<String> results = new ArrayList<>();
        try {
            for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {

                String encode = encode(input, i);
                boolean validate = validate(encode) > 0.7;
                if (validate)
                    results.add(encode);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println(input + ", " + e.getMessage());
        }
        return results;
    }

    public static double validate(String string) {
        int stringSize = string.length();
        int likely = 0;
        for (byte stringByte : string.getBytes()) {
            if (stringByte == ' '  ||
                    stringByte >='0' && stringByte <='9' ||
                    stringByte >='A' && stringByte <='Z' ||
            stringByte >='a' && stringByte <='z')
                likely++;
        }
        return (likely / (double) stringSize);
    }
}
