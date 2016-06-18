package bazinac.pichackanakafe;

import android.app.PendingIntent;

import android.content.Intent;
import android.content.IntentFilter;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;

public class Pichacka extends AppCompatActivity {

    TextView txt1;
    TextView txt2;

    NfcAdapter adapter;
    PendingIntent pIntent;
    IntentFilter filters[];
    String techLists[][];

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    Calendar c = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PichackaRezervace");
        wakeLock.acquire();
    }

    public void onDestroy(){
        super.onDestroy();
        wakeLock.release();
    }


    @Override
    public void onStart(){
            super.onStart();

            txt1 = (TextView) findViewById(R.id.text);

            adapter = NfcAdapter.getDefaultAdapter(this);


            if (adapter == null) {
                Toast.makeText(this, "NFC nefunguje sakra!.", Toast.LENGTH_LONG).show();
                finish();
                return;
             }

            pIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            techLists =  new String[][] {
                new String[] { Ndef.class.getName() },
             };


            IntentFilter f = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

            filters = new IntentFilter[] {
                    f
            };

            // pro pripad ze intent appku nastartoval
            Intent intent = getIntent();
            Log.i("intent: ","(onstart)" + intent + intent.getAction());

            parseIntent(intent);

    };



    @Override
    public void onResume(){
        super.onResume();

        if (adapter != null)
            adapter.enableForegroundDispatch(this, pIntent, filters, techLists);
    }

    @Override
    public void onPause(){
        super.onPause();
        if (adapter != null)
            adapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent){
        Log.i("intent: ","(onNew)" + intent + intent.getAction());
        parseIntent(intent);


    }
    // metoda na zpracovani intentu TECH_DISCOVERED
    private void parseIntent(Intent intent) {
        if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())){


            // tady by se mel osetrit vstup (mame fakt id?)

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] id = tag.getId();
            ByteBuffer wrapped = ByteBuffer.wrap(id);
            wrapped.order(ByteOrder.LITTLE_ENDIAN);
            int signedInt = wrapped.getInt();
            Long number = signedInt & 0xffffffffl;

            String sID = number.toString();
            long t = c.get(Calendar.MILLISECOND);

            Log.i("ID: ", sID);

            Coffee coffee = new Coffee(sID,t);




            // UI AKCE
            txt2 = (TextView) findViewById(R.id.textView_caughtId);
            txt2.setText("got " + sID);







        }
        ;
    }


}