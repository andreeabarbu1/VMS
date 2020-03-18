import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

enum NotificationType {
    EDIT, CANCEL;
}
public class Notification {
    //colectie de notificari
    LocalDateTime receivedDate;
    Integer campaignID;
    ArrayList <Integer> codesVochers;
    NotificationType notification;

    public Notification (LocalDateTime receivedDate, Integer campaignID, ArrayList <Integer> codesVochers, String type) {
        this.receivedDate = receivedDate;
        this.campaignID = campaignID;
        this.codesVochers = codesVochers;
        notification = NotificationType.valueOf(type);
    }

    public String toString() {
        return this.campaignID + ";" + this.codesVochers + ";" + this.receivedDate + ";" + this.notification;
    }
}
