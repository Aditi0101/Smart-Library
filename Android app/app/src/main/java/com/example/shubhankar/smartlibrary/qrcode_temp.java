package com.example.shubhankar.smartlibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static com.example.shubhankar.smartlibrary.R.drawable.map_1;

public class qrcode_temp extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private static final int REQUEST_CAMERA = 1;
    private static final String TAG = "qrcode_temp";
    private ZXingScannerView mScannerView;
    public String self_name = "";
    public String ip = "";
    public point path_res[] = new point[]{new point(0,0),new point(0,1),new point(0.4f,1),
            new point(0.4f,0.4f),new point(1.6f,0.4f),new point(1.6f,1),new point(2.9f,1),
            new point(2.9f,0.4f),new point(4.1f,0.4f),new point(4.1f,2.6f),new point(2.9f,2.6f),
            new point(2.5f,2),new point(1.6f,2),new point(1.6f,2.6f),new point(0.4f,2.6f),
            new point(0.4f,2),new point(0,2),new point(0,3)};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_temp);
        int width = getResources().getDisplayMetrics().widthPixels;
        int hight = getResources().getDisplayMetrics().heightPixels/2;
        LinearLayout l1 = (LinearLayout)findViewById(R.id.layout1);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.layout2);

        l1.setLayoutParams(new LinearLayout.LayoutParams(width, hight));
        l2.setLayoutParams(new LinearLayout.LayoutParams(width, hight));

        ImageView image = (ImageView)findViewById(R.id.imageView2);

        Intent intent = getIntent();
        self_name = intent.getStringExtra("SELF_NAME");
        ip = intent.getStringExtra("IP");

        mScannerView = new ZXingScannerView(this);
        //setContentView(mScannerView);
        l1.addView(mScannerView);
        //l1.setRotation(90);
        //int currentapiVersion = android.os.Build.VERSION.SDK_INT;
       // if (currentapiVersion >= android.os.Build.VERSION_CODES.M)
        {/*
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();

            } else {
                requestPermission();
            }*/
        }

    
    }

    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                               // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                                {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(qrcode_temp.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

        //int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //if (currentapiVersion >= android.os.Build.VERSION_CODES.M)
      /*  {
            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());
        mScannerView.resumeCameraPreview(qrcode_temp.this);

        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result");
        //builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
         //   @Override
         //   public void onClick(DialogInterface dialog, int which) {
               // mScannerView.resumeCameraPreview(qrcode_temp.this);
          //  }
        //});
        /*builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                startActivity(browserIntent);
            }
        });
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/

        String res = rawResult.getText();
        String[] resu = res.split(",");
        Log.i("result ", resu[0]+" "+resu[1]);
        if(resu[0].equals("HSC_SMART_LIBRARY"))
        {
            getPath(resu[1], self_name);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wrong Marker");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Marker not found in DataBase.");
        AlertDialog alert1 = builder.create();
        alert1.show();
        }

    }

    public class point{
        private float x;
        private float y;

        public point(float x, float y){
            this.x = x;
            this.y = y;
        }
        public float getX(){
            return x;
        }
        public float getY(){
            return y;
        }
    }

    public void draw_map(String s, String f, String path)
    {
        point st = path_res[Integer.parseInt(s)];
        ImageView img = (ImageView)findViewById(R.id.imageView2);
        img.setImageResource(map_1);
        Log.i("width ", ""+img.getWidth()+img.getHeight());
        Bitmap bmp = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        img.draw(c);
        Paint p = new Paint();
        int mul_x = (int)( bmp.getWidth()/4.5);
        int mul_y = (int) bmp.getHeight()/3;
        p.setColor(GREEN);
        c.drawCircle(st.getX()*mul_x, st.getY()*mul_y, 20, p);
        String temp[] = path.split(",");
        p.setColor(BLUE);
        p.setStrokeWidth(10);
        point pp1;
        point pp2;
        int i;
        for(i=0; i<temp.length-1;i++)
        {
            pp1 = path_res[Integer.parseInt(temp[i])];
            pp2 = path_res[Integer.parseInt(temp[i+1])];
            c.drawLine(pp1.getX()*mul_x, pp1.getY()*mul_y, pp2.getX()*mul_x,pp2.getY()*mul_y,p);
        }
        pp1 = path_res[Integer.parseInt(temp[i])];
        p.setColor(RED);
        c.drawCircle(pp1.getX()*mul_x, pp1.getY()*mul_y, 20, p);

        img.setImageBitmap(bmp);
    }

    public void getPath(final String s, final String f)
    {
        List<String> result;
        String head;
        String data;
        String url  = "http://" + ip + "/" + "map/" + s + "/" + f;
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
                    builder.setMessage("No direct path be found to your book location.")
                            .setTitle("Path Not Found");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    draw_map(s,f,data);
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPath(s,f);
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
                Log.i("getrequest","kdskjf          " + e);
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
