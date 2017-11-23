package com.example.shubhankar.smartlibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class qr_code extends AppCompatActivity {
    public String book_name = "";
    public String ip = "";
    public String self_name = "";
    private static final String TAG = "QRCODE_FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        Intent intent = getIntent();
        book_name = intent.getStringExtra("BOOK_NAME");
        ip = intent.getStringExtra("IP");
        self_name = intent.getStringExtra("SELF_NAME");

        TextView tv1 = (TextView)findViewById(R.id.textView4);
        tv1.setText(book_name);
        TextView tv2 = (TextView)findViewById(R.id.textView6);
        tv2.setText(self_name);


    }

    public void search(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //back to first
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Do you want to search another Book?")
                .setTitle("Confirm");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void navigate(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //navigate to navigation
                call_nagivate();
                //finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Scan QR makers for viewing path from your current location.")
                .setTitle("Navigate");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void call_nagivate()
    {
        Intent intent1 = new Intent(this, qrcode_temp.class);
        intent1.putExtra("SELF_NAME", self_name);
        intent1.putExtra("IP", ip);
        startActivity(intent1);
    }
    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                Log.i(TAG,"kdskjf          " + e);
                result = null;

            }
            return result;
        }
        @Override
        protected void onPostExecute (String result){
            super.onPostExecute(result);
        }
    }

}
