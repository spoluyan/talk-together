package controllers;

import play.Logger;
import play.mvc.Http.WebSocketClose;
import play.mvc.Http.WebSocketEvent;
import play.mvc.WebSocketController;
import play.mvc.With;

@With(Secure.class)
public class ConversationWS extends WebSocketController {
    public static void conversation() {
        Logger.info("%s connected to conversation", Security.connected());
        while (inbound.isOpen()) {
            WebSocketEvent event = await(inbound.nextEvent());

            for (String message : WebSocketEvent.TextFrame.match(event)) {
                // TODO parse message
            }

            for (WebSocketClose closed : WebSocketEvent.SocketClosed.match(event)) {
                Logger.info("%s disconnected from conversation", Security.connected());
                // TODO remove user from conversation
            }
        }
    }
}
