import java.time.LocalDateTime;
import java.util.ArrayList;
class Singleton
{
    private static Singleton single_instance = null;
    private Singleton() {
    }
    public static Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton();
        return single_instance;
    }
}

public class VMS {
    //modeleaza colectia de date VMS, fiind caracterizata de multimea de campanii existente si de utilizatori
    ArrayList<Campaign> listCampain;
    ArrayList<User> listUsers;
    private static VMS single_instance = null;

    private VMS() {
       listCampain = new ArrayList<>();
       listUsers = new ArrayList<>();
    }

    public static VMS getInstance()
    {
        if (single_instance == null)
            single_instance = new VMS();
        return single_instance;
    }

    public ArrayList<Campaign> getCampaigns () {
        return  listCampain;
    }

    public Campaign getCampaign (Integer id) {
        for(Campaign c : listCampain) {
            if(c.campaignID .equals(id))
                return c;
        }
        return null;
    }

    public void addCampaign(Campaign campaign) {
        listCampain.add(campaign);
    }

    //modifica campania cu id-ul specificat
    public void updateCampaign(Integer id, Campaign campaign, LocalDateTime date) {
        for (Campaign c : listCampain) {
            if (c.campaignID.equals(id)) {
                if (c.status.toString().equals("NEW") && c.nrVouchersDisp.equals( campaign.nrVouchersDisp)) {
                    c.nrVouchers = campaign.nrVouchers;
                    c.name = campaign.name;
                    c.description = campaign.description;
                    c.dateStart = campaign.dateStart;
                    c.dateFinal = campaign.dateFinal;
                    ArrayList <Integer> codesVouchers = new ArrayList<>();
                    for (Voucher u : campaign.listVouchers)
                        codesVouchers.add(u.voucherID);
                    c.notifyAllObservers(new Notification(date, campaign.campaignID, codesVouchers ,"EDIT"));

                }
                else if (c.status.toString().equals("STARTED")) {
                    c.nrVouchers = campaign.nrVouchers;
                    c.dateFinal = campaign.dateFinal;
                    ArrayList <Integer> codesVouchers = new ArrayList<>();
                    for (Voucher u : campaign.listVouchers)
                        codesVouchers.add(u.voucherID);
                    c.notifyAllObservers(new Notification(date, campaign.campaignID, codesVouchers ,"EDIT"));
                }
            }
        }
    }

    public void cancelCampaign (Integer id, LocalDateTime date) {
        ArrayList <Integer> codesVouchers = new ArrayList<>();
        for (Campaign c : listCampain) {
            if (c.campaignID.equals(id))
                if (!(c.status.toString().equals("NEW") && c.status.toString().equals("STARTED"))) {
                    c.status = CampaignStatusType.CANCELLED;
                    for (Voucher u : c.listVouchers)
                        codesVouchers.add(u.voucherID);
                    c.notifyAllObservers(new Notification(date, c.campaignID, codesVouchers ,"CANCEL"));
                }
        }
        codesVouchers.clear();
    }

    public User getUser (String email) {
        for (User u : this.listUsers)
            if(u.email.equals(email))
                return u;
            return null;
    }

    public User getUser (Integer id) {
        for (User u : this.listUsers)
            if(u.userID.equals(id))
                return u;
        return null;
    }

    public ArrayList<User> getUsers() {
        return listUsers;
    }

    public void addUser (User user) {
        listUsers.add(user);
    }

}
