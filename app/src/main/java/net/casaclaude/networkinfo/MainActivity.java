package net.casaclaude.networkinfo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private void writeToFile(String data) {
        try {
            File myFile = new File("/sdcard/networkinfo.txt");
            myFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(myFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv=(TextView)findViewById(R.id.myText);
        final TelephonyManager telephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String getCellIdentity;
                        List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
                        for (CellInfo cellInfo : cellInfoList)
                        {
                            if (cellInfo.isRegistered())
                            {
                                getCellIdentity = cellInfo.toString();
                            }
                            else
                            {
                                getCellIdentity = "NOT REGISTERED";
                            }
                            tv.setText(getCellIdentity);
                            writeToFile(getCellIdentity);
                        }
                    }
                });
            }
        }, 0, 2000);

    }
}
