package controllers;

import java.util.List;

import models.Conversation;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Chat extends Controller {
    public static void join() {
        List<Conversation> conversations = Conversation.findAll();
        render(conversations);
    }
}
