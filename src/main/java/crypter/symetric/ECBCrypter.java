package crypter.symetric;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ECBCrypter {

    private static byte[] cryptECBBytes(byte[] bytes, String key)
            throws Exception {
        byte[] keyBytes = key.getBytes();
        int blockLength = keyBytes.length;
        byte[] blockBuff = new byte[blockLength];

        ByteArrayOutputStream bos = new ByteArrayOutputStream(128);

        try (ByteArrayInputStream bar = new ByteArrayInputStream(bytes)) {
            int bRead;

            while ((bRead = bar.read(blockBuff, 0, blockLength)) > 0) {
                for (int i = 0; i < bRead; i++) {
                    int xor = (int) blockBuff[i] ^ (int) keyBytes[i];
                    bos.write(xor);
                }
            }
        }

        byte[] result = bos.toByteArray();
        bos.close();

        return result;
    }

    public static void cryptECBFile(String path, String key)
            throws Exception {
        Path file = Paths.get(path);

        byte[] cryptResult = cryptECBBytes(Files.readAllBytes(file), key);

        Files.write(file, cryptResult);
    }

}
