package svkreml.challange10;

import svkreml.challenge1.HexBase64Converter;

public class FixedXOR {

    public static void main(String[] args) {
        System.out.println(fixedXOR("1c0111001f010100061a024b53535009181c","686974207468652062756c6c277320657965"));
        System.out.println("746865206b696420646f6e277420706c6179");
    }


    public static byte[] fixedXOR(byte[] input1, byte[] input2) {
        if (input1.length != input2.length) throw new IllegalArgumentException("input1.length!=input2.length");
        byte[] result = new byte[input1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (input1[i] ^ input2[i]);
        }
        return result;
    }

    public static String fixedXOR(String input1, String input2) {
        return HexBase64Converter.bytesToHex(fixedXOR(HexBase64Converter.hexToBytes(input1), HexBase64Converter.hexToBytes(input2)));
    }
}
