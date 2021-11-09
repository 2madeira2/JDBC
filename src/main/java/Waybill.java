import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public final class Waybill {
    private int waybillNum;
    private LocalDate waybillDate;
    private @NotNull
    String OrgSender;

    public Waybill(int waybillNum, LocalDate waybillDate, @NotNull String orgSender) {
        this.waybillNum = waybillNum;
        this.waybillDate = waybillDate;
        OrgSender = orgSender;
    }

    public int getWaybillNum() {
        return waybillNum;
    }

    public void setWaybillNum(int waybillNum) {
        this.waybillNum = waybillNum;
    }

    public LocalDate getWaybillDate() {
        return waybillDate;
    }

    public void setWaybillDate(LocalDate waybillDate) {
        this.waybillDate = waybillDate;
    }

    @NotNull
    public String getOrgSender() {
        return OrgSender;
    }

    public void setOrgSender(@NotNull String orgSender) {
        OrgSender = orgSender;
    }
}
