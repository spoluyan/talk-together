package models;

import javax.persistence.Entity;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class User extends Model {
    @Required
    @Unique
    @MaxLength(255)
    public String nickName;

    @Required
    public String password;

    public User(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

    public static User findByNickName(String nickName) {
        return find("byNickName", nickName.toLowerCase()).first();
    }
}
