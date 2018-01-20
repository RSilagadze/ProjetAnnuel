package crypter.asymetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSACrypter {

    public static void encryptRSAFile(String publicKeyFile, String privateFile, String fileToEncrypt) {

        Path path = Paths.get(fileToEncrypt);

        PublicKey publicKey = RSAKeyManager.readPublicKey(publicKeyFile);

        byte[] bytes;

        try {
            Cipher cipher = Cipher.getInstance("asymetric");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            bytes = cipher.doFinal(Files.readAllBytes(path));

            FileOutputStream file = new FileOutputStream(privateFile);
            file.write(bytes);
            file.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void decrpytRSAFile(String privateKeyFile, String publicFile, String fileToDecrypt) {

        Path path = Paths.get(fileToDecrypt);

        PrivateKey privateKey = RSAKeyManager.readPrivateKey(privateKeyFile);

        byte[] bytes;

        try {
            Cipher dechiffreur = Cipher.getInstance("asymetric");
            dechiffreur.init(Cipher.DECRYPT_MODE, privateKey);
            bytes = dechiffreur.doFinal(Files.readAllBytes(path));

            FileOutputStream file = new FileOutputStream(publicFile);
            file.write(bytes);
            file.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
