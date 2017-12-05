import java.security.PrivateKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.RSAPrivateKeySpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.IOException;

public class Decryption {

	public void main(String filePrivateKey, String fileToDecrypt) {

		PrivateKey privateKey = KeyManagement.privateKeyReading(filePrivateKey);

		Path path = Paths.get(fileToDecrypt);
		byte[] bytes = null;
		try {
			Cipher dechiffreur = Cipher.getInstance("RSA");
			dechiffreur.init(Cipher.DECRYPT_MODE, privateKey);
			bytes = dechiffreur.doFinal(Files.readAllBytes(path));
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
