package controllers;

import java.util.List;

import models.Conversation;
import models.Level;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Chat extends Controller {
    public static void join() {
        List<Conversation> forBeginners = Conversation.findByLevel(Level.BEGINNER.getLevel());
        List<Conversation> forIntermediate = Conversation.findByLevel(Level.INTERMEDIATE.getLevel());
        List<Conversation> forAdvanced = Conversation.findByLevel(Level.ADVANCED.getLevel());
        render(forBeginners, forIntermediate, forAdvanced);
    }

    public static void chat(Long id) {
        Conversation chat = Conversation.findById(id);
        chat.addUser(Security.connected());
        chat.save();
        render(chat);
    }

    public static void create(int level, String topic) {
        Conversation chat = new Conversation(Security.connected(), level, topic).save();
        chat(chat.id);
    }
}
