package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.utils.HTML;

@Entity
@Table(name = "tt_messages")
public class Message extends Model {
    @ManyToOne
    public Conversation conversation;

    @Required
    public String fromNickName;

    @Required
    @MaxLength(255)
    public String message;

    public Date posted;

    public Message(Conversation conversation, String fromNickName, String message) {
        if (message == null) {
            message = "";
        }
        if (message.length() > 255) {
            message = message.substring(0, 255);
        }
        this.conversation = conversation;
        this.fromNickName = fromNickName;
        this.message = message;
        posted = new Date();
    }

    public static List<Message> findChatMessages(Conversation chat) {
        return find("byConversationOrderByPostedAsc", chat).fetch();
    }

    @Override
    public String toString() {
        return new StringBuilder("<p>[").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(posted))
                .append("] ").append("<strong>").append(fromNickName).append(":").append("</strong> ")
                .append(HTML.htmlEscape(message)).append("</p>").toString();
    }
}
