package myapplication.busat;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by KKM on 11/26/2016.
 */

public class BUSATLocListener implements LocationListener {
    public double _longi;
    public double _lati;

    @Override
    public void onLocationChanged(Location location) {
         if(location != null){
             Log.v("Lati lis : ","" + location.getLatitude());
             Log.v("Longi lis : ","" + location.getLongitude());
             _longi = location.getLongitude();
             _lati = location.getLatitude();
         } else {
             Log.v("null case ", "null!!");
         }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

    }
}
