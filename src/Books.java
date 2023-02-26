public class Books {

    String title;
    int isbn;
    int available; // de som inte är utlånade
    int loaned; //utlånade


    Books(String title, int isbn, int available, int loaned){
        this.title = title;
        this.isbn = isbn;
        this.available = available;
        this.loaned = loaned;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getLoaned() {
        return loaned;
    }

    public void setLoaned(int loaned) {
        this.loaned = loaned;
    }
}
