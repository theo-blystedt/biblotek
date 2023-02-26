public class Users {
    int id;
    String fName;
    String lName;
    String title; //bestämmer hur många böcker man kan låna
    int sNum; //personnummber
    int numOfBorrowedBooks;
    boolean status; //hur man suspendar användare, true == suspended. Svårt att se hur man implementerar 15 dagar



    Users(int id, String fname, String lName, String title, int sNum, int numOfBorrowedBooks, boolean status){
        this.id = id;
        this.fName = fname;
        this.lName = lName;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
