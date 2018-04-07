package com.example.froggy.dentistofficetracker;

/*******************************************
 *  <p>EncryptionMachine class takes a key as an argument in the constructor, then
 *  uses that key to encrypt and decrypt strings. </p>
 *
 *  @author Logan Holland
 *  @since 4-7-18
 ******************************************/

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import android.util.Base64;
import java.util.Random;

public class EncryptionMachine {

    private Key keySpec;
    private Cipher cipher;

    /**
     * The public constructor for EncryptionMachine takes a string as an argument.  That string is used as the
     * key for all encryption/decryption used by the class instance.
      * @param key This key MUST be 16 characters long.
     * @throws Exception An exception is thrown if the key is not 16 characters long.
     */
    public EncryptionMachine(String key) throws Exception{
        if(key.length() != 16)
            throw new EMException("EncryptionMachineException: Invalid key length.  Key must be length 16");

        try {

            keySpec = new SecretKeySpec(key.getBytes("UTF8"), "AES");
            cipher = Cipher.getInstance("AES");

        } catch(Exception e){

        }
    }

    /**
     * Encrypts a given string using the key provided in the constructor
      * @param str This is the string that is desired to be encrypted
     * @return The return value is the encrypted String
     * @throws Exception An exception is thrown if the str argument is empty
     */
    public String encrypt(String str) throws Exception{

        if(str == null)
            throw new EMException("EncryptionMachineException: empty string");

        try {

            cipher.init(Cipher.ENCRYPT_MODE, this.keySpec);

            byte[] enc = cipher.doFinal(str.getBytes("UTF8"));
            System.out.println(enc);
            return Base64.encodeToString(enc, Base64.DEFAULT);

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
      * @param str This is the encrypted string that is desired to be decrypted
     * @return The return value is the decrypted version of the string passed by argument
     * @throws Exception an exception is thrown if the str argument is empty;
     */
    public String decrypt(String str) throws Exception{

        if(str == null)
            throw new EMException("EncryptionMachineException: empty string.");

        try{
            cipher.init(Cipher.DECRYPT_MODE, this.keySpec);
            byte[] dec = Base64.decode(str, Base64.DEFAULT);
            dec = cipher.doFinal(dec);
            return new String(dec, "UTF8");

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return Returns a random 16 character key that can be used for encryption/decryption
     */
    public static String createKey(){

        Random rand = new Random(System.currentTimeMillis());
        String str= new String();

        for(int i = 0; i < 16; i++){
            str += (char) rand.nextInt(128);
        }

        assert(str.length() == 16);

        return str;
    }
}