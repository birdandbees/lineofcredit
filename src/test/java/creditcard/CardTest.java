package creditcard;

import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Created by jingjing on 8/23/15.
 */
public class CardTest extends TestCase {

    @Test
    public void testScenario1() throws Exception {

        Card card = new Card(12345, 1000, 0.35, 30);
        DateTime date = new DateTime(2015, 7, 1, 9, 0, 0, 0);

        card.setBillingCycle(date, 0, 0.35, 0);
        card.draw(500, date);
        DateTime dueDate = new DateTime(date.plusDays(30));
        double result = card.getCurrentBalance(dueDate);

        System.out.println(result);

    }

    @Test
    public void testScenario2() throws Exception {
        Card card = new Card(12345, 1000, 0.35, 30);
        DateTime date = new DateTime(2015, 7, 1, 9, 0, 0, 0);

        card.setBillingCycle(date, 0, 0.35, 0);
        card.draw(500, date);
        DateTime dueDate = new DateTime(date.plusDays(15));
        card.make_payment(200, dueDate);
        dueDate = new DateTime(date.plusDays(25));
        card.draw(100, dueDate);
        dueDate = new DateTime(date.plusDays(30));
        double result = card.getCurrentBalance(dueDate);

        System.out.println(result);
    }

    @Test
    public void testGetAccount_id() throws Exception {

    }

    @Test
    public void testGetPayment_period() throws Exception {

    }

    @Test
    public void testGetAccount_open_date() throws Exception {

    }
}