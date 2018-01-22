package crypter.asymetric;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeyManager {

    private final static String userPublicKeyFilePath = "userPublicKey";
    private final static String userPrivateKeyFilePath = "userPrivateKey";


    public static String getUserPublicKeyFilePath() {
        return userPublicKeyFilePath;
    }

    public static String getUserPrivateKeyFilePath() {
        return userPrivateKeyFilePath;
    }


    // Generating
    public static void saveKeys() {

        KeyPairGenerator keyGenerator = null;
        try {
            keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeyPair keysPair = keyGenerator.generateKeyPair();
        RSAKeyManager.generatePublicKey(keysPair.getPublic(), userPublicKeyFilePath);
        RSAKeyManager.generatePrivateKey(keysPair.getPrivate(), userPrivateKeyFilePath);

    }

    // Saving
    private static void generatePublicKey(PublicKey publicKey, String fileName) {
        RSAPublicKeySpec specification = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
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

    private static void generatePrivateKey(PrivateKey privateKey, String fileName) {
        RSAPrivateKeySpec specification = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
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
            ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            RSAPublicKeySpec specification = new RSAPublicKeySpec((BigInteger) file.readObject(), (BigInteger) file.readObject());

            KeyFactory factory = KeyFactory.getInstance("RSA");
            publicKey = factory.generatePublic(specification);

            file.close();
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
            ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            RSAPrivateKeySpec specification = new RSAPrivateKeySpec((BigInteger) file.readObject(), (BigInteger) file.readObject());

            KeyFactory factory = KeyFactory.getInstance("RSA");
            privateKey = factory.generatePrivate(specification);

            file.close();
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

}