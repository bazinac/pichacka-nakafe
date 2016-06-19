package bazinac.pichackanakafe;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.util.HashMap;


/**
 * Created by bazinac on 19.6.16.
 */
public class SerialTranslatorService {

    private static final String USERLIST_FILE = "pichacka_userlist";

    private Context con;

    // sId; jméno/cen
    private HashMap<String, String> userList;

    public SerialTranslatorService(Context context){

        con = context;
        reloadUserList();

    }

    public void reloadUserList(){


        userList = new HashMap<String, String>();

        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    con.openFileInput(USERLIST_FILE)));

            String inputString;

            int i = 0;
            while ((inputString = inputReader.readLine()) != null) {

                String[] strArr = inputString.split(";");
                userList.put(strArr[0],strArr[1]);
                i++;
            }
            Log.i("STranslatorServ: ","reload success, found recs: " + i);

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("STranslatorServ: ","reload failed!");
        }

    }

    public String translateSerialToCen(String sId){

        if (userList == null) {
            reloadUserList();
        }

        if ( userList.containsKey(sId)){

            return userList.get(sId);

        }

        else return sId;
    }



}
