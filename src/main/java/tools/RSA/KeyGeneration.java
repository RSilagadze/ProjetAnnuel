import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGeneration {

	public void generate(String privateFile, String publicFile) {

		KeyPairGenerator keyGenerator = null;
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(2048);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		KeyPair keysPair = keyGenerator.generateKeyPair();
		KeyManagement.privateSave(keysPair.getPrivate(), privateFile);
		KeyManagement.publicSave(keysPair.getPublic(), publicFile);

	}

}