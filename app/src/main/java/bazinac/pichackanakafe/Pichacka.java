package bazinac.pichackanakafe;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.text.CollationElementIterator;

public class Pichacka extends AppCompatActivity {

    TextView mText;
    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    IntentFilter mFilters[];
    String mTechLists[][];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart(){
        super.onStart();


        mText = (TextView) findViewById(R.id.text);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try{
            ndef.addDataType("text/plain");
        }catch(IntentFilter.MalformedMimeTypeException e){
            throw new RuntimeException("fail", e);
        }

        IntentFilter nt = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mFilters = new IntentFilter[]{
                ndef, nt
        };

        mTechLists = new String[][]{
                new String[]{
                        Ndef.class.getName()
                }
        };
        Intent intent = getIntent();

       // String s = getNdefMessages(intent);

        //mText.setText(getNdefMessages(intent));
    }


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
        Log.i("Foreground dispatch", "Discovered tag with .intent:" + intent + intent.getAction());
        //mText = (TextView) findViewById(R.id.text);

        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())||NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){


            byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);


            String t =  this.ByteArrayToHexString(tagId);

            Log.i("ID: ", t);
            Toast.makeText(this, "Captured ID ->." + t, Toast.LENGTH_LONG).show();

        }




        //mText.setText(getNdefMessages(intent));
    }

    // Converting byte[] to hex string:
    private String ByteArrayToHexString(byte [] inarray)
    {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }
}