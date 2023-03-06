import java.util.Date;

public class Users {
    int id;
    String fName;
    String lName;
    int titleId; //bestämmer hur många böcker man kan låna och vem som är admin. Tönker 1 för admin och sedan
    //resten för typ av student, tex phd kan låna 5 böcker osv
    int sNum; //personnummer
    Date suspentionStart;
    Date suspentionEnd;
    int warnings;
    boolean isSuspended;
    int suspentionCount;

    Users(){

    }

    //konstruktor för list of users då jag behöver till gång till varningar och skit
    Users(int id, String fName, String lName, int titleId, int sNum, boolean isSuspended, int warnings){
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.titleId = titleId;
        this.sNum = sNum;
        this.isSuspended = isSuspended;
        this.warnings = warnings;
    }


    //används inte
    Users(String fname, String lName, int titleId, int sNum){

        this.fName = fname;
        this.lName = lName;
        this.titleId = titleId;
        this.sNum = sNum;
        this.suspentionStart = null;
        this.suspentionEnd = null;
        this.warnings = 0;
        this.suspentionCount = 0;
    }


    public void lendItem(String title) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getTitleId() {
        return titleId;
    }


    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public Date getSuspentionStart() {
        return suspentionStart;
    }

    public void setSuspentionStart(Date suspentionStart) {
        this.suspentionStart = suspentionStart;
    }

    public Date getSuspentionEnd() {
        return suspentionEnd;
    }

    public void setSuspentionEnd(Date suspentionEnd) {
        this.suspentionEnd = suspentionEnd;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }
}

