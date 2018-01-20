package crypter.asymetric;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeyManager {

    // Saving
    private static void savePublicKey(PublicKey publicKey, String fileName) {
        RSAPublicKeySpec specification = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("asymetric");
            specification = factory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            file.writeObject(specification.getModulus());
            file.writeObject(specification.getPublicExponent());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePrivateKey(PrivateKey privateKey, String fileName) {
        RSAPrivateKeySpec specification = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("asymetric");
            specification = factory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            file.writeObject(specification.getModulus());
            file.writeObject(specification.getPrivateExponent());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reading
    public static PublicKey readPublicKey(String fileName) {

        PublicKey publicKey = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            RSAPublicKeySpec specification = new RSAPublicKeySpec((BigInteger) ois.readObject(), (BigInteger) ois.readObject());
            KeyFactory factory = KeyFactory.getInstance("asymetric");
            publicKey = factory.generatePublic(specification);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey readPrivateKey(String fileName) {

        PrivateKey privateKey = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            RSAPrivateKeySpec specification = new RSAPrivateKeySpec((BigInteger) ois.readObject(), (BigInteger) ois.readObject());
            KeyFactory factory = KeyFactory.getInstance("asymetric");
            privateKey = factory.generatePrivate(specification);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    // Generating
    public void generateKeys(String privateFile, String publicFile) {

        KeyPairGenerator keyGenerator = null;
        try {
            keyGenerator = KeyPairGenerator.getInstance("asymetric");
            keyGenerator.initialize(2048);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeyPair keysPair = keyGenerator.generateKeyPair();
        RSAKeyManager.savePrivateKey(keysPair.getPrivate(), privateFile);
        RSAKeyManager.savePublicKey(keysPair.getPublic(), publicFile);

    }

}