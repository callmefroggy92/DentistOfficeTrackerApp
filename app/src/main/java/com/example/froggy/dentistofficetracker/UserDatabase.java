package com.example.froggy.dentistofficetracker; /***********************************
 * UserDatabase should contain the information
 * for all user's registered for an instance
 * of DentistOfficeTracker.  UserDatabase is used
 * for two purposes: two verify login information,
 * and to allow the admin user to access all user
 * information.  It shall be encrypted
 * using the admin encryption key, as such,
 * the information should only be accessible to
 * the admin user.
 ***********************************/

import com.google.gson.Gson;
import java.io.FileWriter;
import java.util.Vector;


public class UserDatabase {

    Vector<User> users;

    public UserDatabase() {
        users = new Vector<User>();
    }

    public void add(User newUser) {
        // Needs validation that user is not already in database
        users.add(newUser);
    }

    public void removeUser(User user) {
        // Needs created
    }

    public Vector<User> getUsers() {
        return this.users;
    }

    public void encrypt(String key) {
        // All users will be encrypted to a specific key before being saved
        UserDatabaseEncryptionMachine.encrypt(this, key);
    }

    public void save(){
        try {

            FileWriter writer = new FileWriter("userdatabase.txt");
            Gson gson = new Gson();
            String data = gson.toJson(this);
            writer.write(data);
            writer.close();

        } catch (Exception e){

        }
    }

    public void decrypt(String key) { UserDatabaseEncryptionMachine.decrypt(this, key);}
    public static class UserDatabaseEncryptionMachine {

        public static UserDatabase encrypt(UserDatabase database, String key) {

            // This will encrypt all users to a specific key
            for (User u : database.getUsers()) {
                try {

                } catch (Exception e) {

                }
            }

            return database;
        }

        public static void decrypt(UserDatabase database, String key) {

            // This will decrypt all users to a specific key
            for (User u : database.getUsers()){
                try {

                } catch (Exception e) {
                    // this should be added to the log
                }

            }

        }
    }
}
