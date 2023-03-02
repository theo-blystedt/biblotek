import java.util.Date;

public class Loan {
    int userId;
    int isbn;
    int loanId;
    Date date;

    Loan(){

    }

    Loan(int userId, int isbn, Date date, int loanId){
        this.userId = userId;
        this.isbn = isbn;
        this.date = date;
        this.loanId = loanId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }
}
