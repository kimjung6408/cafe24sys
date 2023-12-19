package cafe24customer.customersys.cafe24;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class CodeChallengeGenerator {
	
	public String generate() throws NoSuchAlgorithmException {
        // Generate a random codeVerifier
        String codeVerifier = generateRandomCodeVerifier();

        // Convert to ASCII bytes
        byte[] asciiBytes = codeVerifier.getBytes(java.nio.charset.StandardCharsets.US_ASCII);

        // Compute SHA-256 hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(asciiBytes);

        // Base64 URL Encode
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }

    private String generateRandomCodeVerifier() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 32 bytes for 256-bit randomness
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
