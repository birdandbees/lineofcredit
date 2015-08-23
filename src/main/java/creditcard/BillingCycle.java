package creditcard;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingjing on 8/23/15.
 */
public class BillingCycle {

    private List<Transaction> transactions;
    private double startBalance;
    private DateTime paymentDueDate;
    private DateTime billingCycleStartDate;
    private double currentAPR;
    private double currentPrincipal;
    private DateTime lastTransactionDate;


    public BillingCycle(DateTime billingCycleStartDate, double balance, double APR, double principal, DateTime dueDate) {
        this.billingCycleStartDate = billingCycleStartDate;
        this.startBalance = balance;
        transactions = new ArrayList<Transaction>();
        this.currentAPR = APR;
        this.currentPrincipal = principal;
        this.lastTransactionDate = billingCycleStartDate;
        this.paymentDueDate = dueDate;

    }

    public double getCurrentPayment(DateTime date) {
        double owned = startBalance + currentPrincipal;
        if (date.toLocalDate().isEqual(paymentDueDate.toLocalDate())) {
            chargeOrCredit(Transaction.TransactionType.Credit, 0.0, paymentDueDate);
            owned += computeTotalInterests();
        }

        return owned;
    }

    public void chargeOrCredit(Transaction.TransactionType type, double amount, DateTime date) {
        Transaction transaction = new Transaction(type, date, amount);
        transaction.setInterest(currentPrincipal > 0 ? currentPrincipal * currentAPR * getDaysinCycle(lastTransactionDate, date) / 365 : 0);
        currentPrincipal = type == Transaction.TransactionType.Charge ? currentPrincipal + amount : currentPrincipal - amount;
        lastTransactionDate = date;
        transactions.add(transaction);

    }

    private int getDaysinCycle(DateTime startDate, DateTime endDate) {
        return Days.daysBetween(startDate.toLocalDate(), endDate.toLocalDate()).getDays();
    }

    private double computeTotalInterests() {
        double owned = 0.0;
        for (Transaction each : transactions) {
            owned += each.interest;
        }
        return owned;
    }

    public void printStatement() {
    }

    public DateTime getPaymentDueDate() {
        return paymentDueDate;
    }

    public DateTime getBillingCycleStartDate() {
        return billingCycleStartDate;
    }

}
