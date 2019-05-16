package svkreml.challange4;

import svkreml.challange3.XORcipher;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class XORdecrypter {
    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        try (InputStream resourceAsStream = XORdecrypter.class.getResourceAsStream("4.txt")) {
            String line = null;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"))) {
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            ArrayList<String> strings = XORcipher.decodeString(line);
          if(!strings.isEmpty()) {
              System.out.println(i);
              strings.spliterator().forEachRemaining(System.out::println);
          }
        }
    }
}
