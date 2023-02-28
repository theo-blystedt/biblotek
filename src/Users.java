public class Users {
    int id;
    String fName;
    String lName;
    int titleId; //bestämmer hur många böcker man kan låna och vem som är admin. Tönker 1 för admin och sedan
    //resten för typ av student, tex phd kan låna 5 böcker osv
    int sNum; //personnummber
    int numOfBorrowedBooks;
    boolean status; //hur man suspendar användare, true == suspended. Svårt att se hur man implementerar 15 dagar



    Users(int id, String fname, String lName, int titleId, int sNum, int numOfBorrowedBooks, boolean status){
        this.id = id;
        this.fName = fname;
        this.lName = lName;
        this.titleId = titleId;
        this.sNum = sNum;
        this.numOfBorrowedBooks = numOfBorrowedBooks;
        this.status = status;

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

    public void setTitle(int titleId) {
        this.titleId = titleId;
    }

    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public int getNumOfBorrowedBooks() {
        return numOfBorrowedBooks;
    }

    public void setNumOfBorrowedBooks(int numOfBorrowedBooks) {
        this.numOfBorrowedBooks = numOfBorrowedBooks;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
