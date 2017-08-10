package myapplication.busat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button _sndBtn;

    private TextView _textView;
    private TextView _phoneNumTextView;


    private LocationManager _locationManager;
    private LocationListener _locationListener;

    private String _phoneNum = "1";
    private double _latitude;
    private double _longitude;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        _phoneNumTextView = (TextView) findViewById(R.id.phoneNumTextView);
        //get Phone#

        _sndBtn = (Button) findViewById(R.id.sndButton);
//        _textView = (TextView) findViewById(R.id.textView);


        // init Location Manager & Handler
        _locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        _locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.v("onLoc", "Here");
                /*double laA = 42.998796;
                double loA = -78.802715;*/
                _latitude = Math.floor(location.getLatitude() * 1000000) / 1000000;
                _longitude = Math.floor(location.getLongitude() * 1000000) / 1000000;

//                _phoneNum = _phoneNum + _phoneNumTextView.getText().toString();
                Log.v("phone#", _phoneNum);


  //              _textView.append("\nSuccess\n" + _latitude + "\n" + _longitude);
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10 );
                return;
            }
        } else {
            configureAction();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v("onRequest","here");
        switch (requestCode){
            case 10:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureAction();
                return;
        }
    }
    private void configureAction() {
        _sndBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                    Log.v("configureAction", "SEEET");

                _locationManager.requestLocationUpdates("gps", 5000, 100, _locationListener);

    //            _phoneNum = _phoneNum + _phoneNumTextView.getText().toString();
                Log.v("phone#", _phoneNum);

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.v("디벅", "try");
//                            Log.v()
                            SNSModel sm = new SNSModel(_longitude, _latitude);
                            // reset;


                        } catch (Exception ex) {

                        }
                    }
                });
                th.start();

            }

        });
    }
}