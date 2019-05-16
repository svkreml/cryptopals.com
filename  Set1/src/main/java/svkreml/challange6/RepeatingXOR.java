package svkreml.challange6;

import javafx.util.Pair;
import jdk.nashorn.internal.runtime.JSType;
import org.omg.PortableInterceptor.INACTIVE;
import svkreml.challange3.XORcipher;
import svkreml.challange4.XORdecrypter;
import org.apache.commons.lang3.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;

import static svkreml.challange3.XORcipher.encode;
import static svkreml.challange3.XORcipher.validate;

public class RepeatingXOR {

    public static void main(String[] args) {
        StringBuilder inputFile = new StringBuilder();
        try (InputStream resourceAsStream = new FileInputStream("C:\\Users\\svkre\\IdeaProjects\\cryptopals\\ Set1\\src\\main\\resources\\svkreml\\challange6\\6.txt")) {
            String line = null;

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"))) {
                while ((line = bufferedReader.readLine()) != null) {
                    inputFile.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<KeyObject> guessedKeyLength = new ArrayList<>();
        byte[] inputBytes = Base64.getMimeDecoder().decode(inputFile.toString());
        for (int i = 2; i <= 40; i++) {

            ArrayList<DecodedObject> pairs = decodeString(inputBytes, i, 0);
            if (!pairs.isEmpty()) {
                //System.out.println("keyLength = " + i);
                int keyLength = i;
                pairs.spliterator().forEachRemaining((s) -> {
                    byte[] bytes = new byte[keyLength];
                    bytes[0] = s.keyByte;
                    guessedKeyLength.add(new KeyObject(keyLength, bytes, s.keyLikely));
    /*                    System.out.println(
                            "keyByte = " + s.getKey() +
                                    "\n\n" +
                                    s.getValue() +
                                    "\n\n\n\n");*/
                });
            }

        }


        guessedKeyLength.forEach((integerPair -> {
            byte[] key = integerPair.key;
            for (int offset = 1; offset < integerPair.keyLength; offset++) {
                ArrayList<DecodedObject> pairs = decodeString(inputBytes, integerPair.keyLength, offset);
                if (pairs.size() == 1) {
                    key[offset] = pairs.get(0).keyByte;
                }/* else if (pairs.size() == 0) {
                    System.out.println("failed");
                    key[offset] = 0;
                } else {
                    double max = 0.0;
                    DecodedObject decodedObject = null;
                    for (DecodedObject pair : pairs) {
                        if (pair.keyLikely > max) decodedObject = pair;
                    }
                    key[offset] = decodedObject.keyByte;
                }*/
            }
        }));
        // System.out.println(guessedKeyLength);

        double max = 0.0;
        KeyObject best =null;
        for (KeyObject decodedText : guessedKeyLength) {
            if (decodedText.keyLikely > max) {
                best = decodedText;
                max = decodedText.keyLikely;
            }
        }


        System.out.println("Key = " + new String(best.key) + "\n\n");
        System.out.println(new String(XORcipher.encode(inputBytes, best.key)));
    }


    public static ArrayList<DecodedObject> decodeString(byte[] inputBytes, int keyLength, int offset) {
        int len = inputBytes.length;
        byte[] repeatingBytes = new byte[len / keyLength];

        for (int i = 0, bytesLength = repeatingBytes.length; i < bytesLength; i++) {
            if (i * keyLength + offset > inputBytes.length) break;
            repeatingBytes[i] = inputBytes[i * keyLength + offset];
        }

        ArrayList<DecodedObject> results = new ArrayList<>();
        try {
            double max = 0.75;
            DecodedObject decodedObject = null;
            for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
                String encode = new String(encode(repeatingBytes, new byte[]{i}));
                double validate = validate(encode);

                //   System.out.println(validate);
                if (validate > max) {
                    decodedObject = new DecodedObject(i, encode, validate);
                    max = validate;
                }
            }

            // System.out.println(max);
            if (decodedObject != null)
                results.add(decodedObject);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println(inputBytes + ", " + e.getMessage());
        }
        return results;
    }
}

class DecodedObject {
    String value;
    byte keyByte;
    double keyLikely;

    public DecodedObject(byte keyByte, String value, double keyLikely) {
        this.value = value;
        this.keyByte = keyByte;
        this.keyLikely = keyLikely;
    }
}

class KeyObject {
    byte[] key;
    int keyLength;
    double keyLikely;

    public KeyObject(int keyLength, byte[] key, double keyLikely) {
        this.key = key;
        this.keyLength = keyLength;
        this.keyLikely = keyLikely;
    }
}
