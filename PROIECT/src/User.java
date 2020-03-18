import java.util.ArrayList;

interface Observers {
    public void update (Notification notification);
}

enum UserType {
    ADMIN, GUEST;
}

public class User implements Observers {
    //clasa ce implementeaza un utilizator
    Integer userID;
    String name;
    String email;
    String pass;
    UserType status;
    //dictionarul de vouchere primite
    UserVoucherMap mapUser = new UserVoucherMap();
    //colectie de notificari
    ArrayList<Notification> notificationCol = new ArrayList<>();
    public User (Integer userID, String name, String email, String pass, String type) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.pass = pass;
        status = UserType.valueOf(type);
    }

    @Override
    public void update(Notification notification) {
        notificationCol.add(notification);
    }

    public String toString() {
        return "[" + this.userID + ";" + this.name + ";" + this.email + ";" + this.status + "]";
    }

    public ArrayList<Notification> getNotificationCol() {
        return notificationCol;
    }
}
