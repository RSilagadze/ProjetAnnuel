import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAPrivateKeySpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encryption {

	public void encrypt(String publicFile, String privateFile,
			String fileToEncrypt) {

		Path path = Paths.get(fileToEncrypt);

		PublicKey publicKey = KeyManagement.publicKeyReading(publicFile);
		byte[] bytes = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			bytes = cipher.doFinal(path.readAllBytes(path));
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
		}

	}

}
