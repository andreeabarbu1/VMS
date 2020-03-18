import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test {

    public static void main(String args[]) {
        BufferedReader reader, reader1;
        Integer campaignID;
        String campaignName;
        Integer userID;
        String voucherID;
        String email;
        Integer buget;
        String strategy;
        LocalDateTime dateStart, dateFinal;
        String campaignDescription;
        Integer nrVouchers;
        Float value;
        String voucherType;
        Campaign c;
        VMS colec = VMS.getInstance();
        ArrayList<String> lines = new ArrayList<>();

        //citirea din fisierul campaigns.txt
        try {
            reader = new BufferedReader(new FileReader("/home/andreea/IdeaProjects/TemaVMS/src/test05/input/campaigns.txt"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DateTimeFormatter fomatt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentTime = LocalDateTime.parse(lines.get(1), fomatt);
        //campaniile vor fi in lines incepand cu indexul 2
        for (int i = 2; i < lines.size(); i++) {
            String[] text = lines.get(i).split(";");
            campaignID = Integer.parseInt(text[0]);
            DateTimeFormatter fomattt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateStart = LocalDateTime.parse(text[3], fomattt);
            DateTimeFormatter fomatttt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateFinal = LocalDateTime.parse(text[4], fomatttt);
            nrVouchers = Integer.parseInt(text[5]);
            c = new Campaign(currentTime, campaignID, text[1], text[2], dateStart, dateFinal, nrVouchers, text[6]);
            colec.addCampaign(c);
        }

        //citirea din fisierul user.txt
        ArrayList<String> line1 = new ArrayList<>();
        try {
            reader1 = new BufferedReader(new FileReader("/home/andreea/IdeaProjects/TemaVMS/src/test05/input/users.txt"));
            String line = reader1.readLine();
            while (line != null) {
                line1.add(line);
                line = reader1.readLine();
            }
            reader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer nrUsers = Integer.parseInt(line1.get(0));
        for (int i = 1; i < line1.size(); i++) {
            String[] text = line1.get(i).split(";");
            userID = Integer.parseInt(text[0]);
            User u = new User(userID, text[1], text[3], text[2], text[4]);
            colec.addUser(u);
        }

        //citirea fisierului events.txt
        int j = 0;
        ArrayList<String> line2 = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader("/home/andreea/IdeaProjects/TemaVMS/src/test05/input/events.txt"));
            String line = reader.readLine();
            while (line != null) {
                line2.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 2; i < line2.size(); i++) {
            String[] text = line2.get(i).split(";");
            userID = Integer.parseInt(text[0]);

            //pentru addCampaign
            User u = colec.getUser(userID);
                if (text[1].equals("addCampaign")) {
                    if (u.userID.equals(userID) && u.status.equals(UserType.ADMIN)) {
                        campaignID = Integer.parseInt(text[2]);
                        campaignName = text[3];
                        campaignDescription = text[4];
                        DateTimeFormatter fomat4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        dateStart = LocalDateTime.parse(text[5], fomat4);
                        DateTimeFormatter fomat5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        dateFinal = LocalDateTime.parse(text[6], fomat5);
                        buget = Integer.parseInt(text[7]);
                        strategy = text[8];
                        Campaign c2 = new Campaign(currentTime, campaignID, campaignName, campaignDescription, dateStart, dateFinal, buget, strategy);
                        colec.addCampaign(c2);
                    }
                } else if (text[1].equals("editCampaign")) {
                    if (u.userID.equals(userID) && u.status.equals((UserType.ADMIN))) {
                        campaignID = Integer.parseInt(text[2]);
                        campaignName = text[3];
                        campaignDescription = text[4];
                        DateTimeFormatter fomat4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        dateStart = LocalDateTime.parse(text[5], fomat4);
                        DateTimeFormatter fomat5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        dateFinal = LocalDateTime.parse(text[6], fomat5);
                        buget = Integer.parseInt(text[7]);
                        for (Campaign camp : colec.listCampain) {
                            if (camp.campaignID.equals(campaignID)) {
                                Campaign newC = new Campaign(currentTime, campaignID, campaignName, campaignDescription, dateStart, dateFinal, buget, camp.strategy);
                                colec.updateCampaign(camp.campaignID, newC, currentTime);
                            }
                        }
                    }
                } else if (text[1].equals("cancelCampaign")) {
                    if (u.userID.equals(userID) && u.status.equals((UserType.ADMIN))) {
                        campaignID = Integer.parseInt(text[2]);
                        for (Campaign camp : colec.listCampain) {
                            if (camp.campaignID.equals(campaignID)) {
                                colec.cancelCampaign(camp.campaignID, currentTime);
                            }
                        }
                    }
                    //pentru generateVoucher
                } else if (text[1].equals("generateVoucher")) {
                    if (u.userID.equals(userID) && u.status.equals((UserType.ADMIN))) {
                        campaignID = Integer.parseInt(text[2]);
                        email = text[3];
                        voucherType = text[4];
                        value = Float.parseFloat(text[5]);
                        User newu = colec.getUser(email);
                        colec.getCampaign(campaignID).addObserver(newu);
                        colec.getCampaign(campaignID).generateVoucher(email, voucherType, value);

                    }
                    //pentru redeemVoucher
                } else if (text[1].equals("redeemVoucher")) {
                    if (u.userID.equals(userID) && u.status.equals((UserType.ADMIN))) {
                        campaignID = Integer.parseInt(text[2]);
                        voucherID = text[3];
                        colec.getCampaign(campaignID).redeemVoucher(voucherID, currentTime);
                    }
                    //pentru getVouchers
                } else if(text[1].equals("getVouchers") && u.status.equals(UserType.GUEST)) {
                        System.out.println(u.mapUser.toString());
                        //pentru getObservers
                } else if(text[1].equals("getObservers") && u.status.equals((UserType.ADMIN))) {
                    if(u.userID.equals(userID)) {
                        campaignID = Integer.parseInt(text[2]);
                        ArrayList <User>  observ = new ArrayList<>();
                        observ = colec.getCampaign(campaignID).getObservers();
                        for (User us : observ) {
                            System.out.println(us);
                        }
                    }
                    //pentru getNotifications
                } else if (text[1].equals("getNotifications")) {
                    if(u.userID.equals(userID) && u.status.equals(UserType.GUEST)) {
                        System.out.println(u.getNotificationCol());
                    }
                }
        }
    }
}
