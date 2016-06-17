package bazinac.pichackanakafe;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.CollationElementIterator;

public class Pichacka extends AppCompatActivity {

    TextView mText;
    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    IntentFilter mFilters[];
    String mTechLists[][];

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        wakeLock.acquire();
    }

    public void onDestroy(){
        super.onDestroy();
        //wakeLock.release();
    }


    @Override
    public void onStart(){
            super.onStart();

            mText = (TextView) findViewById(R.id.text);

            mAdapter = NfcAdapter.getDefaultAdapter(this);


            if (mAdapter == null) {
                Toast.makeText(this, "NFC nefunguje sakra!.", Toast.LENGTH_LONG).show();
                finish();
                return;
             }

            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            mTechLists =  new String[][] {
                new String[] { Ndef.class.getName() },
         };


            IntentFilter f = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);


            mFilters = new IntentFilter[] {
                    f
            };



        // tady se to opakuje, dořešit

            Intent intent = getIntent();
            Log.i("intent: ","jede intent" + intent + intent.getAction());


        if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())){


            //byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);



            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] id = tag.getId();
            ByteBuffer wrapped = ByteBuffer.wrap(id);
            wrapped.order(ByteOrder.LITTLE_ENDIAN);
            int signedInt = wrapped.getInt();
            Long number = signedInt & 0xffffffffl;



            String t = number.toString();

            mTextView = (TextView) findViewById(R.id.textView_explanation);
            mTextView.setText(t);

            Log.i("ID: ", t);
            Toast.makeText(this, "Captured ID ->." + t, Toast.LENGTH_LONG).show();

        };

    };



    @Override
    public void onResume(){
        super.onResume();

        if (mAdapter != null)
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
    }

    @Override
    public void onPause(){
        super.onPause();
        if (mAdapter != null)
            mAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent){
        Log.i("intent: ","nalezen intent" + intent + intent.getAction());
        //mText = (TextView) findViewById(R.id.text);

        if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())){


            //byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);



            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] id = tag.getId();
            ByteBuffer wrapped = ByteBuffer.wrap(id);
            wrapped.order(ByteOrder.LITTLE_ENDIAN);
            int signedInt = wrapped.getInt();
            Long number = signedInt & 0xffffffffl;



            String t = number.toString();

            mTextView = (TextView) findViewById(R.id.textView_explanation);
            mTextView.setText(t);
            
            Log.i("ID: ", t);
            Toast.makeText(this, "Captured ID ->." + t, Toast.LENGTH_LONG).show();

        }




        //mText.setText(getNdefMessages(intent));
    }


}