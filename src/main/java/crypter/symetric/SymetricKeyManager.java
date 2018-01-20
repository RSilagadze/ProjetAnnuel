package crypter.symetric;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class SymetricKeyManager {

    private final static String userKeyFilePath = "userKey";

    // Generating
    private static String generateKey() {
        Random random = new Random();

        StringBuilder generatedString = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            int letter = random.nextInt(74) + 48;

            if (letter < 0) {
                letter *= -1;
            }

            generatedString.append((char) letter);
        }

        return generatedString.toString();
    }

    // Saving
    private static String saveKey() {
        String key = "";
        Path userkeyFile = Paths.get(userKeyFilePath);

        try {
            key = generateKey();
            Files.write(userkeyFile, key.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return key;
    }

    // Reading
    public static String readKey() {
        String key = "";
        File file = new File(userKeyFilePath);

        if (file.exists()) {
            try {
                Path filePath = Paths.get(userKeyFilePath);
                key = Files.readAllLines(filePath).get(0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            key = saveKey();
        }

        return key;
    }

}
