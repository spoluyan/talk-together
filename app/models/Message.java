package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.db.jpa.Model;

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

    public Message(String fromNickName, String message) {
        this.fromNickName = fromNickName;
        this.message = message;
    }

    public static List<Message> findChatMessages(Conversation chat) {
        return find("byConversationOrderByPostedAsc", chat).fetch();
    }
}
