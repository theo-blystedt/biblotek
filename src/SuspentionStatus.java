import java.util.Date;

public class SuspentionStatus {
    int uId;
    Date startDate;
    Date endDate;
    int warnings;
    boolean isSuspended;

    SuspentionStatus(){

    }

    SuspentionStatus(int uId, Date startDate, Date endDate, int warnings, boolean isSuspended){
        this.uId = uId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.warnings = warnings;
        this.isSuspended = isSuspended;

    }
}
