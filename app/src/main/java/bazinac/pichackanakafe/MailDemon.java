package bazinac.pichackanakafe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * Created by bazinac on 18.6.16.
 */
public class MailDemon {


    DBhelper dbh;


    public void mailStatsDump(Context context, String recipients)
    {

        Log.i("dumpuju: ", "stats");
        dbh = new DBhelper(context);

        List<String> coffeeList = dbh.getCoffeeStats();
        String resString = "";

        for (String s : coffeeList)
        {
            resString += s + "\n";
        }

        try {
            GMailSender sender = new GMailSender("pichacka.nakafe@gmail.com", "poctivejturek");
            sender.sendMail("dump kávy (statistika)",resString,"pichacka.nakafe@gmail.com",recipients);
            Log.i("SendMail", "mail sent to> " + recipients);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }

    public void mailLogDump(Context context, String recipients)
    {

        Log.i("dumpuju: ", "log");
        dbh = new DBhelper(context);

        List<Coffee> coffeeList = dbh.getAllCoffees();
        String resString = "";

        for (Coffee c : coffeeList)
        {
            resString += c.getCen() + " - " + c.getTimeStamp() +  "\n";
        }

        try {
            GMailSender sender = new GMailSender("pichacka.nakafe@gmail.com", "poctivejturek");
            sender.sendMail("dump kávy (log)",resString,"pichacka.nakafe@gmail.com",recipients);
            Log.i("SendMail", "mail sent to> " + recipients);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }
}
