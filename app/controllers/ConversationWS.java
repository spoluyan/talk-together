package controllers;

import java.util.HashSet;
import java.util.Set;

import models.Conversation;
import models.Message;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.mvc.Http.Outbound;
import play.mvc.Http.WebSocketEvent;
import play.mvc.WebSocketController;
import play.mvc.With;

@With(Secure.class)
public class ConversationWS extends WebSocketController {
    private static final Set<Outbound> SESSIONS = new HashSet<>();

    public static void conversation(Long chatId) {
        Logger.info("%s connected to conversation", Security.connected());
        SESSIONS.add(outbound);

        sendUserConnected(chatId);

        while (inbound.isOpen()) {
            WebSocketEvent event = await(inbound.nextEvent());

            WebSocketEvent.TextFrame.match(event).forEach(message -> sendMessage(chatId, message));

            WebSocketEvent.SocketClosed.match(event).forEach(closed -> {
                Conversation chat = Conversation.findById(chatId);
                sendUserDisconnected(chatId);
                Logger.info("%s disconnected from conversation", Security.connected());
                chat.removeUser(Security.connected());
                chat.save();
                SESSIONS.remove(outbound);
            });
        }
    }

    private static void sendUserDisconnected(Long chatId) {
        sendMessage(chatId, ">>> has left conversation");
    }

    private static void sendUserConnected(Long chatId) {
        sendMessage(chatId, ">>> has joined to conversation");
    }

    private static void sendMessage(Long chatId, String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        Conversation chat = Conversation.findById(chatId);
        Message msg = new Message(chat, Security.connected(), message).save();
        SESSIONS.forEach(out -> {
            if (out.isOpen()) {
                out.send(msg.toString());
            }
        });
    }
}
