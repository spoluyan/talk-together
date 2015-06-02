package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.MaxLength;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Message extends Model {
    @ManyToOne
    public Conversation conversation;

    @Required
    public String from;

    @Required
    @MaxLength(255)
    public String message;

    public Date posted;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
    }
}
