import java.time.LocalDateTime;

public class GiftVoucher extends Voucher {
    //suma disponibila utilizata in cadrul campaniei, o singura data
    float dispSum;
    public GiftVoucher(Integer voucherID, String email, Integer campaignID, float dispSum) {
        this.voucherID = voucherID;
        this.code = voucherID;
        this.email = email;
        this.campaignID = campaignID;
        this.dispSum = dispSum;
        status = VoucherStatusType.UNUSED;
    }

    public double getDispSum() {
        return dispSum;
    }

    public String getCode() {
        return code.toString();
    }

    public String getStatus() {
        return status.toString();
    }

    @Override
    public void setStatusUsed() {
        this.status = VoucherStatusType.USED;
    }


    public String toString() {
        return this.voucherID + ";" + this.status + ";" + this.email + ";" + this.dispSum + ";" + this.campaignID + ";" + this.useDate;
    }

}
