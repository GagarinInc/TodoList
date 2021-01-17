package my.cv.todolist.security;

import java.util.Date;

public class TokenManager {
    private String secretKey;

    public TokenManager(String secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(TokenPayload tokenPayload) {
        String mixedPayload = crateMixedTokenPayload(tokenPayload);
        String signature = createSignature(mixedPayload);
        return String.format("%s#%s", mixedPayload, signature);
    }

    private String crateMixedTokenPayload(TokenPayload tokenPayload) {
        String creationTime = String.valueOf(tokenPayload.getCretionTime().getTime());
        String userId = String.valueOf(tokenPayload.getUserId());
        String email = String.valueOf(tokenPayload.getEmail());
        return String.format("%s#%s#%s", userId, email, creationTime);
    }

    private String createSignature(String mixedPayload) {
        return "" + mixedPayload.charAt(0) + mixedPayload.charAt(2)
                + secretKey.charAt(0) + secretKey.charAt(2)
                + secretKey.charAt(5) + mixedPayload.charAt(mixedPayload.length() - 1);
    }

    public boolean verifyToken(String token) {
        return createToken(extractPayload(token)).equals(token);
    }

    public TokenPayload extractPayload(String token) {
        String[] tokenParts = token.split("#");
        Long userId = Long.valueOf(tokenParts[0]);
        String email = tokenParts[1];
        Date creationTme = new Date(Long.parseLong(tokenParts[2]));
        return new TokenPayload(userId, email, creationTme);
    }
}
