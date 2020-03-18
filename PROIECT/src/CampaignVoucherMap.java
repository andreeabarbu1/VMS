import java.util.ArrayList;

public class CampaignVoucherMap extends ArrayMap<String,ArrayList <Voucher>> {
    //cheia este emai-ul unui utilizator si valoarea este reprezentata de colectia de vouchere primite in cadrul campaniei
    public boolean addVoucher(Voucher v) {
        if (v == null)
            return false;
        else if (containsKey(v.email) == true) {
            ArrayList<Voucher> vouchers = get(v.email);
                vouchers.add(v);
                return true;
            }
        else {
            ArrayList<Voucher> vouchers = new ArrayList <Voucher> ();
            vouchers.add(v);
            put(v.email, vouchers);
            return true;
        }
    }
}
