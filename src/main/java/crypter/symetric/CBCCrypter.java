package crypter.symetric;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CBCCrypter {

    private static byte[] encryptCBCBytes(ByteArrayInputStream bar, ByteArrayOutputStream bos, byte[] prevBlock, String key)
            throws Exception {
        byte[] keyBytes = key.getBytes();
        int blockLength = keyBytes.length;
        byte[] blockBuff = new byte[blockLength];

        if (blockLength != prevBlock.length)
            throw new Exception("La clé et le vecteur d'initialisation doivent contenir le même nombre de caractères");

        int bRead;

        if ((bRead = bar.read(blockBuff, 0, blockLength)) > 0) {
            for (int i = 0; i < bRead; i++) {
                int xor = (int) prevBlock[i] ^ (int) blockBuff[i] ^ (int) keyBytes[i];
                prevBlock[i] = (byte) xor;
                bos.write(xor);
            }

            return encryptCBCBytes(bar, bos, prevBlock, key);
        }

        return bos.toByteArray();
    }

    private static byte[] decryptCBCBytes(ByteArrayInputStream bar, ByteArrayOutputStream bos, byte[] prevBlock, String key)
            throws Exception {
        byte[] keyBytes = key.getBytes();
        int blockLength = keyBytes.length;
        byte[] blockBuff = new byte[blockLength];

        if (blockLength != prevBlock.length)
            throw new Exception("La clé et le vecteur d'initialisation doivent contenir le même nombre de caractères");

        int bRead;

        if ((bRead = bar.read(blockBuff, 0, blockLength)) > 0) {
            for (int i = 0; i < bRead; i++) {
                int xor = (int) blockBuff[i] ^ (int) keyBytes[i] ^ (int) prevBlock[i];
                bos.write(xor);
            }

            return decryptCBCBytes(bar, bos, blockBuff, key);
        }

        return bos.toByteArray();
    }

    // Méthode permettant le choix entre le chiffrement ou le déchiffrement
    private static byte[] encryptOrDecryptCBCBytes(String action, ByteArrayInputStream bar, ByteArrayOutputStream bos, byte[] prevBlock, String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        int blockLength = keyBytes.length;
        byte[] blockBuff = new byte[blockLength];

        if (blockLength != prevBlock.length)
            throw new Exception("La clé et le vecteur d'initialisation doivent contenir le même nombre de caractères");

        int bRead;

        if (action.equals("encrypt")) {
            if ((bRead = bar.read(blockBuff, 0, blockLength)) > 0) {
                for (int i = 0; i < bRead; i++) {
                    int xor = (int) prevBlock[i] ^ (int) blockBuff[i] ^ (int) keyBytes[i];
                    prevBlock[i] = (byte) xor;
                    bos.write(xor);
                }

                return encryptCBCBytes(bar, bos, prevBlock, key);
            }
        } else if (action.equals("decrypt")) {
            if ((bRead = bar.read(blockBuff, 0, blockLength)) > 0) {
                for (int i = 0; i < bRead; i++) {
                    int xor = (int) blockBuff[i] ^ (int) keyBytes[i] ^ (int) prevBlock[i];
                    bos.write(xor);
                }

                return decryptCBCBytes(bar, bos, blockBuff, key);
            }
        } else {
            System.out.println("Action non reconnue");

            return ("ERREUR !").getBytes();
        }

        return bos.toByteArray();
    }

    public static void encryptCBCFile(String path, String key) throws Exception {
        Path file = Paths.get(path);

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream(128);

        try (ByteArrayInputStream byteInStream = new ByteArrayInputStream(Files.readAllBytes(file))) {
            // Par soucis de facilité, au lieu de mettre le vecteur d'initialisation à la fin du fichier,
            // nous utilisons un vecteur d'initialisation donné
            byte[] prevBlock = ("projet_cryptographie").getBytes();

            byte[] cryptResult = encryptCBCBytes(byteInStream, byteOutStream, prevBlock, key);

            Files.write(file, cryptResult);
        }

        byteOutStream.close();
    }

    public static void decryptCBCFile(String path, String key) throws Exception {
        Path file = Paths.get(path);

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream(128);

        try (ByteArrayInputStream byteInStream = new ByteArrayInputStream(Files.readAllBytes(file))) {
            byte[] prevBlock = ("projet_cryptographie").getBytes();

            byte[] cryptResult = decryptCBCBytes(byteInStream, byteOutStream, prevBlock, key);

            Files.write(file, cryptResult);
        }

        byteOutStream.close();
    }

    public static void encryptOrDecryptCBCFile(String action, String path, String key) throws Exception {
        Path file = Paths.get(path);

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream(128);

        try (ByteArrayInputStream byteInStream = new ByteArrayInputStream(Files.readAllBytes(file))) {
            // Par soucis de facilité, au lieu de mettre le vecteur d'initialisation à la fin du fichier,
            // nous utilisons un vecteur d'initialisation donné
            byte[] prevBlock = ("projetCrypto").getBytes();

            byte[] cryptResult;

            if (action.equals("encrypt")) {
                cryptResult = encryptCBCBytes(byteInStream, byteOutStream, prevBlock, key);
            } else if (action.equals("decrypt")) {
                cryptResult = decryptCBCBytes(byteInStream, byteOutStream, prevBlock, key);
            } else {
                cryptResult = ("ERREUR !").getBytes();

                System.out.println("Action non reconnue");
            }

            Files.write(file, cryptResult);
        }

        byteOutStream.close();
    }

}
