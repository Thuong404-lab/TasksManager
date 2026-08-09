package com.taskmanager.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;

public final class PasswordHasher {
    private PasswordHasher() {
    }

    public static String hash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String rawPassword, String storedHash) {
        if (rawPassword == null || storedHash == null) {
            return false;
        }
        if (storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$")) {
            try {
                return BCrypt.checkpw(rawPassword, storedHash);
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
        // Hỗ trợ hash MD5 cũ trong giai đoạn chuyển đổi; login thành công sẽ nâng lên BCrypt.
        return MessageDigest.isEqual(md5(rawPassword).getBytes(StandardCharsets.UTF_8),
                storedHash.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean needsUpgrade(String storedHash) {
        return storedHash != null && !storedHash.startsWith("$2");
    }

    private static String md5(String raw) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte value : digest) {
                result.append(String.format("%02x", value));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
