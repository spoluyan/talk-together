package controllers;

import models.User;
import play.libs.Crypto;

public class Security extends Secure.Security {
    static boolean authenticate(String username, String password) {
        User user = User.findByNickName(username);
        if (user == null) {
            new User(username, Crypto.passwordHash(password)).save();
            return true;
        }
        return Crypto.passwordHash(password).equals(user.password);
    }
}
