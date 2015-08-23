package creditcard;

import org.joda.time.DateTime;

/**
 * Created by jingjing on 8/23/15.
 */
public class Transaction {

    public enum TransactionType {
        Charge, Credit
    }

    public DateTime dateOccurred;
    public TransactionType transactionType;
    public double amount;
    public double interest;

    public Transaction(TransactionType transactionType, DateTime date, double amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.dateOccurred = date;

    }

    public void setInterest(double amount) {
        this.interest = amount;
    }


}
