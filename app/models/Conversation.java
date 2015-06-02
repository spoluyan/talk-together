package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Entity
public class Conversation extends Model {
    private static final Gson GSON = new Gson();

    public String users; // JSON
    public String level;
    public String topic;

    @OneToMany(fetch = FetchType.LAZY)
    public List<Message> messages;

    public Conversation(String creator, String level, String topic) {
        this.level = level;
        this.topic = topic;
        addUser(creator);
        messages = new ArrayList<>();
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
}
