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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class first extends AppCompatActivity {

    private static final String TAG = "mainactivity";
    public static  EditText et;
    public static  EditText et1;
    public String self_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        et = (EditText)findViewById(R.id.textView2);
        et1 = (EditText)findViewById(R.id.textView3);

        /*Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                button_click();

            }
        });*/


    }

    public void button_click(View view)
    {
        search_book();
    }

    public void search_book()
    {
        List<String> result;
        String head;
        String data;
        String url  = "http://" + et1.getText().toString() + "/" + "search/" + et.getText().toString();
        Log.i(TAG, url);
        HttpGetRequest get_request = new HttpGetRequest();
        try{
            result = get_request.execute(url).get();
            head = result.get(0);
            data = result.get(1);
            Log.i(TAG, "Server Response: " + data);
            Log.i(TAG, "Server Header: " + head);
            //TextView tv = (TextView)findViewById(R.id.textView);
            if(head.equals("200") || head.equals("OK") || head.equals("[HTTP/1.1 200 OK]"))
            {
                if(data.equals("0"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setMessage("The book you searched for is not present in library.")
                            .setTitle("No Book Found");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    //tv.setText("Self Number: "+result);
                    new_activity(data);
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        search_book();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setMessage("Unable to Connect to server. Check your network connectivity and Try Again.")
                        .setTitle("Error");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        catch (Exception e)
        {
            Log.i(TAG, "GET FAILED: " + e);
        }
    }

    public void new_activity(String str)
    {
        Intent intent = new Intent(this, qr_code.class);
        intent.putExtra("BOOK_NAME", et.getText().toString());
        intent.putExtra("IP", et1.getText().toString());
        intent.putExtra("SELF_NAME", str);
        startActivity(intent);
    }

    public class HttpGetRequest extends AsyncTask<String, Void, List<String>> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        List<String> list = new ArrayList<String>();


        @Override
        protected List<String> doInBackground(String... params) {
            String stringUrl = params[0];
            String result[] = {"", ""};
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

               // list.add(connection.getHeaderFieldKey(0).toString());
                Map<String, List<String>> hearder = connection.getHeaderFields();
                list.add(hearder.get(null).toString());
                list.add(stringBuilder.toString());
                Log.i("Connection_response", list.get(0));
            } catch (IOException e) {
                Log.i(TAG,"kdskjf          " + e);
                list.add(null);
                list.add(null);

            }

            return list;
        }
        @Override
        protected void onPostExecute (List<String> result){
            super.onPostExecute(result);
        }
    }

}
