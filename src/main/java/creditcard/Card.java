package creditcard;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jingjing on 8/23/15.
 */
public class Card {

    private final double credit_limit;
    private final double APR;
    private final int account_id;

    private Map<String, BillingCycle> billingCycles = new HashMap<String, BillingCycle>();
    private final int payment_period;
    private final DateTime account_open_date;
    private BillingCycle currentBillingCycle;

    public Card(int account_id, double credit_limit, double APR, int payment_period) {
        this.account_id = account_id;
        this.APR = APR;
        this.credit_limit = credit_limit;
        this.payment_period = payment_period;
        account_open_date = new DateTime();

    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public double getAPR() {
        return APR;
    }

    public int getAccount_id() {
        return account_id;
    }

    public int getPayment_period() {
        return payment_period;
    }

    public DateTime getAccount_open_date() {
        return account_open_date;
    }

    public void setBillingCycle(DateTime startDate, double balance, double APR, double principal) {
        BillingCycle billingCycle = new BillingCycle(startDate, balance, APR, principal, startDate.plusDays(payment_period));
        currentBillingCycle = billingCycle;
    }

    public double getCurrentBalance(DateTime date) throws Exception {
        if (date.getMillis() > currentBillingCycle.getPaymentDueDate().getMillis()) {
            throw new Exception("query date is over current billing cycle");
        }
        return currentBillingCycle.getCurrentPayment(date);
    }

    public double getCurrentPayment(DateTime date) {
        return currentBillingCycle.getCurrentPayment(date);
    }

    public void make_payment(double amount, DateTime date) {
        currentBillingCycle.chargeOrCredit(Transaction.TransactionType.Credit, amount, date);
    }

    public void draw(double amount, DateTime date) {
        currentBillingCycle.chargeOrCredit(Transaction.TransactionType.Charge, amount, date);
    }

    public void printStatement(String billingCycle) {
        billingCycles.get(billingCycle).printStatement();
    }

}
