package svkreml.challange5;

import svkreml.challange3.XORcipher;
import svkreml.challenge1.HexBase64Converter;

import java.io.UnsupportedEncodingException;

public class XORchipher2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //System.out.println(new String(HexBase64Converter.hexToBytes(HexBase64Converter.bytesToHex("Burning 'em, if you ain't quick and nimble".getBytes()))));
        String s = "Burning 'em, if you ain't quick and nimble\n" +
                "I go crazy when I hear a cymbal";

        System.out.println(HexBase64Converter.bytesToHex(
                XORcipher.encode(s.getBytes("UTF-8"), "ICE".getBytes("UTF-8"))
        ));
        System.out.println("0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f");
    }
}
