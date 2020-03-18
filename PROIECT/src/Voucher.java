import java.time.LocalDateTime;

enum VoucherStatusType {
    USED, UNUSED;
}
public abstract class Voucher {
    //clasa ce implementeaza un voucher si atribuitele sale
    Integer voucherID;
    Integer code;
    VoucherStatusType status;
    LocalDateTime useDate;
    String email;
    Integer campaignID;
    public abstract String getCode();
    public abstract String getStatus();
    public abstract void setStatusUsed();
    public abstract String toString();

}
