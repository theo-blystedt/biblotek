import java.util.Date;

public class Users {
    int id;
    String fName;
    String lName;
    int titleId; //bestämmer hur många böcker man kan låna och vem som är admin. Tönker 1 för admin och sedan
    //resten för typ av student, tex phd kan låna 5 böcker osv
    int sNum; //personnummer
    //int numOfBorrowedBooks; //behövs inte kör count på databas rader
    Loan loan;
    Books book;
    Date suspensionStart;
    Date suspensionEnd;
    int warnings;
    boolean isSuspended;



    Users(int id, String fname, String lName, int titleId, int sNum){
        this.id = id;
        this.fName = fname;
        this.lName = lName;
        this.titleId = titleId;
        this.sNum = sNum;

    }

    Users(){
        this.book = new Books();
        this.loan = new Loan();
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

    public Date getSuspensionStart() {
        return suspensionStart;
    }

    public void setSuspensionStart(Date suspensionStart) {
        this.suspensionStart = suspensionStart;
    }

    public Date getSuspensionEnd() {
        return suspensionEnd;
    }

    public void setSuspensionEnd(Date suspensionEnd) {
        this.suspensionEnd = suspensionEnd;
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

