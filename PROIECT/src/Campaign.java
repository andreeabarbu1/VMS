import java.time.LocalDateTime;
import java.util.*;
interface Subject {
    public void addObserver (User user);
    public void removeObserver (User user);
    public void notifyAllObservers (Notification notification);
}

enum CampaignStatusType {
    NEW, STARTED, EXPIRED, CANCELLED;
}

public class Campaign implements Subject{
    Integer campaignID;
    String name;
    String description;
    LocalDateTime dateStart, dateFinal;
    Integer nrVouchers;
    Integer nrVouchersDisp;
    //voucherCode va fi folosit pentru generarea ID-ului
    Integer voucherCode = 0;
    CampaignStatusType status;
    //dictionarul de vouchere distribuite
    CampaignVoucherMap mapCampaign = new CampaignVoucherMap();
    //colectia de vouchere distribuite
    ArrayList<Voucher> listVouchers = new ArrayList<>();
    //colectia de utilizatori
    ArrayList <User> observers = new ArrayList<>();
    //startegie
    String strategy;

    //Constructor
    public Campaign(LocalDateTime currentDate, Integer ID, String name, String description, LocalDateTime dateStart,
                      LocalDateTime dateFinal, Integer nrVouchers, String strategy) {
        this.campaignID = ID;
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateFinal = dateFinal;
        this.nrVouchers = nrVouchers;
        this.strategy = strategy;
        this.nrVouchersDisp = nrVouchers;
        if (currentDate.compareTo(dateStart) < 0) {
            this.status = CampaignStatusType.NEW;
        }
        else if (currentDate.compareTo(dateStart) > 0 && currentDate.compareTo(dateFinal) < 0 ){
            this.status = CampaignStatusType.STARTED;
        }
        else if (currentDate.compareTo(dateFinal) > 0) {
            this.status = CampaignStatusType.EXPIRED;
        }
    }

    public ArrayList<Voucher> getVouchers() {
        return listVouchers;
    }

    public Voucher getVoucher(String code) {
        if(listVouchers != null) {
            for(Voucher v : listVouchers) {
                if(v.getCode().equals(code))
                    return v;
            }
        }
        return null;
    }

    public void generateVoucher(String email, String voucherType, float value) {
        Voucher v;
        if (nrVouchersDisp > 0) {
            voucherCode++;
            nrVouchersDisp--;
        }
        for (User u : observers) {
            if(u.email.equals(email)) {
                if (voucherType.equals("GiftVoucher")) {
                    v = new GiftVoucher(voucherCode, email, campaignID, value);
                    mapCampaign.addVoucher(v);
                    u.mapUser.addVoucher(v);
                    listVouchers.add(v);
                    return;
                }
                else if (voucherType.equals("LoyaltyVoucher")) {
                    v = new LoyalityVoucher(voucherCode, email, campaignID, value);
                    mapCampaign.addVoucher(v);
                    u.mapUser.addVoucher(v);
                    listVouchers.add(v);
                    return;
                }
            }
        }
    }

    public void redeemVoucher (String code, LocalDateTime date) {
        if (listVouchers != null) {
            for (Voucher v : listVouchers) {
                if (v.getCode().equals(code)) {
                    if (v.getStatus().equals(VoucherStatusType.UNUSED))
                        if (date.compareTo(dateStart) >= 0 && date.compareTo(dateFinal) <= 0) {
                        v.setStatusUsed();
                        v.useDate = date;
                    }
                }
            }
        }
    }

    public ArrayList<User> getObservers() {
        return observers;
    }

    public void addObserver (User user) {
        if (!observers.contains(user))
            observers.add(user);
    }
    public void removeObserver (User user) {
        if (observers.contains(user))
            observers.remove(user);
    }

    public void notifyAllObservers (Notification notification) {
        for (User u : observers) {
            u.update(notification);
        }
    }


}
