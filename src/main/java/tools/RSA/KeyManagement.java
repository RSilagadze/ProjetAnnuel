import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.NoSuchAlgorithmException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.math.BigInteger;

public class KeyManagement {

	public static void publicSave(PublicKey publicKey, String fileName) {
		RSAPublicKeySpec specification = null;
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			specification = factory.getKeySpec(publicKey,
					RSAPublicKeySpec.class);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		try {
			ObjectOutputStream file = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(fileName)));
			file.writeObject(specification.getModulus());
			file.writeObject(specification.getPublicExponent());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void privateSave(PrivateKey privateKey, String fileName) {
		RSAPrivateKeySpec specification = null;
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			specification = factory.getKeySpec(privateKey,
					RSAPrivateKeySpec.class);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		try {
			ObjectOutputStream file = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(fileName)));
			file.writeObject(specification.getModulus());
			file.writeObject(specification.getPrivateExponent());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PrivateKey privateKeyReading(String fileName) {

		PrivateKey privateKey = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(fileName)));
			RSAPrivateKeySpec specification = new RSAPrivateKeySpec(
					(BigInteger) ois.readObject(),
					(BigInteger) ois.readObject());
			KeyFactory factory = KeyFactory.getInstance("RSA");
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

	public static PublicKey publicKeyReading(String fileName) {
		
		PublicKey publicKey = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(fileName)));
			RSAPublicKeySpec specification = new RSAPublicKeySpec((BigInteger) ois.readObject(),
					(BigInteger) ois.readObject());
			KeyFactory factory = KeyFactory.getInstance("RSA");
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

}