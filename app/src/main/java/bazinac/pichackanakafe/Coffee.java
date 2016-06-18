package bazinac.pichackanakafe;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by bazinac on 18.6.16.
 */
public class Coffee {

    private String drinkerId;
    private String timeStamp;
    private String cen;
    private long time;


    public Coffee(String dId, long t){

        SimpleDateFormat formatter = new SimpleDateFormat("d.M.yyyy H:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t);


        this.drinkerId = dId;
        this.timeStamp = formatter.format(calendar.getTime());
        this.cen = translateIDtoCen();
        this.time = t;


    }


    public String getDrinkerId() {
        return drinkerId;
    }

    public void setDrinkerId(String drinkerId) {
        this.drinkerId = drinkerId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCen() {
        return cen;
    }

    public void setCen(String cen) {
        this.cen = cen;
    }


    // nakonec na JSON sereme
    public Bundle getJSONBundle(){

        JSONObject json = new JSONObject();

        try {
            json.put("serialID", this.getDrinkerId());
            json.put("cen", this.getCen());
            json.put("timeStamp", this.getTimeStamp());
            json.put("time", this.getTimeStamp());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("json", json.toString());

    }




    //tady je potreba implementovat preklad drinker id na cen
    private String translateIDtoCen(){
        return drinkerId;
    }

}
