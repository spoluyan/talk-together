package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.db.jpa.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Entity
@Table(name = "tt_conversations")
public class Conversation extends Model {
    private static final Gson GSON = new Gson();

    public String users; // JSON

    @Required
    public int level;

    @Required
    @MaxLength(255)
    public String topic;

    public Conversation(String creator, int level, String topic) {
        if (topic == null) {
            topic = "No topic";
        }
        if (topic.length() > 255) {
            topic = topic.substring(0, 255);
        }

        this.level = level;
        this.topic = topic;
        addUser(creator);
    }

    public static List<Conversation> findByLevel(int level) {
        return find("byLevel", level).fetch();
    }

    public Set<String> getConversationUsers() {
        if (users == null) {
            return new HashSet<>();
        }
        return GSON.fromJson(users, new TypeToken<Set<String>>() {
        }.getType());
    }

    public void addUser(String nickName) {
        Set<String> conversationsUsers = getConversationUsers();
        conversationsUsers.add(nickName);
        this.users = GSON.toJson(conversationsUsers);
    }

    public void removeUser(String nickName) {
        Set<String> conversationsUsers = getConversationUsers();
        conversationsUsers.remove(nickName);
        this.users = GSON.toJson(conversationsUsers);
    }

    public Level getPrettyLevel() {
        return Level.fromLevel(level);
    }
}
