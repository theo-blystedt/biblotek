public class Users {
    int id;
    String fName;
    String lName;
    String title;
    int sNum;
    int numOfBorrowedBooks;
    final int numBooksLimit; //vet inte om detta funkar pga final. Den kanske alltid är null.

    Users(int id, String fname, String lName, String title, int sNum, int numOfBorrowedBooks, int numBooksLimit){
        this.id = id;
        this.fName = fname;
        this.lName = lName;
        this.title = title;
        this.sNum = sNum;
        this.numOfBorrowedBooks = numOfBorrowedBooks;
        this.numBooksLimit = numBooksLimit;
    }

    public void lendItem(String title) {

    }

}
