import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class LoyalityVoucher extends Voucher {
    //reucerea este folosita o singura data in cadrul campaniei
    public float sale;
    public LoyalityVoucher(Integer voucherID, String email, Integer campaignID, float sale) {
        this.voucherID = voucherID;
        this.code = voucherID;
        this.email = email;
        this.campaignID = campaignID;
        this.sale = sale;
        status = VoucherStatusType.UNUSED;
    }

    public double getSale() {
        return sale;
    }

    public String getCode() {
        return code.toString();
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatusUsed() {
        this.status = VoucherStatusType.USED;
    }

    public String toString(){
        return this.voucherID + ";" + this.status + ";" + this.email + ";" + this.sale +
                ";" + this.campaignID + ";" + this.useDate;
    }

}
