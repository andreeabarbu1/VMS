import java.util.*;

public class UserVoucherMap extends ArrayMap<Integer, ArrayList <Voucher>> {
    //cheia este ID-ul campaniei si valoarea este reprezentata de colectia de vouchere primite in cadrul campaniei
    public boolean addVoucher (Voucher v) {
        if (v == null)
            return false;
        else if(containsKey(v.campaignID) == true) {
            ArrayList<Voucher> vouchers = get(v.campaignID);
            vouchers.add(v);
            return true;
        }
        else {
            ArrayList<Voucher> vouchers = new ArrayList <Voucher> ();
            vouchers.add(v);
            put(v.campaignID, vouchers);
            return true;
        }
    }

}
