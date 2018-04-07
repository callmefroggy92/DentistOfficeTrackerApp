package com.example.froggy.dentistofficetracker;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Random;
import java.security.SecureRandom;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import java.util.Arrays;
import android.util.Base64;

/**
 * <p>This code was adapted from a code created by Brother Falin of BYU-I.</p>
 *
 * <p>This class has several functions: take a User class and hash its password, take a User class
 * verify that the password matches its hash, create a random key for a User account(to be used
 * for data encryption), then encrypt/decrypt that key using the User's password. </p>
 *
 * @author  Logan Holland, (adapted from code by Brother Falin of BYU-I)
 * @version 1.0
 * @since   4-7-18
 *
 * Brother Falin's code was adapted from <a href="http://stackoverflow.com/a/18143616/28106">this Stack Overflow post</a>.
 */
public final class LoginController {

    private static final Random RANDOM = new SecureRandom();

    // The use of "static final" here is the equivalent of "const" in C++
    // These constant values are used by the hash algorithm.
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    // By using a private constructor, we prevent instances of this class from being created
    private LoginController() {

    }

    // This function generates a random, 16-byte
    // salt value. You might be wondering if a longer salt
    // would result in a more secure hash:
    // http://stackoverflow.com/questions/184112/what-is-the-optimal-length-for-user-password-salt
    private static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    // This function uses the PBKDF2 algorithm for generating the hash. If you're
    // interested in why this particular function was chosen, see:
    // http://security.stackexchange.com/questions/4781/do-any-security-experts-recommend-bcrypt-for-password-storage/6415#6415
    private static byte[] getHash(char[] password, byte[] salt) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return skf.generateSecret(spec).getEncoded();

    }

    /**
     * This function takes the password from the {@link User} class and hashes it.
     * As a side-effect, the original password value is removed for security purposes.
     * @param user The user whose password needs to be hashed.
     * @exception Exception If there is a problem with the chosen hash function.
     */
    public static void hashUserPassword(User user) throws Exception {

        // Get the next random salt value to use for this password
        byte[] salt = getNextSalt();
        char[] password = user.getPassword().toCharArray();

        createKey(user);

        // Once we've generated the hash, clear the old password
        // from memory for security purposes
        byte[] hash = getHash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        user.setPassword("");

        if(hash != null) {

            // By Base64-encoding the raw bytes, we can store them as strings.
            // This allows us to save the values to a file or database if needed.
            // For more information on Base64 encoding, see:
            // http://stackoverflow.com/questions/201479/what-is-base-64-encoding-used-for
            // https://en.wikipedia.org/wiki/Base64
            String hashString = Base64.encodeToString(hash, Base64.DEFAULT);
            String saltString = Base64.encodeToString(salt, Base64.DEFAULT);

            user.setHash(hashString);
            user.setSalt(saltString);
        }
        else
        {
            user.setHash(null);
            user.setSalt(null);
        }
    }

    /**
     * This function uses the password and salt in the {@link User} to generate a hash,
     * then compares that hash to the original hash value.
     * @param user The user whose password needs to be hashed.
     * @return Whether or not the password values match.
     * @exception Exception If there is a problem with the chosen hash function.
     */

    private static void createKey(User user) {
        try {
            // Needs to create a random string then encrypt it using the
            String userKey = user.getPassword();
            while(userKey.length() < 16)
                userKey += '0';

            EncryptionMachine eMachine = new EncryptionMachine(userKey);

            // sets random key
            user.setDecKey(EncryptionMachine.createKey());
            // encrypts key
            user.setKey(eMachine.encrypt(user.getDecKey()));

            Random rand = new Random();
            java.util.Arrays.fill(userKey.toCharArray(), (char)(rand.nextInt(10000)));

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Boolean verifyPassword(User user) throws Exception {

        // Have to get the raw data values to use on our hash function
        char[] password = user.getPassword().toCharArray();
        byte[] salt = Base64.decode(user.getSalt(), Base64.DEFAULT);

        // Generate the new hash, and retrieve the user's hash
        byte[] expectedHash = getHash(password, salt);
        byte[] userHash = Base64.decode(user.getHash(), Base64.DEFAULT);

        // If the new hash came out as null, or the lengths don't match,
        // we know that the original password is different
        if(expectedHash == null || expectedHash.length != userHash.length)
            return false;

        // Check each byte of the two hashes and as soon as we find one
        // that is different, we know that the passwords don't match
        for(int i = 0; i < expectedHash.length; i++) {
            if(expectedHash[i] != userHash[i])
                return false;
        }

        // If we got this far, it means the password hashes match, so we
        // can assume the passwords do as well.
        restoreKey(user);

        // Securely deletes the password
        Random rand = new Random();
        java.util.Arrays.fill(user.getPassword().toCharArray(), (char)(rand.nextInt(600000)));
        user.setPassword("");

        return true;
    }

    private static void restoreKey(User user){
        try{
            // This will decrypt the key using the user's password
            String userKey = user.getPassword();

            // Adds 0s if password is not 16 characters long
            while(userKey.length() < 16)
                userKey += '0';

            EncryptionMachine eMachine = new EncryptionMachine(userKey);
            user.setDecKey(eMachine.decrypt(user.getKey()));

            Random rand = new Random();
            java.util.Arrays.fill(userKey.toCharArray(), (char)(rand.nextInt(600000)));


        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
