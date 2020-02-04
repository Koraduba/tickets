package epam.pratsaunik.tickets.hash;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
    public static String getHash(String encrypted){
    MessageDigest messageDigest;
    byte[] bytesEncoded = null;
    try {
        messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(encrypted.getBytes("utf8"));
        bytesEncoded = messageDigest.digest();
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    BigInteger bigInt = new BigInteger(1, bytesEncoded);
    String resHex = bigInt.toString(16);
    return resHex;
}
}
