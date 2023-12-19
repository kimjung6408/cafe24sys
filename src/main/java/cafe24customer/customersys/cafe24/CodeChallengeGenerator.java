package cafe24customer.customersys.cafe24;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class CodeChallengeGenerator {
	
    private static final int MIN_LENGTH = 43;
    private static final int MAX_LENGTH = 128;
    private static final SecureRandom secureRandom = new SecureRandom();

    public String generateCodeVerifier() {
        String codeVerifier;
        do {
            byte[] randomBytes = new byte[32]; // Generate new random bytes each time
            secureRandom.nextBytes(randomBytes);
            codeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        } while (codeVerifier.length() < MIN_LENGTH || codeVerifier.length() > MAX_LENGTH);

        return codeVerifier;
    }

    public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(codeVerifier.getBytes(java.nio.charset.StandardCharsets.US_ASCII));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
}
