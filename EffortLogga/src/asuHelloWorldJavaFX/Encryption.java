package asuHelloWorldJavaFX;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	public static String hashPassword(String password) {
		try {
			// Create a MessageDigest instance for the SHA-256 algorithm
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// Update the digest with the password bytes
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

			// Convert the hash bytes to a hexadecimal representation
			StringBuilder hexString = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			// inputs hashed password as data value in to storeHashedPasswordToFile function
			//storeHashedPasswordToFile("credentials.txt", userId, password, hexString.toString());
			
			// returns hashed password
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// Handle the exception appropriately (e.g., log or rethrow)
			throw new RuntimeException("SHA-256 algorithm is not available.", e);
		}
	}
}
