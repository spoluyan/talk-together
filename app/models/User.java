package models;

import javax.persistence.Entity;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class User extends Model {
    @Required
    @Unique
    @MaxLength(255)
    public String nickName;

    @Required
    public String password;

    public User(String nickName, String password) {
        if (nickName == null) {
            nickName = "anonymous" + System.currentTimeMillis();
        }
        if (nickName.length() > 255) {
            nickName = nickName.substring(0, 255);
        }
        if (password == null) {
            password = "";
        }
        password = Crypto.passwordHash(password);

        this.nickName = nickName;
        this.password = password;
    }

    public static User findByNickName(String nickName) {
        return find("byNickName", nickName.toLowerCase()).first();
    }
}
