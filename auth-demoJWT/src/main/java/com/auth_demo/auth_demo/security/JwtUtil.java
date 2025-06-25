package com.auth_demo.auth_demo.security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;
@Component
public class JwtUtil {

    private final String SECRET = "promovare2025VR";

    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        long expiry = now + 1000 * 60 * 60; // 1 hour

        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
        String payload = Base64.getEncoder().encodeToString(("{\"sub\":\"" + username + "\",\"exp\":" + expiry + "}").getBytes());

        String signature = hmacSha256(header + "." + payload, SECRET);

        return header + "." + payload + "." + signature;
    }

    public String extractUsername(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return null;

        String payloadJson = new String(Base64.getDecoder().decode(parts[1]));
        String sub = payloadJson.split("\"sub\":\"")[1].split("\"")[0];
        return sub;
    }

    public boolean isTokenValid(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            String signature = hmacSha256(parts[0] + "." + parts[1], SECRET);
            if (!signature.equals(parts[2])) return false;

            String payloadJson = new String(Base64.getDecoder().decode(parts[1]));
            long exp = Long.parseLong(payloadJson.split("\"exp\":")[1].replaceAll("[^0-9]", ""));
            return System.currentTimeMillis() < exp;
        } catch (Exception e) {
            return false;
        }
    }

    private String hmacSha256(String data, String secret) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            byte[] result = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(result).replace("=", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


